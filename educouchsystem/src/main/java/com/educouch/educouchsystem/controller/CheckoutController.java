package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.ChangeScheduleCourseEnrolment;
import com.educouch.educouchsystem.dto.ClientSecretHandler;
import com.educouch.educouchsystem.dto.LearnerPaymentTracker;
import com.educouch.educouchsystem.service.StripeService;
import com.educouch.educouchsystem.util.exception.ClassRunNotFoundException;
import com.educouch.educouchsystem.util.exception.EnrolmentStatusTrackerNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;
import com.educouch.educouchsystem.util.logger.LoggingController;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

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
    public String trackDepositPayment(@RequestBody LearnerPaymentTracker learnerPaymentTracker) {
        Long learnerId = new Long(learnerPaymentTracker.getLearnerId());
        Long classRunId = new Long(learnerPaymentTracker.getClassRunId());
        BigDecimal amount = new BigDecimal(learnerPaymentTracker.getAmount());

        try {
            stripeService.payDeposit(classRunId, learnerId, amount);
            return "Successfully recorded the deposit.";
        } catch(ClassRunNotFoundException | LearnerNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to make deposit record.", ex);
        }

    }

    @PostMapping("/trackRemainingCourseFee")
    public String trackRemainingCourseFee(@RequestBody LearnerPaymentTracker learnerPaymentTracker) {
        Long learnerId = new Long(learnerPaymentTracker.getLearnerId());
        Long classRunId = new Long(learnerPaymentTracker.getClassRunId());
        BigDecimal amount = new BigDecimal(learnerPaymentTracker.getAmount());

        try {
            stripeService.payCourseFee(classRunId, learnerId, amount);
            return "Successfully recorded the course fee payment.";
        } catch(ClassRunNotFoundException | LearnerNotFoundException | EnrolmentStatusTrackerNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find the correct record.", ex);
        }

    }

    @PostMapping("/refundDepositRequest")
    public String trackRefundDepositRequest(@RequestBody LearnerPaymentTracker learnerPaymentTracker) {
        Long learnerId = new Long(learnerPaymentTracker.getLearnerId());
        Long classRunId = new Long(learnerPaymentTracker.getClassRunId());
        BigDecimal amount = new BigDecimal(learnerPaymentTracker.getAmount());

        try {
            stripeService.requestRefund(classRunId, learnerId, amount);
            return "Successfully recorded the refund request. ";
        } catch(ClassRunNotFoundException | LearnerNotFoundException | EnrolmentStatusTrackerNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find the correct record.", ex);
        }

    }

    @PostMapping("/changeClassRunAndPayCourseFee")
    public String trackRefundDepositRequest(@RequestBody ChangeScheduleCourseEnrolment model) {
        Long oldClassRunId = new Long(model.getCurrClassRunId());
        Long newClassRunId = new Long(model.getNewClassRunId());
        Long learnerId = new Long(model.getLearnerId());
        BigDecimal amount = new BigDecimal(model.getAmount());

        try {
            stripeService.changeClassRunAndPaidCourseFee(oldClassRunId, newClassRunId, learnerId, amount);
            return "Successfully changed the class run and pay for course fee. ";
        } catch(ClassRunNotFoundException | LearnerNotFoundException | EnrolmentStatusTrackerNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find the correct record.", ex);
        }

    }



}
