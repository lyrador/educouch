package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.model.QuizAttempt;
import com.educouch.educouchsystem.util.exception.NoQuizAttemptsFoundException;
import com.educouch.educouchsystem.util.exception.QuestionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;

import java.util.List;

public interface QuizAttemptService {


    public QuizAttempt saveQuizAttempt(Quiz quiz, QuizAttempt quizAttempt, Learner learner) throws QuizNotFoundException;
    public List<QuizAttempt> getParticularQuizAttemptsByLearnerId(Long learnerId, Long assessmentId) throws NoQuizAttemptsFoundException;
    public List<QuizAttempt> getQuizAttemptsByLearnerId(Long learnerId) throws NoQuizAttemptsFoundException ;
    public QuizAttempt getMostRecentQuizAttemptByLearnerId(Long learnerId, Long assessmentId) throws NoQuizAttemptsFoundException;
//    public List<QuizAttempt> getAllQuizAttempts();

//    public List<QuestionAttempt> getSubmittedQuestionAttempts(Long quizAttemptId) throws QuizAttemptNotFoundException;

//    public QuizAttempt retrieveQuizAttemptById(Long quizAttemptId) throws QuizAttemptNotFoundException;

//    public void deleteQuizAttempt(Long quizAttemptId) throws QuizAttemptNotFoundException;

//    public void calculateQuestionScore(Long quizAttemptId) throws QuizAttemptNotFoundException, QuestionAttemptNotFoundException;
}

