package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Poll;
import com.educouch.educouchsystem.model.PollQuestion;
import com.educouch.educouchsystem.util.exception.PollQuestionNotFoundException;

import java.util.List;

public interface PollQuestionService {

    public PollQuestion savePollQuestion(PollQuestion pollQuestion);

    public List<PollQuestion> getAllPollQuestions();

    public PollQuestion getPollQuestionById(Long pollQuestionId) throws PollQuestionNotFoundException;

    public void deletePollQuestion(Long id);
}
