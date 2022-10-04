package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.PaymentTypeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private Long payTo;

    @Column(nullable = false)
    private Long payFrom;

    @Column(nullable = false)
    private PaymentTypeEnum paymentType;

    @Column(nullable = false)
    private LocalDateTime paymentTime;

    public Transaction() {
    }

    public Transaction(Long payTo, Long payFrom, PaymentTypeEnum paymentType, LocalDateTime paymentTime) {
        this.payTo = payTo;
        this.payFrom = payFrom;
        this.paymentType = paymentType;
        this.paymentTime = paymentTime;
    }
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getPayTo() {
        return payTo;
    }

    public void setPayTo(Long payTo) {
        this.payTo = payTo;
    }

    public Long getPayFrom() {
        return payFrom;
    }

    public void setPayFrom(Long payFrom) {
        this.payFrom = payFrom;
    }

    public PaymentTypeEnum getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEnum paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
}
