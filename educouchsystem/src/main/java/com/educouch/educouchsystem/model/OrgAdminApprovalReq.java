package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.ApprovalStatusEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class OrgAdminApprovalReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgAdminApprovalId;

    private String adminName;

    private String adminEmail;

    private String adminNumber;

    private String orgName;

    private String orgDescription;

    private String paymentAcc;

    private ApprovalStatusEnum approvalStatusEnum;


    public OrgAdminApprovalReq() {
    }

    public OrgAdminApprovalReq(String adminName, String adminEmail, String adminNumber, String orgName, String orgDescription, String paymentAcc) {
        this.approvalStatusEnum = ApprovalStatusEnum.PENDING;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminNumber = adminNumber;
        this.orgName = orgName;
        this.orgDescription = orgDescription;
        this.paymentAcc = paymentAcc;
    }

    public Long getOrgAdminApprovalId() {
        return orgAdminApprovalId;
    }

    public void setOrgAdminApprovalId(Long orgAdminApprovalId) {
        this.orgAdminApprovalId = orgAdminApprovalId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public ApprovalStatusEnum getApprovalStatusEnum() {
        return approvalStatusEnum;
    }

    public void setApprovalStatusEnum(ApprovalStatusEnum approvalStatusEnum) {
        this.approvalStatusEnum = approvalStatusEnum;
    }

}
