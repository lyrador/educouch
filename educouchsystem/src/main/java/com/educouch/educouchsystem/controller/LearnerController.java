package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.LearnerDTO;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//so it will get response body and controller at the same time
@RestController
//giving learner path here
@RequestMapping("/learner")
//tells the react and springboot application to connect to each other
@CrossOrigin
public class LearnerController {
    @Autowired
    //inject learnerService here
    private LearnerService learnerService;

    //giving path here
    @PostMapping("/add")
    public String add(@RequestBody LearnerDTO learnerDTO) {
        Boolean isKid = true;
        if (learnerDTO.getIsKid().equals("false")) {
            isKid = false;
        }
        learnerService.saveLearner(
                new Learner(learnerDTO.getName(), learnerDTO.getEmail(), learnerDTO.getPassword(), learnerDTO.getUsername(), learnerDTO.getProfilePictureURL(), isKid)
        );
        return "New learner is added";
    }

    @GetMapping("/getAll")
    public List<Learner> getAllLearners(){
        return learnerService.getAllLearners();
    }

    @PostMapping("/update")
    public ResponseEntity<Learner> updateLearner(@RequestBody Learner learner) {
        Learner updatedLearner = learnerService.updateLearner(learner);
        return ResponseEntity.status(HttpStatus.OK).body(updatedLearner);
    }

    @GetMapping("/learnerEnrolled")
    @ResponseBody
    public Boolean learnerEnrolled(@RequestParam String courseId, @RequestParam String learnerId) {
        return true;

    }

    @GetMapping("/getById")
    public ResponseEntity<Learner> getLearnerById(@RequestParam Long learnerId) {
        return ResponseEntity.status(HttpStatus.OK).body(learnerService.getLearnerById(learnerId));


    }
}
