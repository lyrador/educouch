package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TriviaOption;
import com.educouch.educouchsystem.repository.TriviaOptionRepository;
import com.educouch.educouchsystem.util.exception.TriviaOptionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TriviaOptionServiceImpl implements TriviaOptionService{

    @Autowired
    private TriviaOptionRepository triviaOptionRepository;

    @Override
    public TriviaOption saveTriviaOption(TriviaOption triviaOption) {
        return triviaOptionRepository.save(triviaOption);
    }

    @Override
    public List<TriviaOption> getAllTriviaOptions() {
        return triviaOptionRepository.findAll();
    }

    @Override
    public TriviaOption getTriviaOptionById(Long triviaOptionId) throws TriviaOptionNotFoundException {
        Optional<TriviaOption> triviaOptionOptional = triviaOptionRepository.findById(triviaOptionId);
        if (triviaOptionOptional.isPresent()) {
            return triviaOptionOptional.get();
        } else {
            throw new TriviaOptionNotFoundException("Trivia Option cannot be found");
        }
    }

    @Override
    public void deleteTriviaOption(Long id) {
        triviaOptionRepository.deleteById(id);
    }
}
