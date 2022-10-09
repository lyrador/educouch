package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.LmsRevenueReport;
import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.service.LmsRevenueReportService;
import com.educouch.educouchsystem.service.OrgLmsRevenueMapService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    private final OrgLmsRevenueMapService orgLmsRevenueMapService;

    private final LmsRevenueReportService lmsRevenueReportService;

@Autowired
    public TestController(OrgLmsRevenueMapService orgLmsRevenueMapService, LmsRevenueReportService lmsRevenueReportService) {
        this.orgLmsRevenueMapService = orgLmsRevenueMapService;
        this.lmsRevenueReportService = lmsRevenueReportService;
    }

    @PostMapping("/addOrgLmsRevenue")
    public ResponseEntity<OrgLmsRevenueMap> addRevenue(@RequestBody OrgLmsRevenueMap orgLmsRevenueMap) {
        return ResponseEntity.status(HttpStatus.OK).body(orgLmsRevenueMapService.addRevenue(orgLmsRevenueMap));
    }

    @GetMapping("/dayOneTest")
    public ResponseEntity<LmsRevenueReport> dayOneTest() {
        try {
            LmsRevenueReport report = lmsRevenueReportService.createReportAtStartOfMonth();
            orgLmsRevenueMapService.DbPurgeAtStartOfMonth();

            return ResponseEntity.status(HttpStatus.OK).body(report);
        } catch (FileNotFoundException e) {
return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (JRException e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
