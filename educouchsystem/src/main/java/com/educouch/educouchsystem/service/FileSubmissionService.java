package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.FileSubmission;
import com.educouch.educouchsystem.model.FileSubmissionAttempt;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.FileSubmissionNotFoundException;

import java.util.List;

public interface FileSubmissionService {

    public FileSubmission saveFileSubmission(FileSubmission fileSubmission);

    public FileSubmission saveFileSubmission(Long courseId, FileSubmission fileSubmission) throws CourseNotFoundException;

    public List<FileSubmission> getAllFileSubmissions();

    public List<FileSubmission> getAllFileSubmissionByCourseId(Long courseId) throws CourseNotFoundException;

    public FileSubmission retrieveFileSubmissionById(Long fileSubmissionId) throws FileSubmissionNotFoundException;

    public void deleteFileSubmission(Long fileSubmissionId) throws FileSubmissionNotFoundException;

    public FileSubmission updateFileSubmission(FileSubmission fileSubmissionToUpdate, FileSubmission fileSubmission) throws FileSubmissionNotFoundException;
}
