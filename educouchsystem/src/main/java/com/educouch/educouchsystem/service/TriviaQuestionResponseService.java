package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TriviaQuestionResponse;
import com.educouch.educouchsystem.util.exception.TriviaQuestionResponseNotFoundException;

import java.util.List;

public interface TriviaQuestionResponseService {

    public TriviaQuestionResponse saveTriviaQuestionResponse (TriviaQuestionResponse triviaQuestionResponse);

    public List<TriviaQuestionResponse> getAllTriviaQuestionResponses();

    public TriviaQuestionResponse getTriviaQuestionResponseById(Long triviaQuestionResponseId) throws TriviaQuestionResponseNotFoundException;

    public void deleteTriviaQuestionResponse(Long id);
}
