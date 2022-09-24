package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.model.QuizAttempt;
import com.educouch.educouchsystem.util.exception.QuestionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizAttemptNotFoundException;

import java.util.List;

public interface QuizAttemptService {
    public QuizAttempt saveQuizAttempt(QuizAttempt quizAttempt);

    public List<QuizAttempt> getAllQuizAttempts();

    public List<QuestionAttempt> getSubmittedQuestionAttempts(Long quizAttemptId) throws QuizAttemptNotFoundException;

    public QuizAttempt retrieveQuizAttemptById(Long quizAttemptId) throws QuizAttemptNotFoundException;

    public void deleteQuizAttempt(Long quizAttemptId) throws QuizAttemptNotFoundException;

    public void calculateQuestionScore(Long quizAttemptId) throws QuizAttemptNotFoundException, QuestionAttemptNotFoundException;
}
