package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.LmsAdmin;
import com.educouch.educouchsystem.model.OrgAdminApprovalReq;
import com.educouch.educouchsystem.service.LmsAdminService;
import com.educouch.educouchsystem.service.OrgAdminApprovalReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orgAdminApprovalReq")
@CrossOrigin
public class OrgAdminApprovalReqController {
    private final OrgAdminApprovalReqService orgAdminApprovalReqService;

    @Autowired
    public OrgAdminApprovalReqController(OrgAdminApprovalReqService orgAdminApprovalReqService){
        this.orgAdminApprovalReqService = orgAdminApprovalReqService;
    }

    @PostMapping("/addOrgAdminApprovalReqService")
    public ResponseEntity<OrgAdminApprovalReq> createOrgAdminApprovalReqService(@RequestBody OrgAdminApprovalReq orgAdminApprovalReq) {
        OrgAdminApprovalReq newApproval = orgAdminApprovalReqService.createOrgAdminApprovalReq(orgAdminApprovalReq);
        return ResponseEntity.status(HttpStatus.OK).body(newApproval);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrgAdminApprovalReq>> getAllOrgAdminApprovalReq() {
        return ResponseEntity.status(HttpStatus.OK).body(orgAdminApprovalReqService.getAllOrgAdminApprovalReq());
    }
}
