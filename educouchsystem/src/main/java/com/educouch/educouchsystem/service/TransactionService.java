package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TransactionService {
    public Transaction createLearnerToLmsTransaction(Transaction transaction);

    public Transaction createLmsToOrgTransaction(Transaction transaction);

    public Transaction createLmsToLearnerTransaction(Transaction transaction);

    public List<Transaction> findAllLearnerToLms();

    public List<Transaction> findAllLmsToOrg();

    public List<Transaction> findAllLmsToLearner();
}
