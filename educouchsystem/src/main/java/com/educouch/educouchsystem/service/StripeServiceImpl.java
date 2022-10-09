package com.educouch.educouchsystem.service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.LearnerTransactionRepository;
import com.educouch.educouchsystem.util.enumeration.EnrolmentStatusTrackerEnum;
import com.educouch.educouchsystem.util.enumeration.LearnerPaymentEnum;
import com.educouch.educouchsystem.util.exception.*;
import com.stripe.exception.*;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;

import javax.annotation.PostConstruct;

@Service("stripeService")
public class StripeServiceImpl implements StripeService {

    @Autowired
    LearnerTransactionRepository learnerTransactionRepository;

    @Autowired
    ClassRunService classRunService;

    @Autowired
    LearnerService learnerService;

    @Autowired
    OrganisationService organisationService;

    @Autowired
    EnrolmentStatusTrackerService enrolmentStatusTrackerService;

    @Autowired
    DepositRefundRequestService depositRefundRequestService;

    @Autowired
    OrgLmsRevenueMapService orgLmsRevenueMapService;

    String secretKey = "sk_test_51LnPrnBx7BYbBg97Zhhw2HorPZqL5srUqYdFgcYuldnNJlMMO4shzH979NrcJ4NDJGHAg0IQ2KuhR19vnEIaUiF800k3CzgsHu";

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentIntent createPaymentIntent(BigDecimal amount) throws StripeException {
        Long amountInCents = amount.longValue() * 100;
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amountInCents)
                        .setCurrency("sgd")
                        .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
                        .build();
        PaymentIntent paymentIntent = null;
        try {
            paymentIntent = PaymentIntent.create(params);
            return paymentIntent;
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }


    }

    public LearnerTransaction createNewLearnerTransaction(Long classRunId, Long learnerId,
                                                          LearnerPaymentEnum transactionType, BigDecimal amount) throws LearnerNotFoundException, ClassRunNotFoundException {

        ClassRun c = classRunService.retrieveClassRunById(classRunId);
        if (c != null) {
            Learner l = learnerService.getLearnerById(learnerId);
            if(l != null) {
                LearnerTransaction transaction = new LearnerTransaction(amount, transactionType, learnerId);
                transaction.setTimestamp(LocalDateTime.now());

                transaction = learnerTransactionRepository.save(transaction);

                c.getLearnerTransactions().add(transaction);
                l.getLearnerTransactions().add(transaction);

                classRunService.saveClassRun(c);
                learnerService.saveLearner(l);

                return transaction;
            }else {
                throw new LearnerNotFoundException("Unable to find learner");
            }
        } else {
            throw new ClassRunNotFoundException("Unable to find class run. ");
        }
    }

    @Override
    public void payDeposit(Long classRunId, Long learnerId,BigDecimal amount) throws
            ClassRunNotFoundException, LearnerNotFoundException {
        ClassRun c = classRunService.retrieveClassRunById(classRunId);
        if (c != null) {
            Learner l = learnerService.getLearnerById(learnerId);
            if(l != null) {
                EnrolmentStatusTracker e = new EnrolmentStatusTracker(c, l);
                e.setEnrolmentStatus(EnrolmentStatusTrackerEnum.DEPOSITPAID);
                enrolmentStatusTrackerService.saveEnrolmentStatusTracker(e);

                c.getEnrolmentStatusTrackers().add(e);
                classRunService.saveClassRun(c);

                l.getEnrolmentStatusTrackers().add(e);
                learnerService.saveLearner(l);
                createNewLearnerTransaction(c.getClassRunId(), l.getLearnerId(), LearnerPaymentEnum.DEPOSIT, amount);


            }else {
                throw new LearnerNotFoundException("Unable to find learner");
            }
        } else {
            throw new ClassRunNotFoundException("Unable to find class run. ");
        }

    }

    @Override
    public void payCourseFee(Long classRunId, Long learnerId,BigDecimal amount) throws
            ClassRunNotFoundException, LearnerNotFoundException,
            EnrolmentStatusTrackerNotFoundException {
        ClassRun c = classRunService.retrieveClassRunById(classRunId);
        if (c != null) {
            Learner l = learnerService.getLearnerById(learnerId);
            if(l != null) {
                try {
                    EnrolmentStatusTracker e = enrolmentStatusTrackerService.retrieveEnrolmentByLearnerIdAndClassRunId(classRunId, learnerId);
                    e.setEnrolmentStatus(EnrolmentStatusTrackerEnum.ENROLLED);

                    enrolmentStatusTrackerService.saveEnrolmentStatusTracker(e);
                    c.getEnrolledLearners().add(l);
                    classRunService.saveClassRun(c);

                    l.getClassRuns().add(c);
                    learnerService.saveLearner(l);

                    createNewLearnerTransaction(c.getClassRunId(), l.getLearnerId(), LearnerPaymentEnum.REMAININGCOURSEFEE, amount);
                    BigDecimal revenue = amount.divide(new BigDecimal(18),2, RoundingMode.CEILING);
                    Organisation org = c.getInstructor().getOrganisation();
                    orgLmsRevenueMapService.addRevenue(new OrgLmsRevenueMap(org.getOrganisationName(),revenue));
                    org.setOrgBalance(org.getOrgBalance().add(revenue.multiply(new BigDecimal(19))));
                    organisationService.saveOrganisation(org);
                } catch(DuplicateEnrolmentTrackerException ex) {
                    throw new EnrolmentStatusTrackerNotFoundException("Unexpected administration error has occured. Please contact our LMS Admin to sort out your duplicate record. ");
                }


            }else {
                throw new LearnerNotFoundException("Unable to find learner");
            }
        } else {
            throw new ClassRunNotFoundException("Unable to find class run. ");
        }

    }


    @Override
    public void requestRefund(Long classRunId, Long learnerId,BigDecimal amount) throws
            ClassRunNotFoundException, LearnerNotFoundException,
            EnrolmentStatusTrackerNotFoundException {
        ClassRun c = classRunService.retrieveClassRunById(classRunId);
        if (c != null) {
            Learner l = learnerService.getLearnerById(learnerId);
            if(l != null) {
                try {
                    EnrolmentStatusTracker e = enrolmentStatusTrackerService.retrieveEnrolmentByLearnerIdAndClassRunId(classRunId, learnerId);
                    e.setEnrolmentStatus(EnrolmentStatusTrackerEnum.REFUNDREQUEST);
                    enrolmentStatusTrackerService.saveEnrolmentStatusTracker(e);

                    DepositRefundRequest refundRequest = new DepositRefundRequest(learnerId,amount);
                    depositRefundRequestService.saveRefundRequest(refundRequest);

                } catch(DuplicateEnrolmentTrackerException ex) {
                    throw new EnrolmentStatusTrackerNotFoundException("Unexpected administration error has occured. Please contact our LMS Admin to sort out your duplicate record. ");
                }


            }else {
                throw new LearnerNotFoundException("Unable to find learner");
            }
        } else {
            throw new ClassRunNotFoundException("Unable to find class run. ");
        }

    }

    @Override
    public void changeClassRunAndPaidCourseFee(Long currClassRunId, Long newClassRunId, Long learnerId,
                                               BigDecimal amount) throws ClassRunNotFoundException,
            LearnerNotFoundException, EnrolmentStatusTrackerNotFoundException {
        ClassRun oldClassRun = classRunService.retrieveClassRunById(currClassRunId);
        if (oldClassRun != null) {
            Learner learner = learnerService.getLearnerById(learnerId);
            if(learner != null) {
                try {
                    EnrolmentStatusTracker tracker = enrolmentStatusTrackerService.retrieveEnrolmentByLearnerIdAndClassRunId(currClassRunId, learnerId);
                    tracker.setEnrolmentStatus(EnrolmentStatusTrackerEnum.ENROLLED);
                    // change the tracker class run
                    ClassRun newClassRun = classRunService.retrieveClassRunById(newClassRunId);
                    tracker.setClassRun(newClassRun);
                    enrolmentStatusTrackerService.saveEnrolmentStatusTracker(tracker);

                    oldClassRun.getEnrolmentStatusTrackers().remove(tracker);
                    classRunService.saveClassRun(oldClassRun);

                    newClassRun.getEnrolmentStatusTrackers().add(tracker);
                    classRunService.saveClassRun(newClassRun);

                    newClassRun.getEnrolledLearners().add(learner);
                    classRunService.saveClassRun(newClassRun);

                    learner.getClassRuns().add(newClassRun);
                    learnerService.saveLearner(learner);

                    createNewLearnerTransaction(newClassRun.getClassRunId(), learner.getLearnerId(), LearnerPaymentEnum.REMAININGCOURSEFEE, amount);
                } catch(DuplicateEnrolmentTrackerException ex) {
                    throw new EnrolmentStatusTrackerNotFoundException("Unexpected administration error has occured." +
                            " Please contact our LMS Admin to sort out your duplicate record. ");
                }


            }else {
                throw new LearnerNotFoundException("Unable to find learner");
            }
        } else {
            throw new ClassRunNotFoundException("Unable to find class run. ");
        }
    }






}
