package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    public List<Transaction> findAll();
}
