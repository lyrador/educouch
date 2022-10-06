package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.util.exception.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TransactionService {
    public Transaction createTransaction(Transaction transaction);
    public List<Transaction> findAllTransactions();
    public Transaction findTransactionById(Long transactionId) throws TransactionNotFoundException;

    public Transaction updateTransaction(Transaction transaction) throws TransactionNotFoundException;

    public void deleteTransaction(Transaction transaction) throws TransactionNotFoundException;

}
