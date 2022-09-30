package com.educouch.educouchsystem.service;
import java.math.BigDecimal;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.EnrolmentStatusTracker;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.LearnerTransaction;
import com.educouch.educouchsystem.repository.LearnerTransactionRepository;
import com.educouch.educouchsystem.util.enumeration.LearnerPaymentEnum;
import com.educouch.educouchsystem.util.exception.ClassRunNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;
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
    EnrolmentStatusTrackerService enrolmentStatusTrackerService;

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
                LearnerTransaction transaction = new LearnerTransaction(amount, transactionType);
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
    public void payDeposit(Long classRunId, Long learnerId,
                                         LearnerPaymentEnum transactionType, BigDecimal amount) throws
            ClassRunNotFoundException, LearnerNotFoundException {
        ClassRun c = classRunService.retrieveClassRunById(classRunId);
        if (c != null) {
            Learner l = learnerService.getLearnerById(learnerId);
            if(l != null) {
                EnrolmentStatusTracker e = new EnrolmentStatusTracker(c, l);
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




}
