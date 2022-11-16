package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.QuestionAttempt;
import com.educouch.educouchsystem.model.QuestionBank;
import com.educouch.educouchsystem.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestionBankRepository  extends JpaRepository<QuestionBank, Long> {

    @Query("SELECT q FROM QuestionBank q WHERE q.courseId=:courseId")
    List<QuestionBank> findQuestionBanksByCourseId(Long courseId);
}
