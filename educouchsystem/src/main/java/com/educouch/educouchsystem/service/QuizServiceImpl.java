package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.model.QuizAttempt;
import com.educouch.educouchsystem.repository.QuestionRepository;
import com.educouch.educouchsystem.repository.QuizRepository;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QuizServiceImpl implements QuizService{

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public List<Question> getAllQuestionsInQuiz(Long quizId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId).get();
        if (quiz != null) {
            List<Question> questions = quiz.getQuizQuestions();
            return questions;
        } else {
            throw new QuizNotFoundException("Quiz Id " + quizId + " does not exist!");
        }
    }

    @Override
    public List<QuizAttempt> getAllQuizAttemptsInQuiz(Long quizId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId).get();
        if (quiz != null) {
            List<QuizAttempt> quizAttempts = quiz.getQuizAttempts();
            return quizAttempts;
        } else {
            throw new QuizNotFoundException("Quiz Id " + quizId + " does not exist!");
        }
    }

    @Override
    public Quiz retrieveQuizById(Long quizId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId).get();
        if (quiz != null) {
            return quiz;
        } else {
            throw new QuizNotFoundException("Quiz Id " + quizId + " does not exist!");
        }
    }

    @Override
    public void deleteQuiz(Long quizId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId).get();
        if (quiz != null) {
            quizRepository.deleteById(quizId);
        } else {
            throw new QuizNotFoundException("Quiz Id " + quizId + " does not exist!");
        }
    }

    @Override
    public void addQuestionToQuiz(Long quizId, Question question) throws QuizNotFoundException, EntityInstanceExistsInCollectionException {
        Quiz quizToEdit = retrieveQuizById(quizId);
        List<Question> quizQuestions = quizToEdit.getQuizQuestions();
        if (!quizQuestions.contains(question)) {
            quizQuestions.add(question);
            quizToEdit.setQuizQuestions(quizQuestions);
            saveQuiz(quizToEdit);
        } else {
            throw new EntityInstanceExistsInCollectionException("Question already exists in quiz!");
        }
    }

    @Override
    public void removeQuestionFromQuiz(Long quizId, Question question) throws QuizNotFoundException, QuestionNotFoundException {
        Quiz quizToEdit = retrieveQuizById(quizId);
        List<Question> quizQuestions = quizToEdit.getQuizQuestions();
        if (quizQuestions.contains(question)) {
            quizQuestions.remove(question);
            quizToEdit.setQuizQuestions(quizQuestions);
            saveQuiz(quizToEdit);
        } else {
            throw new QuestionNotFoundException("Question does not exist in quiz!");
        }
    }
}
