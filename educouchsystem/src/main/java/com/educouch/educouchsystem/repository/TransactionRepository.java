package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.paymentType = 0")
    public List<Transaction> findAllLearnerToLms();

    @Query("SELECT t FROM Transaction t WHERE t.paymentType = 1")
    public List<Transaction> findAllLmsToOrg();

    @Query("SELECT t FROM Transaction t WHERE t.paymentType = 2")
    public List<Transaction> findAllLmsToLearner();
}
