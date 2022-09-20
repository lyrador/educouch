package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.OrgAdminApprovalReq;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrgAdminApprovalReqRepository extends CrudRepository<OrgAdminApprovalReq, Long> {
    public List<OrgAdminApprovalReq> findAll();

}
