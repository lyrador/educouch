package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.PollQuestionResponse;
import com.educouch.educouchsystem.util.exception.PollQuestionResponseNotFoundException;

import java.util.List;

public interface PollQuestionResponseService {

    public PollQuestionResponse savePollQuestionResponse (PollQuestionResponse pollQuestionResponse);

    public List<PollQuestionResponse> getAllPollQuestionResponse();

    public PollQuestionResponse getPollQuestionResponseById(Long pollQuestionResponseId) throws PollQuestionResponseNotFoundException;

    public void deletePollQuestionResponse(Long id);
}
