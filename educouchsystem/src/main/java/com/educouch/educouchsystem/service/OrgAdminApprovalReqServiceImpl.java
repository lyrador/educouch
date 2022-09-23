package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OrgAdminApprovalReq;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.repository.OrgAdminApprovalReqRepository;
import com.educouch.educouchsystem.util.enumeration.ApprovalStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgAdminApprovalReqServiceImpl implements OrgAdminApprovalReqService{

    @Autowired
    private OrgAdminApprovalReqRepository orgAdminApprovalReqRepository;

    @Autowired
    private OrganisationService organisationService;

    @Override
    public OrgAdminApprovalReq createOrgAdminApprovalReq(OrgAdminApprovalReq orgAdminApprovalReq) {
        OrgAdminApprovalReq newOrgAdminApprovalReq = new OrgAdminApprovalReq(orgAdminApprovalReq.getAdminName(), orgAdminApprovalReq.getAdminEmail(), orgAdminApprovalReq.getAdminNumber(), orgAdminApprovalReq.getOrgName(), orgAdminApprovalReq.getOrgDescription(), orgAdminApprovalReq.getPaymentAcc(), orgAdminApprovalReq.getFileStorageName(), orgAdminApprovalReq.getUsername(), orgAdminApprovalReq.getPassword());
        return orgAdminApprovalReqRepository.save(newOrgAdminApprovalReq);
    }

    public List<OrgAdminApprovalReq> getAllOrgAdminApprovalReq(){
        return orgAdminApprovalReqRepository.findAll();
    }

    @Override
    public List<OrgAdminApprovalReq> getPendingOrgAdminApprovalReq() {
        return orgAdminApprovalReqRepository.findAllPending();
    }

    @Override
    public List<OrgAdminApprovalReq> getRejectedOrgAdminApprovalReq() {
        return orgAdminApprovalReqRepository.findAllRejected();
    }

    @Override
    public List<OrgAdminApprovalReq> getApprovedOrgAdminApprovalReq() {
        return orgAdminApprovalReqRepository.findAllAccepted();
    }

    @Override
    public OrgAdminApprovalReq rejectApprovalReq(OrgAdminApprovalReq orgAdminApprovalReq) {
        orgAdminApprovalReq.setApprovalStatusEnum(ApprovalStatusEnum.DECLINED);
        orgAdminApprovalReqRepository.save(orgAdminApprovalReq);
        return orgAdminApprovalReq;
    }

    @Override
    public OrgAdminApprovalReq approveApprovalReq(OrgAdminApprovalReq orgAdminApprovalReq) {
        orgAdminApprovalReq.setApprovalStatusEnum(ApprovalStatusEnum.ACCEPTED);
        orgAdminApprovalReqRepository.save(orgAdminApprovalReq);
        OrganisationAdmin newAdmin = new OrganisationAdmin(orgAdminApprovalReq.getAdminName(), orgAdminApprovalReq.getAdminEmail(), orgAdminApprovalReq.getPassword(),orgAdminApprovalReq.getUsername());
        Organisation newOrg = new Organisation(orgAdminApprovalReq.getOrgName());
        organisationService.instantiateOrganisation(newAdmin, newOrg);
        return orgAdminApprovalReq;
    }

}
