package com.educouch.educouchsystem.repository;
import com.educouch.educouchsystem.model.DepositRefundRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRefundRequestRepository extends JpaRepository<DepositRefundRequest, Long> {
}
