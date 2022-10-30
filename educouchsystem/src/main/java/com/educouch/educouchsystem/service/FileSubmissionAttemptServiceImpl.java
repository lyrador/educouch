package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.FileSubmission;
import com.educouch.educouchsystem.model.FileSubmissionAttempt;
import com.educouch.educouchsystem.model.QuizAttempt;
import com.educouch.educouchsystem.repository.AssessmentRepository;
import com.educouch.educouchsystem.repository.FileSubmissionAttemptRepository;
import com.educouch.educouchsystem.util.exception.FileSubmissionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.FileSubmissionNotFoundException;
import com.educouch.educouchsystem.util.exception.NoFileSubmissionsFoundException;
import com.educouch.educouchsystem.util.exception.NoQuizAttemptsFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSubmissionAttemptServiceImpl implements FileSubmissionAttemptService {

    @Autowired
    private FileSubmissionAttemptRepository fileSubmissionAttemptRepository;

    @Autowired
    private FileSubmissionService fileSubmissionService;

    @Override
    public FileSubmissionAttempt saveFileSubmissionAttempt(FileSubmissionAttempt fileSubmissionAttempt) {
        return fileSubmissionAttemptRepository.save(fileSubmissionAttempt);
    }

    @Override
    public FileSubmissionAttempt saveFileSubmissionAttempt(Long fileSubmissionId, FileSubmissionAttempt fileSubmissionAttempt) throws FileSubmissionNotFoundException {
        FileSubmission fileSubmission = fileSubmissionService.retrieveFileSubmissionById(fileSubmissionId);
        if (fileSubmission != null) {
//            fileSubmission.getFileSubmissionAttempts().add(fileSubmissionAttempt);
            fileSubmissionService.saveFileSubmission(fileSubmission);
            fileSubmissionAttemptRepository.save(fileSubmissionAttempt);
            return fileSubmissionAttempt;
        } else {
            throw new FileSubmissionNotFoundException("File Submission " + fileSubmissionId + " cannot be found!");
        }
    }

    @Override
    public List<FileSubmissionAttempt> getAllFileSubmissionAttempts() {
        return fileSubmissionAttemptRepository.findAll();
    }

//    @Override
//    public List<FileSubmissionAttempt> getAllFileSubmissionAttemptsByFileSubmissionId(Long fileSubmissionId) throws FileSubmissionNotFoundException {
//        FileSubmission fileSubmission = fileSubmissionService.retrieveFileSubmissionById(fileSubmissionId);
//        if (fileSubmission != null) {
//            List<FileSubmissionAttempt> fileSubmissionAttempts = fileSubmission.getFileSubmissionAttempts();
//            return fileSubmissionAttempts;
//        } else {
//            throw new FileSubmissionNotFoundException("File Submission " + fileSubmissionId + " cannot be found!");
//        }
//    }

    @Override
    public FileSubmissionAttempt retrieveFileSubmissionAttemptById(Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException {
        FileSubmissionAttempt fileSubmissionAttempt = fileSubmissionAttemptRepository.findById(fileSubmissionAttemptId).get();
        if (fileSubmissionAttempt != null) {
            return fileSubmissionAttempt;
        } else {
            throw new FileSubmissionAttemptNotFoundException("File Submission Attempt" + fileSubmissionAttemptId + " cannot be found!");
        }
    }

    @Override
    public void deleteFileSubmissionAttempt(Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException {
        FileSubmissionAttempt fileSubmissionAttempt = fileSubmissionAttemptRepository.findById(fileSubmissionAttemptId).get();
        if (fileSubmissionAttempt != null) {
            fileSubmissionAttemptRepository.deleteById(fileSubmissionAttemptId);

        } else {
            throw new FileSubmissionAttemptNotFoundException("File Submission Attempt" + fileSubmissionAttemptId + " cannot be found!");
        }
    }

    @Override
    public List<FileSubmissionAttempt> getParticularFileSubmissionAttemptsByLearnerId(Long learnerId, Long assessmentId) throws NoFileSubmissionsFoundException {
        List<FileSubmissionAttempt> allQuizAttempts = getFileSubmissionAttemptsByLearnerId(learnerId);
        System.out.println(allQuizAttempts.size());
        if(allQuizAttempts.size()!=0) {
            List<FileSubmissionAttempt> newList = allQuizAttempts.stream().filter(quizAttempt -> quizAttempt.getFileSubmissionAttempted().getAssessmentId().equals(assessmentId)).collect(Collectors.toList());
            if(newList.size()!=0) {
                return newList;
            } else {
                throw new NoFileSubmissionsFoundException();

            }
        } else {
            throw new NoFileSubmissionsFoundException();
        }    }

    @Override
    public FileSubmissionAttempt getMostRecentFileSubmissionAttemptByLearnerId(Long learnerId, Long assessmentId) throws NoFileSubmissionsFoundException {
        List<FileSubmissionAttempt> allQuizAttempts = getParticularFileSubmissionAttemptsByLearnerId(learnerId, assessmentId);
        FileSubmissionAttempt mostRecentAttempt = allQuizAttempts.get(0);
        for(FileSubmissionAttempt q : allQuizAttempts) {
            if(q.getLastAttemptTime().after(mostRecentAttempt.getLastAttemptTime())) {
                mostRecentAttempt = q;
            }
        }
        return mostRecentAttempt;    }

    @Override
    public List<FileSubmissionAttempt> getFileSubmissionAttemptsByLearnerId(Long learnerId) throws NoFileSubmissionsFoundException {
        List<FileSubmissionAttempt> quizAttempts = fileSubmissionAttemptRepository.findFileSubmissionAttemptsByLearnerId(learnerId);
        if(quizAttempts.size()!=0) {
            return quizAttempts;
        } else {
            throw new NoFileSubmissionsFoundException();
        }
    }


}
