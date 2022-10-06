package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.model.RefundTransaction;
import com.educouch.educouchsystem.repository.RefundTransactionRepository;
import com.educouch.educouchsystem.util.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RefundTransactionServiceImpl implements RefundTransactionService {

    @Autowired
    private RefundTransactionRepository refundTransactionRepository;

    @Override
    public RefundTransaction createTransaction(RefundTransaction transaction) {
        return refundTransactionRepository.save(transaction);
    }

    @Override
    public List<RefundTransaction> findAllTransactions() {
        return refundTransactionRepository.findAll();
    }

    @Override
    public RefundTransaction findTransactionById(Long transactionId) throws TransactionNotFoundException {
        if(refundTransactionRepository.findById(transactionId).isPresent()) {
            return refundTransactionRepository.findById(transactionId).get();
        } else {
            throw new TransactionNotFoundException("transaction not found!");

        }
    }

    @Override
    public RefundTransaction updateTransaction(RefundTransaction transaction) throws TransactionNotFoundException {
        RefundTransaction transactionToUpdate = findTransactionById(transaction.getRefundTransactionId());
        transactionToUpdate.setAmountPaid(transaction.getAmountPaid());
        refundTransactionRepository.save(transactionToUpdate);
        return transactionToUpdate;
    }

    @Override
    public void deleteTransaction(RefundTransaction transaction) throws TransactionNotFoundException {
        RefundTransaction transactionToDelete = findTransactionById(transaction.getRefundTransactionId());
        refundTransactionRepository.delete(transactionToDelete);
    }
}
