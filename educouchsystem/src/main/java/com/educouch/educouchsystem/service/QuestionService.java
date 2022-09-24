package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
import com.educouch.educouchsystem.util.exception.OptionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;

import java.util.List;

public interface QuestionService {
    public Question saveQuestion(Question question);

    public List<Question> getAllQuestions();

    public List<Option> getAllOptionsInQuestion(Long questionId) throws QuestionNotFoundException;

    public List<QuestionAttempt> getAllQuestionAttemptsInQuestion(Long questionId) throws QuestionNotFoundException;

    public Question retrieveQuestionById(Long questionId) throws QuestionNotFoundException;

    public void deleteQuestion(Long questionId) throws QuestionNotFoundException;

    public Question updateQuestion (Question question) throws QuestionNotFoundException;

    public void addOptionToQuestion(Long questionId, Option option) throws QuestionNotFoundException, EntityInstanceExistsInCollectionException;

    public void removeOptionFromQuestion(Long questionId, Option option) throws QuestionNotFoundException, OptionNotFoundException;

}
