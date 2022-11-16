package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TriviaQuiz;
import com.educouch.educouchsystem.util.exception.TriviaQuizNotFoundException;

import java.util.List;

public interface TriviaQuizService {

    public TriviaQuiz saveTriviaQuiz(TriviaQuiz triviaQuiz);

    public List<TriviaQuiz> getAllTriviaQuizzes();

    public TriviaQuiz getTriviaQuizById(Long triviaQuizId) throws TriviaQuizNotFoundException;

    public void deleteTriviaQuiz(Long id);


}
