package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.TriviaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaQuestionRepository extends JpaRepository<TriviaQuestion, Long> {
}
