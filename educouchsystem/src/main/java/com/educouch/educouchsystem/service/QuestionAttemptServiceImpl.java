package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.repository.QuestionAttemptRepository;
import com.educouch.educouchsystem.util.exception.QuestionAttemptNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionAttemptServiceImpl implements QuestionAttemptService {

    @Autowired
    QuestionAttemptRepository questionAttemptRepository;

    @Override
    public QuestionAttempt saveQuestionAttempt(QuestionAttempt questionAttempt) {
        return questionAttemptRepository.save(questionAttempt);
    }

    @Override
    public QuestionAttempt getQuestionAttemptById(Long questionAttemptId) throws QuestionAttemptNotFoundException {
        QuestionAttempt questionAttempt = questionAttemptRepository.findById(questionAttemptId).get();
        if (questionAttempt != null) {
            return questionAttempt;
        } else {
            throw new QuestionAttemptNotFoundException("QuestionAttempt Id " + questionAttemptId + " does not exist!");
        }
    }
}
