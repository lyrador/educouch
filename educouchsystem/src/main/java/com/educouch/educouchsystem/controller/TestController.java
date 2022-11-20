package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.LmsRevenueReport;
import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.service.LmsRevenueReportService;
import com.educouch.educouchsystem.service.OrgLmsRevenueMapService;
import com.educouch.educouchsystem.service.OrganisationService;
import com.educouch.educouchsystem.service.TransactionService;
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

    private final OrganisationService organisationService;

    private final TransactionService transactionService;


    @Autowired
    public TestController(OrgLmsRevenueMapService orgLmsRevenueMapService, LmsRevenueReportService lmsRevenueReportService, OrganisationService organisationService, TransactionService transactionService) {
        this.orgLmsRevenueMapService = orgLmsRevenueMapService;
        this.lmsRevenueReportService = lmsRevenueReportService;
        this.organisationService = organisationService;
        this.transactionService = transactionService;
    }

    @PostMapping("/addOrgLmsRevenue")
    public ResponseEntity<OrgLmsRevenueMap> addRevenue(@RequestBody OrgLmsRevenueMap orgLmsRevenueMap) {
        return ResponseEntity.status(HttpStatus.OK).body(orgLmsRevenueMapService.addRevenue(orgLmsRevenueMap));
    }

    @GetMapping("/dayOneTestReport")
    public ResponseEntity<LmsRevenueReport> dayOneTest() {
        try {
            LmsRevenueReport report = lmsRevenueReportService.createReportAtStartOfMonth();
            transactionService.dayOneTransactionGeneration();

            orgLmsRevenueMapService.DbPurgeAtStartOfMonth();
            organisationService.courseStatsRefresh();


            return ResponseEntity.status(HttpStatus.OK).body(report);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (JRException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

    @GetMapping("/dayOneTestOrgReport")
    public ResponseEntity dayOneOrgTest() {
        try {
            transactionService.dayOneTransactionGeneration();
            organisationService.courseStatsRefresh();

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FileNotFoundException e) {
            System.out.println("no file issue");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (JRException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }



    }
//    @GetMapping("dayOneTestOrgDue")
//    public ResponseEntity<Object> dayOneTestOrgDue() {
//        organisationService.dayOneOrgScheduling();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @GetMapping("dayEightTestOrgDue")
//    public ResponseEntity<Object> dayOneTestOrgOverDue() {
//        organisationService.daySevenOrgScheduling();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
