package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.repository.AssessmentRepository;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private CourseService courseService;

    @Override
    public Assessment saveAssessment (Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    @Override
    public Assessment saveAssessment(Long courseId, Assessment assessment) throws CourseNotFoundException {
        Course course = courseService.getCourseById(courseId);
        if (course != null) {
            assessment.setAssessmentCourse(course);
            course.getAssessments().add(assessment);
            courseService.saveCourse(course);
            saveAssessment(assessment);
            return assessment;
        } else {
            throw new CourseNotFoundException("Course Id " + courseId + " does not exist!");
        }
    }

    @Override
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    @Override
    public Assessment retrieveAssessmentById(Long assessmentId) throws AssessmentNotFoundException {
        Assessment assessment = assessmentRepository.findById(assessmentId).get();
        if (assessment != null) {
            return assessment;
        } else {
            throw new AssessmentNotFoundException("Assessment Id " + assessmentId + " does not exist!");
        }
    }

    @Override
    public void deleteAssessment(Long assessmentId) throws AssessmentNotFoundException {
        Assessment assessment = assessmentRepository.findById(assessmentId).get();
        if (assessment != null) {
            assessmentRepository.deleteById(assessmentId);
        } else {
            throw new AssessmentNotFoundException("Assessment Id " + assessmentId + " does not exist!");
        }
    }
}
