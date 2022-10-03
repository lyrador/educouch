package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.LearnerTransaction;
import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.service.LearnerTransactionService;
import com.educouch.educouchsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/learnerTransaction")
@CrossOrigin
public class LearnerTransactionController {


        private final LearnerTransactionService learnerTransactionService;

        @Autowired
        public LearnerTransactionController(LearnerTransactionService learnerTransactionService) {
            this.learnerTransactionService = learnerTransactionService;
        }

        @PostMapping("/createLearnerTransaction")
        public ResponseEntity<LearnerTransaction> createLearnerTransaction(@RequestBody LearnerTransaction transaction) {
            LearnerTransaction transaction1 = learnerTransactionService.createLearnerTransaction(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(transaction1);
        }


        @GetMapping("/getAllLearnerTransaction")
        public ResponseEntity<List<LearnerTransaction>> getAllLearnerTransaction() {
            return ResponseEntity.status(HttpStatus.OK).body(learnerTransactionService.findAllLearnerTransaction());
        }



}
