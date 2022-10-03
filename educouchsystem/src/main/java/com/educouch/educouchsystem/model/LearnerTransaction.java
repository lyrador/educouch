package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.LearnerPaymentEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
public class LearnerTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learnerTransactionId;

    @Column(nullable = false)
    private LearnerPaymentEnum transactionType;

    @Column(nullable = false)
    private Long learnerId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private BigDecimal amount;

    public LearnerTransaction() {
        this.timestamp = LocalDateTime.now();
    }

    public LearnerTransaction(BigDecimal amount, LearnerPaymentEnum transactionType, Long learnerId) {
        new LearnerTransaction();
        this.learnerId = learnerId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public Long getLearnerTransactionId() {
        return learnerTransactionId;
    }

    public void setLearnerTransactionId(Long learnerTransactionId) {
        this.learnerTransactionId = learnerTransactionId;
    }

    public LearnerPaymentEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(LearnerPaymentEnum transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
