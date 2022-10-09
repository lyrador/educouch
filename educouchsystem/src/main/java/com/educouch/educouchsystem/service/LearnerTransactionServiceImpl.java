package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.LearnerTransaction;
import com.educouch.educouchsystem.repository.LearnerTransactionRepository;
import com.educouch.educouchsystem.util.enumeration.LearnerPaymentEnum;
import com.stripe.param.SetupIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LearnerTransactionServiceImpl implements LearnerTransactionService{

    @Autowired
    LearnerTransactionRepository learnerTransactionRepository;


    @Override
    public LearnerTransaction createLearnerTransaction(LearnerTransaction learnerTransaction) {
        return learnerTransactionRepository.save(learnerTransaction);
    }

    @Override
    public List<LearnerTransaction> findAllLearnerTransaction() {
        return learnerTransactionRepository.findAll();
    }
}
