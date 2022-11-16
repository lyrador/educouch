package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.QuestionBank;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionBankNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionBankService {
    public QuestionBank createQuestionBank(String questionBankName, Long courseId) throws CourseNotFoundException;
    public Long deleteQuestionBank(Long questionBankId) throws QuestionBankNotFoundException;
    public QuestionBank addQuestionToQuestionBank(Question questionToDuplicate, Long questionBankId) throws QuestionBankNotFoundException;
    public QuestionBank removeQuestionFromQuestionBank(Long questionToRemoveId, Long questionBankId) throws QuestionNotFoundException, QuestionBankNotFoundException;
    public List<QuestionBank> getQuestionBanksByCourseId(Long courseId) throws QuestionBankNotFoundException;
}
