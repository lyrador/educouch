package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.repository.TransactionRepository;
import com.educouch.educouchsystem.util.enumeration.PaymentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }


    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

}
