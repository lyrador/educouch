package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Answer;
import com.educouch.educouchsystem.repository.AnswerRepository;
import com.educouch.educouchsystem.util.exception.AnswerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Override
    public Answer retrieveAnswerById(Long answerId) throws AnswerNotFoundException {
        Answer answer = answerRepository.findById(answerId).get();
        if (answer != null) {
            return answer;
        } else {
            throw new AnswerNotFoundException("Answer Id " + answerId + " does not exist!");
        }
    }

    @Override
    public void deleteAnswer(Long answerId) throws AnswerNotFoundException {
        Answer answer = answerRepository.findById(answerId).get();
        if (answer != null) {
            answerRepository.deleteById(answerId);
        } else {
            throw new AnswerNotFoundException("Answer Id " + answerId + " does not exist!");
        }
    }
}
