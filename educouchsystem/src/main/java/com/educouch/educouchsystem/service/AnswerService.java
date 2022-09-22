package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Answer;
import com.educouch.educouchsystem.util.exception.AnswerNotFoundException;

import java.util.List;

public interface AnswerService {
    public Answer saveAnswer(Answer answer);

    public List<Answer> getAllAnswers();

    public Answer retrieveAnswerById(Long answerId) throws AnswerNotFoundException;

    public void deleteAnswer(Long answerId) throws AnswerNotFoundException;

}
