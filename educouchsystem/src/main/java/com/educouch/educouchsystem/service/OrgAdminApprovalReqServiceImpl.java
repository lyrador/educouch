package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OrgAdminApprovalReq;
import com.educouch.educouchsystem.repository.OrgAdminApprovalReqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgAdminApprovalReqServiceImpl implements OrgAdminApprovalReqService{

    @Autowired
    private OrgAdminApprovalReqRepository orgAdminApprovalReqRepository;

    @Override
    public OrgAdminApprovalReq createOrgAdminApprovalReq(OrgAdminApprovalReq orgAdminApprovalReq) {
        return orgAdminApprovalReqRepository.save(orgAdminApprovalReq);
    }

    public List<OrgAdminApprovalReq> getAllOrgAdminApprovalReq(){
        return orgAdminApprovalReqRepository.findAll();
    }

}
