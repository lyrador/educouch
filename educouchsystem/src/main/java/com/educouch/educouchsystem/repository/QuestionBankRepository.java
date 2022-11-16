package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.model.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface QuestionBankRepository  extends JpaRepository<QuestionBank, Long> {
}
