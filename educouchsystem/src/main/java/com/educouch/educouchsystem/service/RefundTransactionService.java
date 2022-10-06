package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.RefundTransaction;
import com.educouch.educouchsystem.util.exception.TransactionNotFoundException;

import java.util.List;

public interface RefundTransactionService {
    public RefundTransaction createTransaction(RefundTransaction transaction);
    public List<RefundTransaction> findAllTransactions();
    public RefundTransaction findTransactionById(Long transactionId) throws TransactionNotFoundException;

    public RefundTransaction updateTransaction(RefundTransaction transaction) throws TransactionNotFoundException;

    public void deleteTransaction(RefundTransaction transaction) throws TransactionNotFoundException;

}
