package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.service.TransactionService;
import com.educouch.educouchsystem.util.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/updateTransaction")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction)  {
        Transaction transaction1 = null;
        try {
            transaction1 = transactionService.updateTransaction(transaction);
        } catch (TransactionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find Transaction", e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(transaction1);
    }

    @PostMapping("/deleteTransaction")
    public ResponseEntity<Long> deleteTransaction(@RequestBody Transaction transaction)  {
        try {
    transactionService.deleteTransaction(transaction);
        } catch (TransactionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find Transaction", e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(transaction.getTransactionId());
    }


    @PostMapping("/payCourseDeposit")
    public String payCourseDeposit(@RequestBody Transaction transaction) {


        return "Successfully paid deposit.";
    }



}
