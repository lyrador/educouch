package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OrgAdminApprovalReq;

import java.util.List;

public interface OrgAdminApprovalReqService {

    public OrgAdminApprovalReq createOrgAdminApprovalReq(OrgAdminApprovalReq orgAdminApprovalReq);

    public List<OrgAdminApprovalReq> getAllOrgAdminApprovalReq();
}
