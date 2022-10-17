package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.InteractiveBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractiveBookRepository extends JpaRepository<InteractiveBook, Long> {
}
