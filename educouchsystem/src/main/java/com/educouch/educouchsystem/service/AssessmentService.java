package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Assessment;
import java.util.List;

public interface AssessmentService {

    public Assessment saveAssessment(Assessment assessment);

    public List<Assessment> getAllAssessments();

    public Assessment retrieveAssessmentById(Long assessmentId);

    public void deleteAssessment(Long assessmentId);

}
