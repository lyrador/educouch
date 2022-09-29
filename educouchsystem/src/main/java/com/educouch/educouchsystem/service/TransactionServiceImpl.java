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
    public Transaction createLearnerToLmsTransaction(Transaction transaction) {
        transaction.setPaymentType(PaymentTypeEnum.LEARNERTOLMS);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction createLmsToOrgTransaction(Transaction transaction) {
        transaction.setPaymentType(PaymentTypeEnum.LMSTOORG);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction createLmsToLearnerTransaction(Transaction transaction) {        transaction.setPaymentType(PaymentTypeEnum.LEARNERTOLMS);
        transaction.setPaymentType(PaymentTypeEnum.LMSTOLEARNER);
        return transactionRepository.save(transaction);    }

    @Override
    public List<Transaction> findAllLearnerToLms() {
        return transactionRepository.findAllLearnerToLms();
    }

    @Override
    public List<Transaction> findAllLmsToOrg() {
        return transactionRepository.findAllLmsToOrg();
    }

    @Override
    public List<Transaction> findAllLmsToLearner() {
        return transactionRepository.findAllLmsToLearner();
    }
}
