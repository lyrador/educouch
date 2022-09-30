package com.educouch.educouchsystem.service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.educouch.educouchsystem.model.ChargeRequest;
import com.educouch.educouchsystem.model.Learner;
import com.stripe.exception.*;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;

import javax.annotation.PostConstruct;

@Service("stripeService")
public class StriveServiceImpl implements StripeService {

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




}
