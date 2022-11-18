package com.educouch.educouchsystem.controller;


import com.educouch.educouchsystem.dto.TechnicalSupportRequestDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.TechnicalSupportRequestService;
import com.educouch.educouchsystem.util.enumeration.TechnicalSupportRequestStatusEnum;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import com.educouch.educouchsystem.util.exception.TechnicalSupportRequestNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/createTechnicalSupportRequest/{userId}")
    public ResponseEntity<TechnicalSupportRequest> createTechnicalSupportRequest(@PathVariable(value="userId") Long userId, @RequestBody TechnicalSupportRequestDTO requestDTO) {
        try {
            TechnicalSupportRequest newRequest = new TechnicalSupportRequest();
            newRequest.setTitle(requestDTO.getRequestTitle());
            newRequest.setDescription(requestDTO.getRequestDescription());
            newRequest.setTimestamp(LocalDateTime.now());

            if (requestDTO.getRequestStatus().equals("PENDING")) {
                newRequest.setTechnicalSupportRequestStatus(TechnicalSupportRequestStatusEnum.PENDING);
            } else if (requestDTO.getRequestStatus().equals("RESOLVED")) {
                newRequest.setTechnicalSupportRequestStatus(TechnicalSupportRequestStatusEnum.RESOLVED);
            }

            //if created by learner, find the learner via learnerId and set it as the creator of forum, else it will be educator
            if (requestDTO.getCreatedByUserType().equals("LEARNER")) {
                Learner learner = learnerService.getLearnerById(requestDTO.getCreatedByUserId());
                newRequest.setCreatedByLearner(learner);
                learner.getRequests().add(newRequest);
            } else if (requestDTO.getCreatedByUserType().equals("INSTRUCTOR")) {
                Instructor instructor = educatorService.findInstructorById(requestDTO.getCreatedByUserId());
                newRequest.setCreatedByInstructor(instructor);
                instructor.getRequests().add(newRequest);
            } else if (requestDTO.getCreatedByUserType().equals("ORG_ADMIN")) {
                OrganisationAdmin orgAdmin = educatorService.findOrganisationAdminById(requestDTO.getCreatedByUserId());
                newRequest.setCreatedByOrganisationAdmin(orgAdmin);
                orgAdmin.getRequests().add(newRequest);
            }
            TechnicalSupportRequest request = technicalSupportRequestService.saveTechnicalSupportRequest(newRequest);
            return new ResponseEntity<>(request, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (TechnicalSupportRequestNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
