package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;

import java.util.List;

public interface QuizService {
    public Quiz saveQuiz(Quiz quiz);

    public List<Quiz> getAllQuizzes();

    public Quiz retrieveQuizById(Long quizId) throws QuizNotFoundException;

    public void deleteQuiz(Long quizId) throws QuizNotFoundException;

    public void addQuestionToQuiz(Long quizId, Question question) throws QuizNotFoundException, EntityInstanceExistsInCollectionException;

}
