package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.OpenEnded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenEndedRepository extends JpaRepository<OpenEnded, Long> {
}
