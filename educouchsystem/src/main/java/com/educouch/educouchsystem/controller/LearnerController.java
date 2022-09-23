package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.service.LearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public String add(@RequestBody Learner learner) {
        learnerService.saveLearner(learner);
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
}
