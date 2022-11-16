package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TriviaOption;
import com.educouch.educouchsystem.util.exception.TriviaOptionNotFoundException;

import java.util.List;

public interface TriviaOptionService {

    public TriviaOption saveTriviaOption (TriviaOption triviaOption);

    public List<TriviaOption> getAllTriviaOptions();

    public TriviaOption getTriviaOptionById(Long triviaOptionId) throws TriviaOptionNotFoundException;

    public void deleteTriviaOption(Long id);
}
