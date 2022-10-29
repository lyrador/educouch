package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.GradeBookEntry;
import com.educouch.educouchsystem.model.QuizAttempt;
import com.educouch.educouchsystem.repository.AssessmentRepository;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.NoQuizAttemptsFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private GradeBookEntryService gradeBookEntryService;

    @Autowired
    private QuizAttemptService quizAttemptService;

    @Override
    public Assessment saveAssessment (Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    @Override
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    @Override
    public List<Assessment> getAllAssessmentsByCourseId(Long courseId) throws CourseNotFoundException{
            Course course = courseService.getCourseById(courseId);
            List<Assessment> assessments = course.getAssessments();
            return assessments;
    }

    @Override
    public List<Assessment> getAllReleasedAssessmentsByCourseId(Long courseId) throws CourseNotFoundException{
        List<Assessment> assessments = getAllAssessmentsByCourseId(courseId);
        for(Assessment a : assessments) {
            System.out.println(a.toString());
        }
         return assessments.stream().filter((assessment) -> assessment.getOpen().equals(Boolean.TRUE)).collect(Collectors.toList());
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

    @Override
    public void togglePublish(Long assessmentId) throws AssessmentNotFoundException {
        Assessment a = retrieveAssessmentById(assessmentId);
        a.setPublished(!a.isPublished());
        saveAssessment(a);

        List<GradeBookEntry> list = gradeBookEntryService.findAllGradeBookEntriesByAssessmentId(assessmentId);
        for(GradeBookEntry g : list) {
            g.setPublished(a.isPublished());
            try {
                QuizAttempt attempt = quizAttemptService.getMostRecentQuizAttemptByLearnerId(g.getLearnerId(), assessmentId);
                g.setLearnerScore(attempt.getObtainedScore());
                gradeBookEntryService.createGradeBookEntry(g);
            } catch (NoQuizAttemptsFoundException e) {
                g.setLearnerScore(0.0);
                gradeBookEntryService.createGradeBookEntry(g);
            }


        }
    }


    @Override
    public void deleteAssessmentFromCourseId(Long assessmentId, Long courseId) throws AssessmentNotFoundException, CourseNotFoundException {
        Assessment assessment = assessmentRepository.findById(assessmentId).get();
        courseService.getCourseById(courseId).getAssessments().remove(assessment);
        if (assessment != null) {
            assessmentRepository.deleteById(assessmentId);
        } else {
            throw new AssessmentNotFoundException("Assessment Id " + assessmentId + " does not exist!");
        }
    }
}
