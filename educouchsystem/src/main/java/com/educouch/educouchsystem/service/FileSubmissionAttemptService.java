package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.FileSubmission;
import com.educouch.educouchsystem.model.FileSubmissionAttempt;
import com.educouch.educouchsystem.model.QuizAttempt;
import com.educouch.educouchsystem.util.exception.FileSubmissionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.FileSubmissionNotFoundException;
import com.educouch.educouchsystem.util.exception.NoFileSubmissionsFoundException;
import com.educouch.educouchsystem.util.exception.NoQuizAttemptsFoundException;

import java.util.List;

public interface FileSubmissionAttemptService {

    public FileSubmissionAttempt saveFileSubmissionAttempt(FileSubmissionAttempt fileSubmissionAttempt);

    public FileSubmissionAttempt saveFileSubmissionAttempt(Long fileSubmissionId, FileSubmissionAttempt fileSubmissionAttempt) throws FileSubmissionNotFoundException;

    public List<FileSubmissionAttempt> getAllFileSubmissionAttempts();

//    public List<FileSubmissionAttempt> getAllFileSubmissionAttemptsByFileSubmissionId(Long fileSubmissionId) throws FileSubmissionNotFoundException;

    public FileSubmissionAttempt retrieveFileSubmissionAttemptById(Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException;

    public void deleteFileSubmissionAttempt(Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException;

    public List<FileSubmissionAttempt> getParticularFileSubmissionAttemptsByLearnerId(Long learnerId, Long assessmentId) throws NoFileSubmissionsFoundException;
    public FileSubmissionAttempt getMostRecentFileSubmissionAttemptByLearnerId(Long learnerId, Long assessmentId) throws NoFileSubmissionsFoundException;

    public List<FileSubmissionAttempt> getFileSubmissionAttemptsByLearnerId(Long learnerId) throws NoFileSubmissionsFoundException;


    }
