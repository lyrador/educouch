package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.TriviaQuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaQuestionResponseRepository extends JpaRepository<TriviaQuestionResponse, Long> {
}
