package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Educator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducatorRepository extends JpaRepository<Educator, Integer> {
}
