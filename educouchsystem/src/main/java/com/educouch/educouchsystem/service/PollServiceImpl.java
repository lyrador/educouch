package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Poll;
import com.educouch.educouchsystem.repository.PollRepository;
import com.educouch.educouchsystem.util.exception.PollNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService{

    @Autowired
    private PollRepository pollRepository;

    @Override
    public Poll savePoll(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    @Override
    public Poll getPollById(Long pollId) throws PollNotFoundException {
        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if(pollOptional.isPresent()) {
            return pollOptional.get();
        } else {
            throw new PollNotFoundException("Poll cannot be found");
        }
    }

    @Override
    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
}
