package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.LmsRevenueReport;
import com.educouch.educouchsystem.service.LmsRevenueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lmsRevenueReport")
@CrossOrigin
public class LmsRevenueReportController {
    private final LmsRevenueReportService lmsRevenueReportService;
@Autowired
    public LmsRevenueReportController(LmsRevenueReportService lmsRevenueReportService) {
        this.lmsRevenueReportService = lmsRevenueReportService;
    }

    @GetMapping("/getAllReports")
    public ResponseEntity<List<LmsRevenueReport>> getAllReports() {
    return ResponseEntity.status(HttpStatus.OK).body(lmsRevenueReportService.findAllLmsRevenueReport());
    }
}
