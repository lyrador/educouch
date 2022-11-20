package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.PaymentTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private Long payTo;

    @Column(nullable = false)
    private String orgName;

    @Column(nullable = false)
    private String orgAccNumber;

    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private LocalDateTime paymentTime;

    @Column(nullable = false)
    private String reportUrl;

    @Column(nullable = false)
    private String fileStorageName;

    @Column(nullable = false, unique = true)
    private String monthYear;

    public Transaction() {
        this.paymentTime = LocalDateTime.now();

    }

    public Transaction(Long payTo, String orgName, String orgAccNumber, BigDecimal amountPaid) {
        this.payTo = payTo;
        this.orgName = orgName;
        this.orgAccNumber = orgAccNumber;
        this.amountPaid = amountPaid;
        this.paymentTime = LocalDateTime.now();
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getFileStorageName() {
        return fileStorageName;
    }

    public void setFileStorageName(String fileStorageName) {
        this.fileStorageName = fileStorageName;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAccNumber() {
        return orgAccNumber;
    }

    public void setOrgAccNumber(String orgAccNumber) {
        this.orgAccNumber = orgAccNumber;
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
