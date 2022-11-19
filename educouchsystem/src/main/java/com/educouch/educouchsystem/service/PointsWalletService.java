package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.model.PointsWallet;
import com.educouch.educouchsystem.util.exception.PointsWalletNotFoundException;

import java.util.List;

public interface PointsWalletService {

    public PointsWallet createWallet(Long learnerId, Long organisationId, String orgName);

    public PointsWallet saveWallet(PointsWallet wallet);

    public List<PointsWallet> findAllWalletForLearner(Long learnerId);

    public PointsWallet updatePointsWallet(PointsWallet wallet)  throws PointsWalletNotFoundException;

    public PointsWallet findParticularWallet(Long learnerId, Long organisationId) throws PointsWalletNotFoundException;
}
