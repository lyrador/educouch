package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TransactionService {
    public Transaction createTransaction(Transaction transaction);
    public List<Transaction> findAllTransactions();

}
