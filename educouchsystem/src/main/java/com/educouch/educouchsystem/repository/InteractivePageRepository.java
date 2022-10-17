package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.InteractivePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractivePageRepository extends JpaRepository<InteractivePage, Long> {
}
