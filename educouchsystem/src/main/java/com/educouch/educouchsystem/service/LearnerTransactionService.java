package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.LearnerTransaction;

import java.util.List;

public interface LearnerTransactionService {
    public LearnerTransaction createLearnerTransaction(LearnerTransaction learnerTransaction);

    public List<LearnerTransaction> findAllLearnerTransaction();
}
