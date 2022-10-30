package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.LearnerAttemptDTO;
import com.educouch.educouchsystem.dto.QuestionAttemptDTO;
import com.educouch.educouchsystem.dto.QuestionDTO;
import com.educouch.educouchsystem.dto.QuizAttemptDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.GradeBookEntryRepository;
import com.educouch.educouchsystem.util.enumeration.AssessmentAttemptStatusEnum;
import com.educouch.educouchsystem.util.enumeration.QuestionTypeEnum;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GradeBookEntryServiceImpl implements GradeBookEntryService {

    @Autowired
    private GradeBookEntryRepository gradeBookEntryRepository;

    @Autowired
    private LearnerService learnerService;

    @Autowired
    private QuizAttemptService quizAttemptService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuestionAttemptService questionAttemptService;

    @Autowired
    private FileSubmissionAttemptService fileSubmissionAttemptService;

    @Override
    public GradeBookEntry createGradeBookEntry(GradeBookEntry gradeBookEntry) {
        return gradeBookEntryRepository.save(gradeBookEntry);
    }

    @Override
    public GradeBookEntry updateGradeBookEntry(GradeBookEntry gradeBookEntry) throws GradeBookEntryNotFoundException {
        GradeBookEntry entryToUpdate = findById(gradeBookEntry.getGradeBookEntryId());
        entryToUpdate.setAssessmentMax(gradeBookEntry.getAssessmentMax());
        entryToUpdate.setAssessmentName(gradeBookEntry.getAssessmentName());
        entryToUpdate.setCourseId(gradeBookEntry.getCourseId());
        entryToUpdate.setLearnerScore(gradeBookEntry.getLearnerScore());
        gradeBookEntryRepository.save(entryToUpdate);
        return entryToUpdate;
    }

    @Override
    public List<GradeBookEntry> findAllGradeBookEntries() {
        return gradeBookEntryRepository.findAll();
    }

    @Override
    public List<GradeBookEntry> findAllGradeBookEntriesByAssessmentId(Long assessmentId) {
        return gradeBookEntryRepository.findByAssessmentId(assessmentId);
    }

    @Override
    public List<GradeBookEntry> findAllGradeBookEntriesByLearnerIdAndCourseId(Long learnerId, Long courseId) {
        return gradeBookEntryRepository.findByLearnerIDAndCourseId(learnerId, courseId);
    }

    @Override
    public GradeBookEntry findById(Long gradeBookEntryId) throws GradeBookEntryNotFoundException{
        if(gradeBookEntryRepository.findById(gradeBookEntryId).isPresent()) {
            return gradeBookEntryRepository.findById(gradeBookEntryId).get();
        } else {
            throw new GradeBookEntryNotFoundException("Grade Book Entry not found!");
        }
    }

    @Override
    public List<LearnerAttemptDTO> viewLearnerAttemptPage(Long courseId, Long assessmentId,Long identifier) throws CourseNotFoundException {
        //identifier 1 == quiz, 2 == filesubmission
        List<Learner> learnerInCourse = courseService.getLearnerByCourse(courseId);
        List<LearnerAttemptDTO> finalList = new ArrayList<>();
        for(Learner l : learnerInCourse) {
            LearnerAttemptDTO dto = new LearnerAttemptDTO();
            dto.setLearnerId(l.getLearnerId());
            dto.setLearnerName(l.getName());
            if(identifier.intValue() == 1) {
                dto.setQuiz(true);
                try {
                    QuizAttempt attempt = quizAttemptService.getMostRecentQuizAttemptByLearnerId(l.getLearnerId(), assessmentId);
                    dto.setDidAttempt(true);
                    dto.setQuizMax(attempt.getAttemptedQuiz().getMaxScore());
                    if (attempt.getAssessmentAttemptStatusEnum().equals(AssessmentAttemptStatusEnum.GRADED)) {
                        dto.setGraded(true);
                    } else {
                        dto.setGraded(false);
                    }
                    dto.setLearnerMcqScore(attempt.getLearnerMcqScore());
                    dto.setObtainedScore(attempt.getObtainedScore());
                    List<QuestionAttempt> qnAttempt = attempt.getQuestionAttempts();
                    for (QuestionAttempt q : qnAttempt) {
                        if (q.getQuestionAttempted().getQuestionType().equals(QuestionTypeEnum.OPEN_ENDED)) {
                            dto.setOpenEnded(true);
                            break;
                        }
                    }

                } catch (NoQuizAttemptsFoundException e) {
                    dto.setDidAttempt(false);
                }
            } else {
                dto.setQuiz(false);
                try {
                    FileSubmissionAttempt attempt = fileSubmissionAttemptService.getMostRecentFileSubmissionAttemptByLearnerId(l.getLearnerId(),assessmentId);
                    dto.setDidAttempt(true);
                    dto.setQuizMax(attempt.getFileSubmissionAttempted().getMaxScore());
                    if (attempt.getAssessmentAttemptStatusEnum().equals(AssessmentAttemptStatusEnum.GRADED)) {
                        dto.setGraded(true);
                        dto.setObtainedScore(attempt.getObtainedScore());

                    } else {
                        dto.setGraded(false);
                    }


                } catch (NoFileSubmissionsFoundException e) {
                    dto.setDidAttempt(false);
                }
            }
            finalList.add(dto);
        }
        return finalList;
    }

    @Override
    public List<QuestionAttemptDTO> getOpenEndedQns(Long learnerId, Long assessmentId) throws NoQuizAttemptsFoundException {
        QuizAttempt attempt = quizAttemptService.getMostRecentQuizAttemptByLearnerId(learnerId, assessmentId);
        List<QuestionAttemptDTO> list = new ArrayList<>();
        for(QuestionAttempt q : attempt.getQuestionAttempts()) {
            if(q.getQuestionAttempted().getQuestionType().equals(QuestionTypeEnum.OPEN_ENDED)) {
                QuestionAttemptDTO dto = new QuestionAttemptDTO();
                dto.setQuestionAttemptId(q.getQuestionAttemptId());
                dto.setQuestionAttemptScore(q.getQuestionAttemptScore());
                dto.setShortAnswerResponse(q.getShortAnswerResponse());
                dto.setFeedback(q.getFeedback());
                QuestionDTO dto2 = new QuestionDTO();
                dto2.setQuestionMaxPoints(q.getQuestionAttempted().getQuestionMaxScore().toString());
                dto2.setQuestionType(q.getQuestionAttempted().getQuestionType().toString());
                dto2.setLocalid(q.getQuestionAttempted().getLocalid());
                dto2.setQuestionContent(q.getQuestionAttempted().getQuestionContent());
                dto2.setQuestionTitle(q.getQuestionAttempted().getQuestionTitle());
                dto2.setQuestionId(q.getQuestionAttempted().getQuestionId().toString());
                dto.setQuestionAttempted(dto2);
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public void updateOpenEndedQns(List<QuestionAttemptDTO> qns, Long learnerId, Long assessmentId) throws QuestionAttemptNotFoundException, NoQuizAttemptsFoundException {
        QuizAttempt quizAttempt = quizAttemptService.getMostRecentQuizAttemptByLearnerId(learnerId, assessmentId);
        quizAttempt.setAssessmentAttemptStatusEnum(AssessmentAttemptStatusEnum.GRADED);
        double newOpenEndedScore = 0.0;
        for(QuestionAttemptDTO q : qns) {
            QuestionAttempt attempt = questionAttemptService.getQuestionAttemptById(q.getQuestionAttemptId());
            attempt.setQuestionAttemptScore(q.getQuestionAttemptScore());
            newOpenEndedScore += q.getQuestionAttemptScore();
            attempt.setFeedback(q.getFeedback());
            questionAttemptService.saveQuestionAttempt(attempt);
        }
        quizAttempt.setObtainedScore(quizAttempt.getLearnerMcqScore() + newOpenEndedScore);
        quizAttemptService.saveQuizAttemptEz(quizAttempt);
    }
}
