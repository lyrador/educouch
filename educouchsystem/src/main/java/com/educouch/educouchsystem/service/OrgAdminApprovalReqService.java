package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OrgAdminApprovalReq;

import java.util.List;

public interface OrgAdminApprovalReqService {

    public OrgAdminApprovalReq createOrgAdminApprovalReq(OrgAdminApprovalReq orgAdminApprovalReq);

    public List<OrgAdminApprovalReq> getAllOrgAdminApprovalReq();

    public List<OrgAdminApprovalReq> getPendingOrgAdminApprovalReq();

    public List<OrgAdminApprovalReq> getRejectedOrgAdminApprovalReq();

    public List<OrgAdminApprovalReq> getApprovedOrgAdminApprovalReq();

    public OrgAdminApprovalReq rejectApprovalReq(OrgAdminApprovalReq orgAdminApprovalReq);

    public OrgAdminApprovalReq approveApprovalReq(OrgAdminApprovalReq orgAdminApprovalReq);
}
