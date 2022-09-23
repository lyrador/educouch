package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.repository.QuestionAttemptRepository;
import com.educouch.educouchsystem.util.exception.QuestionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionAttemptServiceImpl implements QuestionAttemptService {

    @Autowired
    QuestionAttemptRepository questionAttemptRepository;

    @Override
    public QuestionAttempt saveQuestionAttempt(QuestionAttempt questionAttempt) {
        return questionAttemptRepository.save(questionAttempt);
    }

    @Override
    public List<QuestionAttempt> getAllQuestionAttempts() {
        return questionAttemptRepository.findAll();
    }

    @Override
    public List<Option> getSubmittedOptions(Long questionAttemptId) throws QuestionAttemptNotFoundException{
        QuestionAttempt questionAttempt = questionAttemptRepository.findById(questionAttemptId).get();
        if (questionAttempt != null) {
            List<Option> questionAttemptOption = questionAttempt.getLearnerOptions();
            return questionAttemptOption;
        } else {
            throw new QuestionAttemptNotFoundException("QuestionAttempt Id " + questionAttemptId + " does not exist!");
        }
    }

    @Override
    public QuestionAttempt retrieveQuestionAttemptById(Long questionAttemptId) throws QuestionAttemptNotFoundException {
        QuestionAttempt questionAttempt = questionAttemptRepository.findById(questionAttemptId).get();
        if (questionAttempt != null) {
            return questionAttempt;
        } else {
            throw new QuestionAttemptNotFoundException("QuestionAttempt Id " + questionAttemptId + " does not exist!");
        }
    }

    @Override
    public void deleteQuestionAttempt(Long questionAttemptId) throws QuestionAttemptNotFoundException {
        QuestionAttempt questionAttempt = questionAttemptRepository.findById(questionAttemptId).get();
        if (questionAttempt != null) {
            questionAttemptRepository.deleteById(questionAttemptId);
        } else {
            throw new QuestionAttemptNotFoundException("QuestionAttempt Id " + questionAttemptId + " does not exist!");
        }
    }

    /*
    @Override
    public void calculateQuestionScore(Long questionAttemptId) throws QuestionAttemptNotFoundException, QuestionNotFoundException {
        QuestionAttempt questionAttempt = retrieveQuestionAttemptById(questionAttemptId);
        Question questionAttempted = questionAttempt.getQuestionAttempted();
        if (questionAttempted != null) {
            List<Option> learnerOptions = getSubmittedOptions(questionAttemptId);
            List<Answer> correctAnswers = questionAttempted.getAnswers();
            Double obtainedScore = 0.0;
            for (Option option : learnerOptions) {
                for (Answer ans : correctAnswers) {
                    if (option.getOptionContent().equals(ans.getAnswerContent())) {
                        obtainedScore += ans.getMaxScore();
                    }
                }
            }
            questionAttempt.setQuestionAttemptScore(obtainedScore);
            questionAttemptRepository.save(questionAttempt);
        } else {
            throw new QuestionNotFoundException("Question does not exist for this Question Attempt!");
        }
    }
    */
}
