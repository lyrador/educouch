package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TriviaQuiz;
import com.educouch.educouchsystem.repository.TriviaQuizRepository;
import com.educouch.educouchsystem.util.exception.TriviaQuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TriviaQuizServiceImpl implements TriviaQuizService{

    @Autowired
    private TriviaQuizRepository triviaQuizRepository;

    @Override
    public TriviaQuiz saveTriviaQuiz(TriviaQuiz triviaQuiz) {
        return triviaQuizRepository.save(triviaQuiz);
    }

    @Override
    public List<TriviaQuiz> getAllTriviaQuizzes() {
        return triviaQuizRepository.findAll();
    }

    @Override
    public TriviaQuiz getTriviaQuizById(Long triviaQuizId) throws TriviaQuizNotFoundException {
        Optional<TriviaQuiz> triviaQuizOptional = triviaQuizRepository.findById(triviaQuizId);
        if(triviaQuizOptional.isPresent()) {
            return triviaQuizOptional.get();
        } else {
            throw new TriviaQuizNotFoundException("Trivia Quiz Cannot Be Found");
        }
    }

    @Override
    public void deleteTriviaQuiz(Long id) {
        triviaQuizRepository.deleteById(id);
    }
}
