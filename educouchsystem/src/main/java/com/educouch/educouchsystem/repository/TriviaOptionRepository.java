package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.TriviaOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaOptionRepository extends JpaRepository<TriviaOption, Long> {
}
