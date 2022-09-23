package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.repository.OptionRepository;
import com.educouch.educouchsystem.repository.QuestionRepository;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
import com.educouch.educouchsystem.util.exception.OptionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;


    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Option> getAllOptionsInQuestion(Long questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId).get();
        if (question != null) {
            List<Option> options = question.getOptions();
            return options;
        } else {
            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
        }
    }

    @Override
    public List<QuestionAttempt> getAllQuestionAttemptsInQuestion(Long questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId).get();
        if (question != null) {
            List<QuestionAttempt> questionAttempts = question.getQuestionAttempts();
            return questionAttempts;
        } else {
            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
        }
    }

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
    public void addOptionToQuestion(Long questionId, Option option) throws QuestionNotFoundException, EntityInstanceExistsInCollectionException {
        Question questionToEdit = retrieveQuestionById(questionId);
        List<Option> questionOptions = questionToEdit.getOptions();
        if (!questionOptions.contains(option)) {
            questionOptions.add(option);
            questionToEdit.setOptions(questionOptions);
            saveQuestion(questionToEdit);
        } else {
            throw new EntityInstanceExistsInCollectionException("Option already exists in question!");
        }
    }

    @Override
    public void removeOptionFromQuestion(Long questionId, Option option) throws QuestionNotFoundException, OptionNotFoundException {
        Question questionToEdit = retrieveQuestionById(questionId);
        List<Option> questionOptions = questionToEdit.getOptions();
        if (questionOptions.contains(option)) {
            questionOptions.remove(option);
            questionToEdit.setOptions(questionOptions);
            saveQuestion(questionToEdit);
        } else {
            throw new OptionNotFoundException("Option does not exist in question!");
        }
    }
}
