package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.repository.QuestionRepository;
import com.educouch.educouchsystem.repository.QuizRepository;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
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
        Quiz quizToAdd = retrieveQuizById(quizId);
        List<Question> quizQuestions = quizToAdd.getQuizQuestions();
        if (quizQuestions.contains(question)) {
            quizQuestions.add(question);
            quizToAdd.setQuizQuestions(quizQuestions);
            saveQuiz(quizToAdd);
        } else {
            throw new EntityInstanceExistsInCollectionException("Question already exists in quiz!");
        }
    }
}
