package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.PointsWallet;
import com.educouch.educouchsystem.repository.PointsWalletRepository;
import com.educouch.educouchsystem.util.exception.PointsWalletNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsWalletServiceImpl implements PointsWalletService{
    @Autowired
    PointsWalletRepository pointsWalletRepository;

    @Override
    public PointsWallet createWallet(Long learnerId, Long organisationId, String orgName) {
        PointsWallet exists = pointsWalletRepository.findByLearnerIdOrganisationId(learnerId,organisationId);
        if(exists == null) {
            exists = pointsWalletRepository.save(new PointsWallet(learnerId,organisationId, orgName));
        }

        return exists;
    }

    @Override
    public PointsWallet saveWallet(PointsWallet wallet) {
        return pointsWalletRepository.save(wallet);
    }

    @Override
    public List<PointsWallet> findAllWalletForLearner(Long learnerId) {
        return pointsWalletRepository.findAllByLearnerId(learnerId);
    }

    @Override
    public PointsWallet updatePointsWallet(PointsWallet wallet) throws PointsWalletNotFoundException {
        PointsWallet walletToUpdate = findParticularWallet(wallet.getLearnerId(),wallet.getOrganisationId());
        walletToUpdate.setDiscountPoints(wallet.getDiscountPoints());
        pointsWalletRepository.save(walletToUpdate);
        return walletToUpdate;
    }

    @Override
    public PointsWallet findParticularWallet(Long learnerId, Long organisationId) throws PointsWalletNotFoundException {
        PointsWallet wallet = pointsWalletRepository.findByLearnerIdOrganisationId(learnerId,organisationId);
        if(wallet == null) {
            throw new PointsWalletNotFoundException("Wallet does not exist!");
        }
        return wallet;
    }
}
