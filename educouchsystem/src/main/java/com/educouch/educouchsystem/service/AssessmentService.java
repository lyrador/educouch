package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;

import java.util.List;

public interface AssessmentService {

    public Assessment saveAssessment(Assessment assessment);

    public List<Assessment> getAllAssessments();

    public Assessment retrieveAssessmentById(Long assessmentId) throws AssessmentNotFoundException;

    public void deleteAssessment(Long assessmentId) throws AssessmentNotFoundException;

}
