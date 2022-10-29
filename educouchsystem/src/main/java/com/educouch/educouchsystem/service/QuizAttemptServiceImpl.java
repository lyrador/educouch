package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.OptionRepository;
import com.educouch.educouchsystem.repository.QuestionAttemptRepository;
import com.educouch.educouchsystem.repository.QuizAttemptRepository;
import com.educouch.educouchsystem.util.enumeration.AssessmentAttemptStatusEnum;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizAttemptServiceImpl implements QuizAttemptService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private QuizServiceImpl quizService;

    @Autowired
    private LearnerServiceImpl learnerService;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private QuestionAttemptRepository questionAttemptRepository;

    @Override
    public QuizAttempt saveQuizAttempt(Quiz quiz, QuizAttempt quizAttempt, Learner learner) throws QuizNotFoundException {
        Quiz quizToUpdate = quizService.retrieveQuizById(quiz.getAssessmentId());
        Learner learnerToUpdate = learnerService.getLearnerById(learner.getLearnerId());
        if(quizToUpdate != null) {
            List<QuestionAttempt> questionAttempts = quizAttempt.getQuestionAttempts();
            for(QuestionAttempt q : questionAttempts) {
                optionRepository.save(q.getOptionSelected());
                questionAttemptRepository.save(q);
            }
//            quizAttemptRepository.save(quizAttempt);
            quizAttempt.setLearner(learnerToUpdate);
            quizAttempt.setAttemptedQuiz(quiz);
            quizAttemptRepository.save(quizAttempt);
//            quizService.saveQuiz(quizToUpdate);
            return quizAttempt;
        } else {
            throw new QuizNotFoundException();
        }
    }

    @Override
    public List<QuizAttempt> getQuizAttemptsByLearnerId(Long learnerId) throws NoQuizAttemptsFoundException {
        List<QuizAttempt> quizAttempts = quizAttemptRepository.findQuizAttemptsByLearnerId(learnerId);
            if(quizAttempts.size()!=0) {
            return quizAttempts;
        } else {
            throw new NoQuizAttemptsFoundException();
        }
    }

    @Override
    public List<QuizAttempt> getParticularQuizAttemptsByLearnerId(Long learnerId, Long assessmentId) throws NoQuizAttemptsFoundException {
        List<QuizAttempt> allQuizAttempts = getQuizAttemptsByLearnerId(learnerId);
        if(allQuizAttempts.size()!=0) {
            return allQuizAttempts.stream().filter(quizAttempt -> quizAttempt.getAttemptedQuiz().getAssessmentId().equals(assessmentId)).collect(Collectors.toList());
        } else {
            throw new NoQuizAttemptsFoundException();
        }
    }

    @Override
    public QuizAttempt getMostRecentQuizAttemptByLearnerId(Long learnerId, Long assessmentId) throws NoQuizAttemptsFoundException{

        List<QuizAttempt> allQuizAttempts = getParticularQuizAttemptsByLearnerId(learnerId, assessmentId);
        QuizAttempt mostRecentAttempt = allQuizAttempts.get(0);
        for(QuizAttempt q : allQuizAttempts) {
            if(q.getLastAttemptTime().after(mostRecentAttempt.getLastAttemptTime())) {
                mostRecentAttempt = q;
            }
        }
        return mostRecentAttempt;
    }

    @Override
    public QuizAttempt getQuizAttemptById(Long quizAttemptID) throws QuizAttemptNotFoundException {
        QuizAttempt q = quizAttemptRepository.findById(quizAttemptID).get();
        if(q!=null) {
            return q;
        } else {
            throw new QuizAttemptNotFoundException();
        }
    }

    @Override
    public QuizAttempt updateQuizAttempt(QuizAttempt updatedQuizAttempt) throws QuizAttemptNotFoundException{
        QuizAttempt quizAttemptToUpdate = getQuizAttemptById(updatedQuizAttempt.getQuizAttemptId());
        List<QuestionAttempt> questionAttemptsToUpdate = quizAttemptToUpdate.getQuestionAttempts();
        List<QuestionAttempt> updatedQuestionAttempts = updatedQuizAttempt.getQuestionAttempts();
        for(int i=0; i<questionAttemptsToUpdate.size(); i++) {
            QuestionAttempt q = questionAttemptsToUpdate.get(i);
//            if(q.getOptionSelected()!=null) {
//                optionRepository.deleteById(q.getOptionSelected().getOptionId()); //delete old option selected
//            }
            q.setShortAnswerResponse(updatedQuestionAttempts.get(i).getShortAnswerResponse());
            q.setOptionSelected(updatedQuestionAttempts.get(i).getOptionSelected());
            optionRepository.save(updatedQuestionAttempts.get(i).getOptionSelected()); //set new option selected
        }
        quizAttemptToUpdate.setAttemptCounter(quizAttemptToUpdate.getAttemptCounter());
        quizAttemptToUpdate.setAssessmentAttemptStatusEnum(updatedQuizAttempt.getAssessmentAttemptStatusEnum());
        quizAttemptToUpdate.setTimeLimitRemaining(updatedQuizAttempt.getTimeLimitRemaining());
        quizAttemptRepository.save(quizAttemptToUpdate);

        return quizAttemptToUpdate;
    }

    @Override
    public QuizAttempt submitQuizAttempt(QuizAttempt updatedQuizAttempt) throws QuizAttemptNotFoundException {
        QuizAttempt quizAttemptToUpdate = getQuizAttemptById(updatedQuizAttempt.getQuizAttemptId());
        quizAttemptToUpdate.setAssessmentAttemptStatusEnum(AssessmentAttemptStatusEnum.SUBMITTED);
        return quizAttemptToUpdate;
    }


