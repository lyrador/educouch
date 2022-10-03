package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.LearnerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearnerTransactionRepository extends JpaRepository<LearnerTransaction, Long> {
    public List<LearnerTransaction> findAll();

}