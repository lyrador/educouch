package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Poll;
import com.educouch.educouchsystem.util.exception.PollNotFoundException;

import java.util.List;

public interface PollService {

    public Poll savePoll(Poll poll);

    public List<Poll> getAllPolls();

    public Poll getPollById(Long pollId) throws PollNotFoundException;

    public void deletePoll(Long id);
}
