package com.educouch.educouchsystem.controller;

import ch.qos.logback.core.net.server.Client;
import com.educouch.educouchsystem.dto.ClientSecretHandler;
import com.educouch.educouchsystem.dto.DepositTracker;
import com.educouch.educouchsystem.model.ChargeRequest;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.service.StripeService;
import com.educouch.educouchsystem.util.exception.ClassRunNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;
import com.educouch.educouchsystem.util.logger.LoggingController;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
//giving learner path here
@RequestMapping("/payment")
//tells the react and springboot application to connect to each other
@CrossOrigin
public class CheckoutController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    String stripePublicKey = "pk_test_51LnPrnBx7BYbBg97eaxGtJNPIBG88wK36CGBCzldo5RmE5w3F9G7JKI7sOLafQB6yBdgfVsz6VHUpx5ja4LeVhp700UuLU3SOn";
    String privateKey = "sk_test_51LnPrnBx7BYbBg97Zhhw2HorPZqL5srUqYdFgcYuldnNJlMMO4shzH979NrcJ4NDJGHAg0IQ2KuhR19vnEIaUiF800k3CzgsHu";

    @Autowired
    private StripeService stripeService;
    @GetMapping("/checkout")
    public ClientSecretHandler checkout(@RequestParam String amount) {
        BigDecimal amountInBigDecimal = new BigDecimal(amount);
        try {
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInBigDecimal);
            ClientSecretHandler clientSecretHandler = new ClientSecretHandler(paymentIntent.getClientSecret());
            return clientSecretHandler;

        } catch(StripeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment cannot be processed. ", ex);
        }
    }

    @PostMapping("/trackDeposit")
    public String trackDepositPayment(@RequestBody DepositTracker depositTracker) {
        Long learnerId = new Long(depositTracker.getLearnerId());
        Long classRunId = new Long(depositTracker.getClassRunId());
        BigDecimal amount = new BigDecimal(depositTracker.getAmount());

        try {
            stripeService.payDeposit(classRunId, learnerId, amount);
            return "Successfully recorded the deposit.";
        } catch(ClassRunNotFoundException | LearnerNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to make deposit record.", ex);
        }

    }

}
