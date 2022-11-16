package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.TechnicalSupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalSupportRequestRepository extends JpaRepository<TechnicalSupportRequest, Long> {
}
