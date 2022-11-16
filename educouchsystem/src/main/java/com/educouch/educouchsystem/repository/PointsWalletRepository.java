package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.model.PointsWallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointsWalletRepository extends CrudRepository<PointsWallet, Long> {
    public List<PointsWallet> findAll();

    @Query("SELECT p FROM PointsWallet p WHERE p.learnerId =:learnerId")
    public List<PointsWallet> findAllByLearnerId(Long learnerId);

    @Query("SELECT p FROM PointsWallet p WHERE p.learnerId =:learnerId AND p.organisationId =:organisationId")
    public PointsWallet findByLearnerIdOrganisationId(Long learnerId, Long organisationId);
}
