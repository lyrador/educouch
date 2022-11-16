package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.util.exception.QuestionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;

import java.util.List;

public interface QuestionAttemptService {

    public QuestionAttempt saveQuestionAttempt(QuestionAttempt questionAttempt);

    public QuestionAttempt getQuestionAttemptById(Long questionAttemptId) throws QuestionAttemptNotFoundException;

}
