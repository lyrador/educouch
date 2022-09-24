package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.util.exception.QuestionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;

import java.util.List;

public interface QuestionAttemptService {

    public QuestionAttempt saveQuestionAttempt(QuestionAttempt questionAttempt);

    public List<QuestionAttempt> getAllQuestionAttempts();

    public List<Option> getSubmittedOptions(Long questionAttemptId) throws QuestionAttemptNotFoundException;

    public QuestionAttempt retrieveQuestionAttemptById(Long questionAttemptId) throws QuestionAttemptNotFoundException;

    public void deleteQuestionAttempt(Long questionAttemptId) throws QuestionAttemptNotFoundException;

    /*public void calculateQuestionScore(Long questionAttemptId) throws QuestionAttemptNotFoundException, QuestionNotFoundException;*/

}
