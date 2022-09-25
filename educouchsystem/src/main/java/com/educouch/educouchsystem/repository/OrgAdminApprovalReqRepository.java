package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.OrgAdminApprovalReq;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrgAdminApprovalReqRepository extends CrudRepository<OrgAdminApprovalReq, Long> {
    public List<OrgAdminApprovalReq> findAll();
    @Query("SELECT admin FROM OrgAdminApprovalReq admin WHERE admin.approvalStatusEnum = 0")
    public List<OrgAdminApprovalReq> findAllPending();

    @Query("SELECT admin FROM OrgAdminApprovalReq admin WHERE admin.orgAdminApprovalId=:approvalId")
    public OrgAdminApprovalReq findPending(Long approvalId);

    @Query("SELECT admin FROM OrgAdminApprovalReq admin WHERE admin.approvalStatusEnum = 1")
    public List<OrgAdminApprovalReq> findAllAccepted();

    @Query("SELECT admin FROM OrgAdminApprovalReq admin WHERE admin.approvalStatusEnum = 2")
    public List<OrgAdminApprovalReq> findAllRejected();

}
