package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.util.enumeration.PaymentStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

public class OrganisationDTO {

    private Long organisationId;
    private String organisationName;
//    private List<Instructor> instructors;
//    private List<Course> courses;
    private String orgDescription;
    private String paymentAcc;
    private BigDecimal orgBalance;
    private PaymentStatusEnum paymentStatus;
    private Double rewardPointsConversionNumber;
    private Double currencyConversionNumber;

    private Double maxAssignmentPoints;

    public OrganisationDTO() {
    }

    public OrganisationDTO(Long organisationId, String organisationName, String orgDescription, String paymentAcc, BigDecimal orgBalance, PaymentStatusEnum paymentStatus, Double rewardPointsConversionNumber, Double currencyConversionNumber,Double maxAssignmentPoints) {
        this.organisationId = organisationId;
        this.organisationName = organisationName;
        this.orgDescription = orgDescription;
        this.paymentAcc = paymentAcc;
        this.orgBalance = orgBalance;
        this.paymentStatus = paymentStatus;
        this.rewardPointsConversionNumber = rewardPointsConversionNumber;
        this.currencyConversionNumber = currencyConversionNumber;
        this.maxAssignmentPoints = maxAssignmentPoints;
    }

    public Double getMaxAssignmentPoints() {
        return maxAssignmentPoints;
    }

    public void setMaxAssignmentPoints(Double maxAssignmentPoints) {
        this.maxAssignmentPoints = maxAssignmentPoints;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getPaymentAcc() {
        return paymentAcc;
    }

    public void setPaymentAcc(String paymentAcc) {
        this.paymentAcc = paymentAcc;
    }

    public BigDecimal getOrgBalance() {
        return orgBalance;
    }

    public void setOrgBalance(BigDecimal orgBalance) {
        this.orgBalance = orgBalance;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getRewardPointsConversionNumber() {
        return rewardPointsConversionNumber;
    }

    public void setRewardPointsConversionNumber(Double rewardPointsConversionNumber) {
        this.rewardPointsConversionNumber = rewardPointsConversionNumber;
    }

    public Double getCurrencyConversionNumber() {
        return currencyConversionNumber;
    }

    public void setCurrencyConversionNumber(Double currencyConversionNumber) {
        this.currencyConversionNumber = currencyConversionNumber;
    }
}
