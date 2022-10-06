package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.RefundStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class DepositRefundRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long RefundRequestId;

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDateTime dueTime;

    @Column(nullable = false)
    private RefundStatusEnum refundStatusEnum;

    @Column(nullable = false)
    private Long learnerId;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    public DepositRefundRequest() {
        this.refundStatusEnum = RefundStatusEnum.REQUESTED;
        this.timeStamp = LocalDateTime.now();
        this.dueTime = timeStamp.plusDays(5);
    }

    public DepositRefundRequest(Long learnerId, BigDecimal amount) {
        this();
        this.learnerId = learnerId;
        this.amount = amount;
    }

    public Long getRefundRequestId() {
        return RefundRequestId;
    }

    public void setRefundRequestId(Long refundRequestId) {
        RefundRequestId = refundRequestId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDateTime dueTime) {
        this.dueTime = dueTime;
    }

    public RefundStatusEnum getRefundStatusEnum() {
        return refundStatusEnum;
    }

    public void setRefundStatusEnum(RefundStatusEnum refundStatusEnum) {
        this.refundStatusEnum = refundStatusEnum;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
