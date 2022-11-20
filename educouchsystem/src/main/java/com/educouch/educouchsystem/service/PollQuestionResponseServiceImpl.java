package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.PollQuestionResponse;
import com.educouch.educouchsystem.repository.PollQuestionResponseRepository;
import com.educouch.educouchsystem.util.exception.PollQuestionResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollQuestionResponseServiceImpl implements PollQuestionResponseService{

    @Autowired
    private PollQuestionResponseRepository pollQuestionResponseRepository;

    @Override
    public PollQuestionResponse savePollQuestionResponse(PollQuestionResponse pollQuestionResponse) {
        return pollQuestionResponseRepository.save(pollQuestionResponse);
    }

    @Override
    public List<PollQuestionResponse> getAllPollQuestionResponse() {
        return pollQuestionResponseRepository.findAll();
    }

    @Override
    public PollQuestionResponse getPollQuestionResponseById(Long pollQuestionResponseId) throws PollQuestionResponseNotFoundException {
        Optional<PollQuestionResponse> pollQuestionResponseOptional = pollQuestionResponseRepository.findById(pollQuestionResponseId);
        if (pollQuestionResponseOptional.isPresent()) {
            return pollQuestionResponseOptional.get();
        } else {
            throw new PollQuestionResponseNotFoundException("Poll Question Response cannot be found");
        }
    }

    @Override
    public void deletePollQuestionResponse(Long id) {
        pollQuestionResponseRepository.deleteById(id);
    }
}
