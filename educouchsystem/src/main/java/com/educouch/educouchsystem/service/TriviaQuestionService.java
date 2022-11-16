package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TriviaQuestion;
import com.educouch.educouchsystem.util.exception.TriviaQuestionNotFoundException;

import java.util.List;

public interface TriviaQuestionService {

    public TriviaQuestion saveTriviaQuestion(TriviaQuestion triviaQuestion);

    public List<TriviaQuestion> getAllTriviaQuestions();

    public TriviaQuestion getTriviaQuestionById(Long triviaQuestionId) throws TriviaQuestionNotFoundException;

    public void deleteTriviaQuestion(Long id);
}
