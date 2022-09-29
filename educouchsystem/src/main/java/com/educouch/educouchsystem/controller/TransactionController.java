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

    @PostMapping("/createLearnerToLmsTransaction")
    public ResponseEntity<Transaction> learnerToLmsTransaction(@RequestBody Transaction transaction) {
        Transaction transaction1 = transactionService.createLearnerToLmsTransaction(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(transaction1);
    }

    @PostMapping("/createLmsToOrgTransaction")
    public ResponseEntity<Transaction> lmsToOrgTransaction(@RequestBody Transaction transaction) {
        Transaction transaction1 = transactionService.createLmsToOrgTransaction(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(transaction1);
    }

    @PostMapping("/createLmsToLearnerTransaction")
    public ResponseEntity<Transaction> lmsToLearnerTransaction(@RequestBody Transaction transaction) {
        Transaction transaction1 = transactionService.createLmsToLearnerTransaction(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(transaction1);
    }

    @GetMapping("/getAllLearnerToLms")
    public ResponseEntity<List<Transaction>> getAllLearnerToLms() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllLearnerToLms());
    }

    @GetMapping("/getAllLmsToOrg")
    public ResponseEntity<List<Transaction>> getAllLmsToOrg() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllLmsToOrg());
    }

    @GetMapping("/getAllLmsToLearner")
    public ResponseEntity<List<Transaction>> getAllLmsToLearner() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllLmsToLearner());
    }

}
