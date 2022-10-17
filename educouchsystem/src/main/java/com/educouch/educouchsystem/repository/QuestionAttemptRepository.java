package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.QuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {

//    @Query("SELECT i FROM Instructor i WHERE i.username=:username")
//    Instructor findInstructorByUsername(String username);

//    List<QuestionAttempt> findQuestionAttemptsByLearnerId

}
