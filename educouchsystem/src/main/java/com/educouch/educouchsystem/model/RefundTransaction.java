package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class RefundTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refundTransactionId;

    @Column(nullable = false)
    private Long learnerId;

    @Column(nullable = false)
    private Long refundRequestId;

    @Column(nullable = false)
    private String learnerAccNumber;

    @Column(nullable = false)
    private BigDecimal amountPaid;
    @Column(nullable = false)
    private LocalDateTime paymentTime;

    public RefundTransaction() {
    }

    public RefundTransaction(Long learnerId, String learnerAccNumber, BigDecimal amountPaid, Long refundRequestId) {
        this.learnerId = learnerId;
        this.learnerAccNumber = learnerAccNumber;
        this.amountPaid = amountPaid;
        this.paymentTime = LocalDateTime.now();
        this.refundRequestId = refundRequestId;
    }

    public Long getRefundRequestId() {
        return refundRequestId;
    }

    public void setRefundRequestId(Long refundRequestId) {
        this.refundRequestId = refundRequestId;
    }

    public Long getRefundTransactionId() {
        return refundTransactionId;
    }

    public void setRefundTransactionId(Long refundTransactionId) {
        this.refundTransactionId = refundTransactionId;
    }


    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public String getLearnerAccNumber() {
        return learnerAccNumber;
    }

    public void setLearnerAccNumber(String learnerAccNumber) {
        this.learnerAccNumber = learnerAccNumber;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
}
