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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
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
            newRequest.setImageUrl(requestDTO.getImageUrl());

            if (requestDTO.getRequestStatus().equals("PENDING")) {
                newRequest.setTechnicalSupportRequestStatus(TechnicalSupportRequestStatusEnum.PENDING);
            } else if (requestDTO.getRequestStatus().equals("RESOLVED")) {
                newRequest.setTechnicalSupportRequestStatus(TechnicalSupportRequestStatusEnum.RESOLVED);
            }

            //if created by learner, find the learner via learnerId and set it as the creator of forum, else it will be educator
            if (requestDTO.getCreatedByUserType().equals("LEARNER")) {
                newRequest.setCreatedByLearner(learnerService.getLearnerById(requestDTO.getCreatedByUserId()));
            } else if (requestDTO.getCreatedByUserType().equals("INSTRUCTOR")) {
                newRequest.setCreatedByInstructor(educatorService.findInstructorById(requestDTO.getCreatedByUserId()));
            } else if (requestDTO.getCreatedByUserType().equals("ORG_ADMIN")) {
                newRequest.setCreatedByOrganisationAdmin(educatorService.findOrganisationAdminById(requestDTO.getCreatedByUserId()));
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

    @GetMapping("/getAllTechnicalSupportRequestsByLearner/{userId}")
    public ResponseEntity<List<TechnicalSupportRequestDTO>> getAllTechnicalSupportRequestsByLearnerUserId (@PathVariable(value="userId") Long userId) {
        try {
            List<TechnicalSupportRequest> requests = new ArrayList<TechnicalSupportRequest>();
            requests.addAll(learnerService.getLearnerById(userId).getRequests());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<TechnicalSupportRequestDTO> requestDTOs = new ArrayList<>();
            for (TechnicalSupportRequest request : requests) {
                TechnicalSupportRequestDTO requestDTO = new TechnicalSupportRequestDTO();
                requestDTO.setRequestId(request.getTechnicalSupportRequestId());
                requestDTO.setRequestTitle(request.getTitle());
                requestDTO.setRequestDescription(request.getDescription());
                requestDTO.setImageUrl(request.getImageUrl());
                requestDTO.setCreatedDateTime(request.getTimestamp().format(formatter));
                if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.PENDING) {
                    requestDTO.setRequestStatus("PENDING");
                } else if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.RESOLVED) {
                    requestDTO.setRequestStatus("RESOLVED");
                }

                if (request.getCreatedByLearner() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByLearner().getLearnerId());
                    requestDTO.setCreatedByUserName(request.getCreatedByLearner().getName());
                    requestDTO.setCreatedByUserType("LEARNER");
                }
                requestDTOs.add(requestDTO);
            }
            return new ResponseEntity<>(requestDTOs, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllTechnicalSupportRequestsByInstructor/{userId}")
    public ResponseEntity<List<TechnicalSupportRequestDTO>> getAllTechnicalSupportRequestsByInstructorUserId (@PathVariable(value="userId") Long userId) {
        try {
            List<TechnicalSupportRequest> requests = new ArrayList<TechnicalSupportRequest>();
            requests.addAll(educatorService.findInstructorById(userId).getRequests());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<TechnicalSupportRequestDTO> requestDTOs = new ArrayList<>();
            for (TechnicalSupportRequest request : requests) {
                TechnicalSupportRequestDTO requestDTO = new TechnicalSupportRequestDTO();
                requestDTO.setRequestId(request.getTechnicalSupportRequestId());
                requestDTO.setRequestTitle(request.getTitle());
                requestDTO.setRequestDescription(request.getDescription());
                requestDTO.setImageUrl(request.getImageUrl());
                requestDTO.setCreatedDateTime(request.getTimestamp().format(formatter));
                if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.PENDING) {
                    requestDTO.setRequestStatus("PENDING");
                } else if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.RESOLVED) {
                    requestDTO.setRequestStatus("RESOLVED");
                }

                if (request.getCreatedByInstructor() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByInstructor().getInstructorId());
                    requestDTO.setCreatedByUserName(request.getCreatedByInstructor().getName());
                    requestDTO.setCreatedByUserType("INSTRUCTOR");
                }
                requestDTOs.add(requestDTO);
            }
            return new ResponseEntity<>(requestDTOs, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    @GetMapping("/getAllTechnicalSupportRequestsByOrgAdmin/{userId}")
    public ResponseEntity<List<TechnicalSupportRequestDTO>> getAllTechnicalSupportRequestsByOrgAdminUserId (@PathVariable(value="userId") Long userId) {
        try {
            List<TechnicalSupportRequest> requests = new ArrayList<TechnicalSupportRequest>();
            requests.addAll(educatorService.findOrganisationAdminById(userId).getRequests());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<TechnicalSupportRequestDTO> requestDTOs = new ArrayList<>();
            for (TechnicalSupportRequest request : requests) {
                TechnicalSupportRequestDTO requestDTO = new TechnicalSupportRequestDTO();
                requestDTO.setRequestId(request.getTechnicalSupportRequestId());
                requestDTO.setRequestTitle(request.getTitle());
                requestDTO.setRequestDescription(request.getDescription());
                requestDTO.setImageUrl(request.getImageUrl());
                requestDTO.setCreatedDateTime(request.getTimestamp().format(formatter));
                if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.PENDING) {
                    requestDTO.setRequestStatus("PENDING");
                } else if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.RESOLVED) {
                    requestDTO.setRequestStatus("RESOLVED");
                }

                if (request.getCreatedByOrganisationAdmin() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByOrganisationAdmin().getOrganisationAdminId());
                    requestDTO.setCreatedByUserName(request.getCreatedByOrganisationAdmin().getName());
                    requestDTO.setCreatedByUserType("ORG_ADMIN");
                }
                requestDTOs.add(requestDTO);
            }
            return new ResponseEntity<>(requestDTOs, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllPendingTechnicalSupportRequests")
    public ResponseEntity<List<TechnicalSupportRequestDTO>> getAllPendingTechnicalSupportRequests () {
        try {
            List<TechnicalSupportRequest> requests = new ArrayList<TechnicalSupportRequest>();
            requests.addAll(technicalSupportRequestService.getAllTechnicalSupportRequests());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<TechnicalSupportRequestDTO> requestDTOs = new ArrayList<>();
            for (TechnicalSupportRequest request : requests) {
                TechnicalSupportRequestDTO requestDTO = new TechnicalSupportRequestDTO();
                requestDTO.setRequestId(request.getTechnicalSupportRequestId());
                requestDTO.setRequestTitle(request.getTitle());
                requestDTO.setRequestDescription(request.getDescription());
                requestDTO.setImageUrl(request.getImageUrl());
                requestDTO.setCreatedDateTime(request.getTimestamp().format(formatter));
                if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.PENDING) {
                    requestDTO.setRequestStatus("PENDING");
                } else if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.RESOLVED) {
                    requestDTO.setRequestStatus("RESOLVED");
                }
                if (request.getCreatedByLearner() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByLearner().getLearnerId());
                    requestDTO.setCreatedByUserName(request.getCreatedByLearner().getName());
                    requestDTO.setCreatedByUserType("LEARNER");
                }
                if (request.getCreatedByInstructor() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByInstructor().getInstructorId());
                    requestDTO.setCreatedByUserName(request.getCreatedByInstructor().getName());
                    requestDTO.setCreatedByUserType("INSTRUCTOR");
                }
                if (request.getCreatedByOrganisationAdmin() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByOrganisationAdmin().getOrganisationAdminId());
                    requestDTO.setCreatedByUserName(request.getCreatedByOrganisationAdmin().getName());
                    requestDTO.setCreatedByUserType("ORG_ADMIN");
                }

                if (requestDTO.getRequestStatus().equals("PENDING")) {
                    requestDTOs.add(requestDTO);
                }
            }
            return new ResponseEntity<>(requestDTOs, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllResolvedTechnicalSupportRequests")
    public ResponseEntity<List<TechnicalSupportRequestDTO>> getAllResolvedTechnicalSupportRequests () {
        try {
            List<TechnicalSupportRequest> requests = new ArrayList<TechnicalSupportRequest>();
            requests.addAll(technicalSupportRequestService.getAllTechnicalSupportRequests());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<TechnicalSupportRequestDTO> requestDTOs = new ArrayList<>();
            for (TechnicalSupportRequest request : requests) {
                TechnicalSupportRequestDTO requestDTO = new TechnicalSupportRequestDTO();
                requestDTO.setRequestId(request.getTechnicalSupportRequestId());
                requestDTO.setRequestTitle(request.getTitle());
                requestDTO.setRequestDescription(request.getDescription());
                requestDTO.setImageUrl(request.getImageUrl());
                requestDTO.setCreatedDateTime(request.getTimestamp().format(formatter));
                if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.PENDING) {
                    requestDTO.setRequestStatus("PENDING");
                } else if (request.getTechnicalSupportRequestStatus() == TechnicalSupportRequestStatusEnum.RESOLVED) {
                    requestDTO.setRequestStatus("RESOLVED");
                }
                if (request.getCreatedByLearner() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByLearner().getLearnerId());
                    requestDTO.setCreatedByUserName(request.getCreatedByLearner().getName());
                    requestDTO.setCreatedByUserType("LEARNER");
                }
                if (request.getCreatedByInstructor() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByInstructor().getInstructorId());
                    requestDTO.setCreatedByUserName(request.getCreatedByInstructor().getName());
                    requestDTO.setCreatedByUserType("INSTRUCTOR");
                }
                if (request.getCreatedByOrganisationAdmin() != null) {
                    requestDTO.setCreatedByUserId(request.getCreatedByOrganisationAdmin().getOrganisationAdminId());
                    requestDTO.setCreatedByUserName(request.getCreatedByOrganisationAdmin().getName());
                    requestDTO.setCreatedByUserType("ORG_ADMIN");
                }

                if (requestDTO.getRequestStatus().equals("RESOLVED")) {
                    requestDTOs.add(requestDTO);
                }
            }
            return new ResponseEntity<>(requestDTOs, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateRequestStatus/{requestId}")
    public ResponseEntity<TechnicalSupportRequest> updateRequestStatusById(@PathVariable("requestId") Long requestId, @RequestBody TechnicalSupportRequestDTO requestDTO) {
        try {
            TechnicalSupportRequest existingRequest = technicalSupportRequestService.retrieveTechnicalSupportRequestById(requestId);
            if (requestDTO.getCreatedByUserType() == "LEARNER") {
                existingRequest.getCreatedByLearner().setClassRuns(new ArrayList<>());
            } else if (requestDTO.getCreatedByUserType() == "INSTRUCTOR") {
                existingRequest.getCreatedByLearner().setClassRuns(new ArrayList<>());
            }
            if (requestDTO.getRequestStatus().equals("RESOLVED")) {
                existingRequest.setTechnicalSupportRequestStatus(TechnicalSupportRequestStatusEnum.RESOLVED);
            }
            return new ResponseEntity<TechnicalSupportRequest>(technicalSupportRequestService.saveTechnicalSupportRequest(existingRequest), HttpStatus.OK);
        } catch (NoSuchElementException | TechnicalSupportRequestNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
