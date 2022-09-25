package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.FileSubmission;
import com.educouch.educouchsystem.repository.FileSubmissionRepository;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.FileSubmissionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileSubmissionServiceImpl implements FileSubmissionService {

    @Autowired
    FileSubmissionRepository fileSubmissionRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    AssessmentService assessmentService;

    @Override
    public FileSubmission saveFileSubmission(FileSubmission fileSubmission) {
        return fileSubmissionRepository.save(fileSubmission);
    }

    @Override
    public FileSubmission saveFileSubmission(Long courseId, FileSubmission fileSubmission) throws CourseNotFoundException {
        Course course = courseService.retrieveCourseById(courseId);
        if (course != null) {
            course.getAssessments().add(fileSubmission);
            courseService.saveCourse(course);
            return fileSubmission;
        } else {
            throw new CourseNotFoundException("Course Id " + courseId + " does not exist!");
        }
    }

    @Override
    public List<FileSubmission> getAllFileSubmissions() {
        return fileSubmissionRepository.findAll();
    }

    @Override
    public List<FileSubmission> getAllFileSubmissionByCourseId(Long courseId) throws CourseNotFoundException {
        Course course = courseService.retrieveCourseById(courseId);
        if (course != null) {
            List<Assessment> assessments = course.getAssessments();
            List<FileSubmission> fileSubmissions = new ArrayList<>();
            for (Assessment assessment : assessments) {
                if (assessment instanceof FileSubmission) {
                    FileSubmission fileSubmission = (FileSubmission) assessment;
                    fileSubmissions.add(fileSubmission);
                }
            }
            return fileSubmissions;
        } else {
            throw new CourseNotFoundException("Course Id " + courseId + " does not exist!");
        }
    }

    @Override
    public FileSubmission retrieveFileSubmissionById(Long fileSubmissionId) throws FileSubmissionNotFoundException {
        FileSubmission fileSubmission = fileSubmissionRepository.findById(fileSubmissionId).get();
        if (fileSubmission != null) {
            return fileSubmission;
        } else {
            throw new FileSubmissionNotFoundException("File Submission " + fileSubmissionId + " cannot be found!");
        }
    }

    @Override
    public void deleteFileSubmission(Long fileSubmissionId) throws FileSubmissionNotFoundException {
        try {
            FileSubmission fileSubmission = fileSubmissionRepository.findById(fileSubmissionId).get();
            fileSubmissionRepository.deleteById(fileSubmissionId);
            assessmentService.deleteAssessment(fileSubmissionId);
        } catch (AssessmentNotFoundException ex) {
            throw new FileSubmissionNotFoundException("File Submission " + fileSubmissionId + " cannot be found!");
        }
    }

    @Override
    public FileSubmission updateFileSubmission(FileSubmission fileSubmissionToUpdate, FileSubmission fileSubmission) throws FileSubmissionNotFoundException {
        if (fileSubmissionToUpdate != null && fileSubmissionToUpdate.getAssessmentId().equals(fileSubmission.getAssessmentId())) {
            fileSubmissionToUpdate.setTitle(fileSubmission.getTitle());
            fileSubmissionToUpdate.setDescription(fileSubmission.getDescription());
            fileSubmissionToUpdate.setMaxScore(fileSubmission.getMaxScore());
            fileSubmissionToUpdate.setStartDate(fileSubmission.getStartDate());
            fileSubmissionToUpdate.setEndDate(fileSubmission.getEndDate());
            fileSubmissionToUpdate.setOpen(fileSubmission.getOpen());
            fileSubmissionToUpdate.setAssessmentStatus(fileSubmission.getAssessmentStatus());
            fileSubmissionToUpdate.setFileSubmissionEnum(fileSubmission.getFileSubmissionEnum());
            fileSubmissionRepository.save(fileSubmissionToUpdate);
            return fileSubmissionToUpdate;
        } else {
            throw new FileSubmissionNotFoundException("File Submission to be updated cannot be found!");
        }
    }
}
