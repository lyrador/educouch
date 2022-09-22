package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.model.QuizAttempt;
import com.educouch.educouchsystem.repository.QuizAttemptRepository;
import com.educouch.educouchsystem.util.exception.QuestionAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizAttemptNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QuizAttemptServiceImpl implements QuizAttemptService {

    @Autowired
    QuizAttemptRepository quizAttemptRepository;

    @Override
    public QuizAttempt saveQuizAttempt(QuizAttempt quizAttempt) {
        return quizAttemptRepository.save(quizAttempt);
    }

    @Override
    public List<QuizAttempt> getAllQuizAttempts() {
        return quizAttemptRepository.findAll();
    }

    @Override
    public List<QuestionAttempt> getSubmittedQuestionAttempts(Long quizAttemptId) throws QuizAttemptNotFoundException {
        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId).get();
        if (quizAttempt != null) {
            List<QuestionAttempt> questionAttempts = quizAttempt.getQuestionAttempts();
            return questionAttempts;
        } else {
            throw new QuizAttemptNotFoundException("QuizAttempt Id " + quizAttemptId + " does not exist!");
        }
    }

    @Override
    public QuizAttempt retrieveQuizAttemptById(Long quizAttemptId) throws QuizAttemptNotFoundException {
        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId).get();
        if (quizAttempt != null) {
            return quizAttempt;
        } else {
            throw new QuizAttemptNotFoundException("QuizAttempt Id " + quizAttemptId + " does not exist!");
        }
    }

    @Override
    public void deleteQuizAttempt(Long quizAttemptId) throws QuizAttemptNotFoundException {
        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId).get();
        if (quizAttempt != null) {
            quizAttemptRepository.deleteById(quizAttemptId);
        } else {
            throw new QuizAttemptNotFoundException("QuizAttempt Id " + quizAttemptId + " does not exist!");
        }
    }

    @Override
    public void calculateQuestionScore(Long quizAttemptId) throws QuizAttemptNotFoundException, QuestionAttemptNotFoundException {
        QuizAttempt quizAttempt = retrieveQuizAttemptById(quizAttemptId);
        List<QuestionAttempt> questionAttempts = quizAttempt.getQuestionAttempts();
        if (questionAttempts != null) {
            Double obtainedScore = 0.0;
            for (QuestionAttempt questionAttempt : questionAttempts) {
                obtainedScore += questionAttempt.getQuestionAttemptScore();
            }
            quizAttempt.setObtainedScore(obtainedScore);
            quizAttemptRepository.save(quizAttempt);
        } else {
            throw new QuestionAttemptNotFoundException("QuestionAttempt does not exist for this Quiz Attempt!");
        }
    }
}
