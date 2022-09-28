package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.model.QuizAttempt;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;

import java.util.List;

public interface QuizService {
    public Quiz saveQuiz(Quiz quiz);

    public Quiz saveQuiz(Long courseId, Quiz quiz) throws CourseNotFoundException;

    public List<Quiz> getAllQuizzes();

    public List<Quiz> getAllQuizzesByCourseId(Long courseId) throws CourseNotFoundException;

    public List<Question> getAllQuestionsInQuiz(Long quizId) throws QuizNotFoundException;

    public List<QuizAttempt> getAllQuizAttemptsInQuiz(Long quizId) throws QuizNotFoundException;

    public Quiz retrieveQuizById(Long quizId) throws QuizNotFoundException;

    public void deleteQuiz(Long quizId) throws QuizNotFoundException;

    public Quiz updateQuiz(Quiz quizToUpdate, Quiz quiz) throws QuizNotFoundException;

    public void addQuestionToQuiz(Long quizId, Question question) throws QuizNotFoundException, EntityInstanceExistsInCollectionException;
    public void removeQuestionFromQuiz(Long quizId, Question question) throws QuizNotFoundException, QuestionNotFoundException;

}
