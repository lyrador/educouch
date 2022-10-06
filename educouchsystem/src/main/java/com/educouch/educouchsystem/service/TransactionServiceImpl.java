package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.repository.TransactionRepository;
import com.educouch.educouchsystem.util.enumeration.PaymentTypeEnum;
import com.educouch.educouchsystem.util.exception.TransactionNotFoundException;
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
    @Override
    public Transaction findTransactionById(Long transactionId) throws TransactionNotFoundException{
        if(transactionRepository.findById(transactionId).isPresent()) {
            return transactionRepository.findById(transactionId).get();
        } else {
            throw new TransactionNotFoundException("transaction not found!");

        }
    }
    @Override
    public Transaction updateTransaction(Transaction transaction) throws TransactionNotFoundException {
        Transaction transactionToUpdate = findTransactionById(transaction.getTransactionId());
        transactionToUpdate.setAmountPaid(transaction.getAmountPaid());
        transactionRepository.save(transactionToUpdate);
        return transactionToUpdate;
    }

    @Override
    public void deleteTransaction(Transaction transaction) throws TransactionNotFoundException{
        Transaction transactionToDelete = findTransactionById(transaction.getTransactionId());
        transactionRepository.delete(transactionToDelete);

    }

}
