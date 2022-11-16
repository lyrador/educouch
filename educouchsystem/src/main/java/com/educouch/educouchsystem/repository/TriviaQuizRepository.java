package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.TriviaQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaQuizRepository extends JpaRepository<TriviaQuiz, Long> {
}
