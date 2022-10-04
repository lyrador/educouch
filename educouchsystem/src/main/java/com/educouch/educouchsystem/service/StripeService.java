package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ChargeRequest;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.util.enumeration.LearnerPaymentEnum;
import com.educouch.educouchsystem.util.exception.ClassRunNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;

import java.math.BigDecimal;

public interface StripeService {
    public PaymentIntent createPaymentIntent(BigDecimal amount) throws StripeException;
    public void payDeposit(Long classRunId, Long learnerId,
                           LearnerPaymentEnum transactionType, BigDecimal amount) throws
            ClassRunNotFoundException, LearnerNotFoundException;
}
