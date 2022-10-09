package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.OrgAdminApprovalReq;
import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.service.OrgLmsRevenueMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orgLmsRevenueMap")
@CrossOrigin
public class OrgLmsRevenueMapController {

    private final OrgLmsRevenueMapService orgLmsRevenueMapService;

    @Autowired
    public OrgLmsRevenueMapController(OrgLmsRevenueMapService orgLmsRevenueMapService){
        this.orgLmsRevenueMapService = orgLmsRevenueMapService;
    }

    @GetMapping("/getForOrgName")
    public ResponseEntity<OrgLmsRevenueMap> getForOrgName(@RequestParam String orgName) {
        return ResponseEntity.status(HttpStatus.OK).body(orgLmsRevenueMapService.findByOrgName(orgName));
    }
}
