package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/createLmsToOrgTransaction")
    public ResponseEntity<Transaction> learnerToLmsTransaction(@RequestBody Transaction transaction) {
        Transaction transaction1 = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(transaction1);
    }


    @GetMapping("/getAllLmsToOrgTransaction")
    public ResponseEntity<List<Transaction>> getAllLearnerToLms() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllTransactions());
    }


    @PostMapping("/payCourseDeposit")
    public String payCourseDeposit(@RequestBody Transaction transaction) {


        return "Successfully paid deposit.";
    }



}
