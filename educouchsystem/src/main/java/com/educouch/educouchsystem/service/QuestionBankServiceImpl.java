package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.QuestionBank;
import com.educouch.educouchsystem.repository.OptionRepository;
import com.educouch.educouchsystem.repository.QuestionBankRepository;
import com.educouch.educouchsystem.repository.QuestionRepository;
import com.educouch.educouchsystem.util.enumeration.QuestionTypeEnum;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionBankNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionBankServiceImpl implements QuestionBankService {

    @Autowired
    public CourseService courseService;

    @Autowired
    public QuestionService questionService;

    @Autowired
    public QuestionBankRepository questionBankRepository;

    @Autowired
    public OptionRepository optionRepository;

    @Autowired
    public QuestionRepository questionRepository;

    public QuestionBank createQuestionBank(String questionBankName, Long courseId) throws CourseNotFoundException {
        QuestionBank q = new QuestionBank(questionBankName, courseId);
        return questionBankRepository.save(q);
    }

    public Long deleteQuestionBank(Long questionBankId) throws QuestionBankNotFoundException {
        QuestionBank questionBankToDelete = questionBankRepository.findById(questionBankId).get();
        List<Question> questionsCopy = questionBankToDelete.getQuestions();
        questionsCopy.size();
        questionBankToDelete.setQuestions(new ArrayList<>());
        questionBankRepository.save(questionBankToDelete);
        for (Question q : questionsCopy) {
            questionRepository.delete(q);
        }
        questionBankRepository.delete(questionBankToDelete);
        return questionBankId;
    }

    public QuestionBank addQuestionToQuestionBank(Question questionToDuplicate, Long questionBankId) throws QuestionBankNotFoundException {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId).get();
        Question q = duplicateQuestion(questionToDuplicate);
        List<Option> options = q.getOptions();
        if (options.size() > 0) {
            for (Option o : options) {
                optionRepository.save(o);
            }
        }
        if (!(q.getCorrectOption() == null)) {
            optionRepository.save(q.getCorrectOption());
        }
        questionRepository.save(q);

        List<Question> questions = questionBank.getQuestions();
        questions.add(q);
        questionBank.setQuestions(questions);
        return questionBankRepository.save(questionBank);
    }

    public QuestionBank removeQuestionFromQuestionBank(Long questionBankId, Long questionToRemoveId) throws QuestionNotFoundException, QuestionBankNotFoundException {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId).get();
        for (Question q : questionBank.getQuestions()) {
            if (q.getQuestionId().equals(questionToRemoveId)) {
                questionBank.getQuestions().remove(q);
                questionRepository.delete(q);
                System.out.println("qBankServiceImpl || removeQuestion || removing question: " + questionToRemoveId);
                break;
            }
        }
        return questionBankRepository.save(questionBank);
    }

    public List<QuestionBank> getQuestionBanksByCourseId(Long courseId) throws QuestionBankNotFoundException {
        return questionBankRepository.findQuestionBanksByCourseId(courseId);
    }

    public Question duplicateQuestion(Question question) {
        question.setQuestionId(null);
        for (Option o : question.getOptions()) {
            o.setOptionId(null);
        }
        if (question.getQuestionType() != QuestionTypeEnum.OPEN_ENDED) {
            question.getCorrectOption().setOptionId(null);
        }
        return question;
    }

}
