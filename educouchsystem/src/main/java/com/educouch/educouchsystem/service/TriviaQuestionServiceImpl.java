package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TriviaQuestion;
import com.educouch.educouchsystem.repository.TriviaQuestionRepository;
import com.educouch.educouchsystem.util.exception.TriviaQuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TriviaQuestionServiceImpl implements TriviaQuestionService{

    @Autowired
    private TriviaQuestionRepository triviaQuestionRepository;

    @Override
    public TriviaQuestion saveTriviaQuestion(TriviaQuestion triviaQuestion) {
        return triviaQuestionRepository.save(triviaQuestion);
    }

    @Override
    public List<TriviaQuestion> getAllTriviaQuestions() {
        return triviaQuestionRepository.findAll();
    }

    @Override
    public TriviaQuestion getTriviaQuestionById(Long triviaQuestionId) throws TriviaQuestionNotFoundException {
        Optional<TriviaQuestion> triviaQuestionOptional = triviaQuestionRepository.findById(triviaQuestionId);
        if(triviaQuestionOptional.isPresent()) {
            return triviaQuestionOptional.get();
        } else {
            throw new TriviaQuestionNotFoundException("Trivia Question cannot be found");
        }
    }

    @Override
    public void deleteTriviaQuestion(Long id) {
        triviaQuestionRepository.deleteById(id);
    }
}
