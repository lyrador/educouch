package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;

import java.util.List;

public interface AssessmentService {

    public Assessment saveAssessment(Assessment assessment);

    public List<Assessment> getAllAssessments();

    public Assessment retrieveAssessmentById(Long assessmentId) throws AssessmentNotFoundException;

    public List<Assessment> getAllAssessmentsByCourseId(Long courseId) throws CourseNotFoundException;

    List<Assessment> getAllReleasedAssessmentsByCourseId(Long courseId) throws CourseNotFoundException;

    public void deleteAssessment(Long assessmentId) throws AssessmentNotFoundException;

    public void deleteAssessmentFromCourseId(Long assessmentId, Long courseId) throws AssessmentNotFoundException, CourseNotFoundException;
}
