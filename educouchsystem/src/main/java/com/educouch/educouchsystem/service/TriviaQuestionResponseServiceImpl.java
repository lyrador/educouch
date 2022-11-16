package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TriviaQuestionResponse;
import com.educouch.educouchsystem.repository.TriviaQuestionResponseRepository;
import com.educouch.educouchsystem.util.exception.TriviaQuestionResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TriviaQuestionResponseServiceImpl implements TriviaQuestionResponseService{

    @Autowired
    private TriviaQuestionResponseRepository triviaQuestionResponseRepository;

    @Override
    public TriviaQuestionResponse saveTriviaQuestionResponse(TriviaQuestionResponse triviaQuestionResponse) {
        return triviaQuestionResponseRepository.save(triviaQuestionResponse);
    }

    @Override
    public List<TriviaQuestionResponse> getAllTriviaQuestionResponses() {
        return triviaQuestionResponseRepository.findAll();
    }

    @Override
    public TriviaQuestionResponse getTriviaQuestionResponseById(Long triviaQuestionResponseId) throws TriviaQuestionResponseNotFoundException {
        Optional<TriviaQuestionResponse> triviaQuestionResponseOptional = triviaQuestionResponseRepository.findById(triviaQuestionResponseId);
        if (triviaQuestionResponseOptional.isPresent()) {
            return triviaQuestionResponseOptional.get();
        } else {
            throw new TriviaQuestionResponseNotFoundException("Trivia Response cannot be found");
        }
    }

    @Override
    public void deleteTriviaQuestionResponse(Long id) {
        triviaQuestionResponseRepository.deleteById(id);
    }
}
