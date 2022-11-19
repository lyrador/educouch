package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Poll;
import com.educouch.educouchsystem.model.PollQuestion;
import com.educouch.educouchsystem.repository.PollQuestionRepository;
import com.educouch.educouchsystem.util.exception.PollQuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollQuestionServiceImpl implements PollQuestionService{

    @Autowired
    private PollQuestionRepository pollQuestionRepository;

    @Override
    public PollQuestion savePollQuestion(PollQuestion pollQuestion) {
        return pollQuestionRepository.save(pollQuestion);
    }

    @Override
    public List<PollQuestion> getAllPollQuestions() {
        return pollQuestionRepository.findAll();
    }

    @Override
    public PollQuestion getPollQuestionById(Long pollQuestionId) throws PollQuestionNotFoundException {
        Optional<PollQuestion> pollQuestionOptional = pollQuestionRepository.findById(pollQuestionId);
        if(pollQuestionOptional.isPresent()) {
            return pollQuestionOptional.get();
        } else {
            throw new PollQuestionNotFoundException("Poll Question cannot be found");
        }
    }

    @Override
    public void deletePollQuestion(Long id) {
        pollQuestionRepository.deleteById(id);
    }
}
