package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.AssessmentRepository;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.NoQuizAttemptsFoundException;
import com.educouch.educouchsystem.util.exception.PointsWalletNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    @Autowired
    private PointsWalletService pointsWalletService;

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

        List<GradeBookEntry> list = gradeBookEntryService.findAllGradeBookEntriesByAssessmentId(assessmentId);

        List<Double> listOfScores = new ArrayList<>();
        for(GradeBookEntry g : list) {
            g.setPublished(a.isPublished());
            try {
                QuizAttempt attempt = quizAttemptService.getMostRecentQuizAttemptByLearnerId(g.getLearnerId(), assessmentId);
                listOfScores.add(attempt.getObtainedScore());
                g.setLearnerScore(attempt.getObtainedScore());
                gradeBookEntryService.createGradeBookEntry(g);
            } catch (NoQuizAttemptsFoundException e) {
                g.setLearnerScore(0.0);
                gradeBookEntryService.createGradeBookEntry(g);
            }


        }
        if(!a.getPointsAllocation() && a.getDiscountPointForAssessment() > 0L) {
        Collections.sort(listOfScores);
        Double scoreToGetDiscount = percentile(listOfScores,a.getDiscountPointToTopPercent());
        System.out.println("score to get discount: " + scoreToGetDiscount);
        list = gradeBookEntryService.findAllGradeBookEntriesByAssessmentId(assessmentId);
        System.out.println("size of gradebook list: " + list.size());

            for(GradeBookEntry gb : list) {
            if (gb.getLearnerScore() >= scoreToGetDiscount) {
                try {
                    System.out.println("learner with ID is getting discount: " + gb.getLearnerId());
                    Course course = courseService.getCourseById(gb.getCourseId());
                    PointsWallet wallet = pointsWalletService.findParticularWallet(gb.getLearnerId(), course.getOrganisation().getOrganisationId());
                    wallet.setDiscountPoints(wallet.getDiscountPoints() + a.getDiscountPointForAssessment());
                    pointsWalletService.updatePointsWallet(wallet);
                } catch (CourseNotFoundException | PointsWalletNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        a.setPointsAllocation(true);
        }
        saveAssessment(a);
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


    public Double percentile(List<Double> latencies, Integer topPercent) {
        Integer percentile = 100 - topPercent;
        int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
        return latencies.get(index-1);
    }
}
