package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.RefundTransaction;
import com.educouch.educouchsystem.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RefundTransactionRepository extends CrudRepository<RefundTransaction, Long> {
    public List<RefundTransaction> findAll();
}
