package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.FileSubmission;
import com.educouch.educouchsystem.model.FileSubmissionAttempt;
import com.educouch.educouchsystem.util.exception.FileSubmissionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.FileSubmissionNotFoundException;

import java.util.List;

public interface FileSubmissionAttemptService {

    public FileSubmissionAttempt saveFileSubmissionAttempt(FileSubmissionAttempt fileSubmissionAttempt);

    public FileSubmissionAttempt saveFileSubmissionAttempt(Long fileSubmissionId, FileSubmissionAttempt fileSubmissionAttempt) throws FileSubmissionNotFoundException;

    public List<FileSubmissionAttempt> getAllFileSubmissionAttempts();

//    public List<FileSubmissionAttempt> getAllFileSubmissionAttemptsByFileSubmissionId(Long fileSubmissionId) throws FileSubmissionNotFoundException;

    public FileSubmissionAttempt retrieveFileSubmissionAttemptById(Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException;

    public void deleteFileSubmissionAttempt(Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException;

}
