package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.repository.OptionRepository;
import com.educouch.educouchsystem.repository.QuestionRepository;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private QuizService quizService;


    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question saveQuestion(Long quizId, Question question) throws QuizNotFoundException {
        Quiz quiz = quizService.retrieveQuizById(quizId);
        if (quiz != null) {
            quiz.getQuizQuestions().add(question);
            quizService.saveQuiz(quiz);
            questionRepository.save(question);
            return question;
        } else {
            throw new QuizNotFoundException("Quiz Id " + quizId + " does not exist!");
        }
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

//    @Override
//    public List<String> getAllOptionsInQuestion(Long questionId) throws QuestionNotFoundException {
//        Question question = questionRepository.findById(questionId).get();
//        if (question != null) {
//            List<String> options = question.getOptions();
//            return options;
//        } else {
//            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
//        }
//    }

//    @Override
//    public List<QuestionAttempt> getAllQuestionAttemptsInQuestion(Long questionId) throws QuestionNotFoundException {
//        Question question = questionRepository.findById(questionId).get();
//        if (question != null) {
//            List<QuestionAttempt> questionAttempts = question.getQuestionAttempts();
//            return questionAttempts;
//        } else {
//            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
//        }
//    }

    @Override
    public Question retrieveQuestionById(Long questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId).get();
        if (question != null) {
            return question;
        } else {
            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
        }
    }

    @Override
    public void deleteQuestion(Long questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId).get();
        if (question != null) {
            questionRepository.deleteById(questionId);
        } else {
            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
        }
    }

    @Override
    public Question updateQuestion(Question questionToUpdate, Question question) throws QuestionNotFoundException {
        if (questionToUpdate.getQuestionId().equals(question.getQuestionId())) {
            questionToUpdate.setQuestionContent(question.getQuestionContent());
            questionToUpdate.setQuestionHint(question.getQuestionHint());
            questionToUpdate.setQuestionMaxScore(question.getQuestionMaxScore());
            questionToUpdate.setQuestionType(question.getQuestionType());
            questionRepository.save(questionToUpdate);
            return questionToUpdate;
        } else {
            throw new QuestionNotFoundException("Question to be updated does not exist!");
        }
    }

//    @Override
//    public void addOptionToQuestion(Long questionId, Option option) throws QuestionNotFoundException, EntityInstanceExistsInCollectionException {
//        Question questionToEdit = retrieveQuestionById(questionId);
//        List<Option> questionOptions = questionToEdit.getOptions();
//        if (!questionOptions.contains(option)) {
//            questionOptions.add(option);
//            saveQuestion(questionToEdit);
//            optionRepository.save(option);
//        } else {
//            throw new EntityInstanceExistsInCollectionException("Option already exists in question!");
//        }
//    }

//    @Override
//    public void deleteOptionFromQuestionId(Long optionId, Long questionId) throws QuestionNotFoundException {
//        Option option = optionRepository.findById(optionId).get();
//        retrieveQuestionById(questionId).getOptions().remove(option);
//        if (option != null) {
//            optionRepository.deleteById(optionId);
//        } else {
//            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
//        }
//    }
}
