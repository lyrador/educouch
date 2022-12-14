package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.FileSubmissionAttemptDTO;
import com.educouch.educouchsystem.dto.FileSubmissionDTO;
import com.educouch.educouchsystem.dto.LearnerAttemptDTO;
import com.educouch.educouchsystem.dto.QuestionAttemptDTO;
import com.educouch.educouchsystem.model.GradeBookEntry;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.repository.GradeBookEntryRepository;
import com.educouch.educouchsystem.util.exception.*;

import java.util.List;

public interface GradeBookEntryService {

    public GradeBookEntry createGradeBookEntry(GradeBookEntry gradeBookEntry);

    public GradeBookEntry updateGradeBookEntry(GradeBookEntry gradeBookEntry) throws GradeBookEntryNotFoundException;

    public List<GradeBookEntry> findAllGradeBookEntries();

    public List<GradeBookEntry> findAllGradeBookEntriesByAssessmentId(Long assessmentId);
    public List<GradeBookEntry> findAllGradeBookEntriesByLearnerIdAndCourseId(Long learnerId, Long courseId);

    public GradeBookEntry findById(Long gradeBookEntryId) throws GradeBookEntryNotFoundException;

    public List<LearnerAttemptDTO> viewLearnerAttemptPage(Long courseId, Long assessmentId, Long identifier) throws CourseNotFoundException;

    public List<QuestionAttemptDTO> getOpenEndedQns(Long learnerId, Long assessmentId) throws NoQuizAttemptsFoundException;

    public void updateOpenEndedQns(List<QuestionAttemptDTO> qns, Long learnerId, Long assessmentId) throws QuestionAttemptNotFoundException, NoQuizAttemptsFoundException;

    public FileSubmissionAttemptDTO getFileSubmission(Long learnerId, Long assessmentId) throws NoFileSubmissionsFoundException;

    public void updateFileSubmissionAttempt(FileSubmissionAttemptDTO fileSubmissionAttemptDTO) throws FileSubmissionAttemptNotFoundException;
}
