package com.educouch.educouchsystem.service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.LearnerTransactionRepository;
import com.educouch.educouchsystem.util.enumeration.EnrolmentStatusTrackerEnum;
import com.educouch.educouchsystem.util.enumeration.LearnerPaymentEnum;
import com.educouch.educouchsystem.util.exception.*;
import com.stripe.exception.*;
import com.stripe.param.PaymentIntentCreateParams;
import org.hibernate.Hibernate;
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
    GradeBookEntryService gradeBookEntryService;

    @Autowired
    DepositRefundRequestService depositRefundRequestService;

    @Autowired
    PointsWalletService pointsWalletService;

    @Autowired
    OrgLmsRevenueMapService orgLmsRevenueMapService;

    @Autowired
    AssessmentService assessmentService;

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

        // retrieve the points wallet
        Organisation o = c.getCourse().getOrganisation();

        Long currPoints = new Long(0);
        PointsWallet pw;
        try {
            pw = pointsWalletService.findParticularWallet(learnerId, o.getOrganisationId());
            currPoints = pw.getDiscountPoints();
        } catch(PointsWalletNotFoundException ex) {
            pw = pointsWalletService.createWallet(learnerId, o.getOrganisationId(), o.getOrganisationName());
        }

        if (c != null) {
            Learner l = learnerService.getLearnerById(learnerId);
            if(l != null) {
                try {
                    EnrolmentStatusTracker e = enrolmentStatusTrackerService.retrieveEnrolmentByLearnerIdAndClassRunId(classRunId, learnerId);
                    e.setEnrolmentStatus(EnrolmentStatusTrackerEnum.ENROLLED);

                    enrolmentStatusTrackerService.saveEnrolmentStatusTracker(e);
                    Hibernate.initialize(c.getEnrolledLearners());
                    c.getEnrolledLearners().size();
                    c.getEnrolledLearners().add(l);
                    classRunService.saveClassRun(c);

                    l.getClassRuns().add(c);
                    learnerService.saveLearner(l);

                    createNewLearnerTransaction(c.getClassRunId(), l.getLearnerId(), LearnerPaymentEnum.REMAININGCOURSEFEE, amount);
                    BigDecimal revenue = amount.divide(new BigDecimal(18), 2, RoundingMode.CEILING);
                    Organisation org = c.getInstructor().getOrganisation();
                    orgLmsRevenueMapService.addRevenue(new OrgLmsRevenueMap(org.getOrganisationName(), revenue));
                    org.setOrgBalance(org.getOrgBalance().add(revenue.multiply(new BigDecimal(19))));
                    organisationService.saveOrganisation(org);

                    List<Assessment> list = assessmentService.getAllAssessmentsByCourseId(c.getCourse().getCourseId());

                    for(Assessment a : list) {
                        GradeBookEntry g = new GradeBookEntry(c.getCourse().getCourseId(),learnerId,a.getAssessmentId(),a.getTitle(),a.getMaxScore());
                        gradeBookEntryService.createGradeBookEntry(g);
                    }

                    // update the discount points here
                    Map<String, Double> conversionRate = pointsWalletService.retrieveCourseConversionRate(c.getCourse().getCourseId());
                    Double nominal = conversionRate.get("currency");
                    Double equivalentPoint = conversionRate.get("points");
                    Double nominalDiscount = c.getCourse().getCourseFee().doubleValue() * 0.90 - amount.doubleValue();
                    Double pointsUsed = Math.floor(nominalDiscount / nominal * equivalentPoint);

                    if(currPoints != 0) {
                        // if points wallet is not newly created
                        currPoints = currPoints - new Long(pointsUsed.intValue());
                        pw.setDiscountPoints(currPoints);
                        pointsWalletService.saveWallet(pw);

                    }

                } catch(DuplicateEnrolmentTrackerException ex) {
                    throw new EnrolmentStatusTrackerNotFoundException("Unexpected administration error has occured. Please contact our LMS Admin to sort out your duplicate record. ");
                } catch (CourseNotFoundException e) {
                    throw new RuntimeException(e);
                }


            }else {
                throw new LearnerNotFoundException("Unable to find learner");
            }
        } else {
            throw new ClassRunNotFoundException("Unable to find class run. ");
        }

    }

    public void dataloaderEnrolment(Long learnerId, Long classRunId, BigDecimal courseFee) {
        try {
            this.payDeposit(classRunId, learnerId, courseFee.multiply(new BigDecimal(0.10)));
            this.payCourseFee(classRunId, learnerId, courseFee.multiply(new BigDecimal(0.90)));
        } catch(Exception ex) {
            System.out.println("Data loader failed.");
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
