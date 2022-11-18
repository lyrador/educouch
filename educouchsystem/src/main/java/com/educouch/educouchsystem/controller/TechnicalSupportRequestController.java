package com.educouch.educouchsystem.controller;


import com.educouch.educouchsystem.model.Announcement;
import com.educouch.educouchsystem.model.TechnicalSupportRequest;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.TechnicalSupportRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/technicalSupportRequest")
@CrossOrigin
public class TechnicalSupportRequestController {

    @Autowired
    TechnicalSupportRequestService technicalSupportRequestService;

    @Autowired
    private LearnerService learnerService;

    @Autowired
    private EducatorService educatorService;

    @GetMapping("/getTechnicalSupportRequestById/{technicalSupportRequestId}")
    public ResponseEntity<TechnicalSupportRequest> getTechnicalSupportRequestById(@PathVariable(value = "technicalSupportRequestId") Long technicalSupportRequestId) {
        try {
            TechnicalSupportRequest existingRequest = technicalSupportRequestService.retrieveTechnicalSupportRequestById(technicalSupportRequestId);
            return new ResponseEntity<TechnicalSupportRequest>(existingRequest, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteTechnicalSupportRequestById/{technicalSupportRequestId}")
    public ResponseEntity<HttpStatus> deleteTechnicalSupportRequestById(@PathVariable("technicalSupportRequestId") Long technicalSupportRequestId) {
        technicalSupportRequestService.deleteTechnicalSupportRequest(technicalSupportRequestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