//    @Override
//    public List<QuizAttempt> getAllQuizAttempts() {
//        return quizAttemptRepository.findAll();
//    }
//
//    @Override
//    public List<QuestionAttempt> getSubmittedQuestionAttempts(Long quizAttemptId) throws QuizAttemptNotFoundException {
//        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId).get();
//        if (quizAttempt != null) {
//            List<QuestionAttempt> questionAttempts = quizAttempt.getQuestionAttempts();
//            return questionAttempts;
//        } else {
//            throw new QuizAttemptNotFoundException("QuizAttempt Id " + quizAttemptId + " does not exist!");
//        }
//    }
//
//    @Override
//    public QuizAttempt retrieveQuizAttemptById(Long quizAttemptId) throws QuizAttemptNotFoundException {
//        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId).get();
//        if (quizAttempt != null) {
//            return quizAttempt;
//        } else {
//            throw new QuizAttemptNotFoundException("QuizAttempt Id " + quizAttemptId + " does not exist!");
//        }
//    }

//    @Override
//    public void deleteQuizAttempt(Long quizAttemptId) throws QuizAttemptNotFoundException {
//        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId).get();
//        if (quizAttempt != null) {
//            quizAttemptRepository.deleteById(quizAttemptId);
//        } else {
//            throw new QuizAttemptNotFoundException("QuizAttempt Id " + quizAttemptId + " does not exist!");
//        }
//    }

//    @Override
//    public void calculateQuestionScore(Long quizAttemptId) throws QuizAttemptNotFoundException, QuestionAttemptNotFoundException {
//        QuizAttempt quizAttempt = retrieveQuizAttemptById(quizAttemptId);
//        List<QuestionAttempt> questionAttempts = quizAttempt.getQuestionAttempts();
//        if (questionAttempts != null) {
//            Double obtainedScore = 0.0;
//            for (QuestionAttempt questionAttempt : questionAttempts) {
//                obtainedScore += questionAttempt.getQuestionAttemptScore();
//            }
//            quizAttempt.setObtainedScore(obtainedScore);
//            quizAttemptRepository.save(quizAttempt);
//        } else {
//            throw new QuestionAttemptNotFoundException("QuestionAttempt does not exist for this Quiz Attempt!");
//        }
//    }
}

