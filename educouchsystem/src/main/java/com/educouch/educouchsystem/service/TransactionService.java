package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.util.exception.TransactionNotFoundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
public interface TransactionService {
    public Transaction createTransaction(Long organisationId) throws FileNotFoundException, JRException;
    public List<Transaction> findAllTransactions();

    public void dayOneTransactionGeneration() throws JRException, FileNotFoundException;
    public Transaction findTransactionById(Long transactionId) throws TransactionNotFoundException;

    public Transaction updateTransaction(Transaction transaction) throws TransactionNotFoundException;

    public void deleteTransaction(Transaction transaction) throws TransactionNotFoundException;

}
