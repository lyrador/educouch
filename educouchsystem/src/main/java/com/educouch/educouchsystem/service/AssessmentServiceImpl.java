package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Override
    public Assessment saveAssessment (Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    @Override
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    @Override
    public Assessment retrieveAssessmentById(Long assessmentId) {
        return assessmentRepository.findById(assessmentId).get();
    }

    @Override
    public void deleteAssessment(Long assessmentId) {
        assessmentRepository.deleteById(assessmentId);
    }
}
