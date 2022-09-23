package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.service.AssessmentService;
import com.educouch.educouchsystem.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private AssessmentService assessmentService;
}
