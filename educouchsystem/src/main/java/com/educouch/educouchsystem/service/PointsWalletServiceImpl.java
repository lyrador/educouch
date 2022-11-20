package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.PointsWallet;
import com.educouch.educouchsystem.repository.PointsWalletRepository;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.PointsWalletNotFoundException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PointsWalletServiceImpl implements PointsWalletService{
    @Autowired
    PointsWalletRepository pointsWalletRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    OrganisationService organisationService;

    @Autowired
    LearnerService learnerService;

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

    @Override
    public Long findLearnerPoints(Long learnerId, Long courseId) throws PointsWalletNotFoundException{
        try {
            Course course = courseService.getCourseById(courseId);
            Organisation org = course.getOrganisation();
            PointsWallet pw = pointsWalletRepository.findByLearnerIdOrganisationId(learnerId, org.getOrganisationId());
            if(pw != null) {
                return pw.getDiscountPoints();
            } else {
                throw new PointsWalletNotFoundException("Unable to find any wallet!");
            }

        } catch(CourseNotFoundException ex) {
            return new Long(0);
        }
    }

    @Override
    public Double retrievePriceDiscount(Long learnerId, Long courseId) throws CourseNotFoundException, PointsWalletNotFoundException {
        // discount can only be implemented when they ae paying for 'remaining course fee' NOT 'deposit'
        try {
            Course course = courseService.getCourseById(courseId);
            Organisation org = course.getOrganisation();
            PointsWallet pw = pointsWalletRepository.findByLearnerIdOrganisationId(learnerId, org.getOrganisationId());
            if(pw != null) {
                Double currency = org.getCurrencyConversionNumber();
                Double points = org.getRewardPointsConversionNumber();
                Integer ownedPoints = pw.getDiscountPoints().intValue();

                Double maxDiscount =  Math.floor(ownedPoints / points) * currency;
                Double neededDiscount = course.getCourseFee().doubleValue() * 0.90;

                if(maxDiscount < neededDiscount) {
                    return maxDiscount;
                } else {
                    return neededDiscount;
                }
            } else {
                throw new PointsWalletNotFoundException("Unable to find any wallet!");
            }

        } catch(CourseNotFoundException ex) {
            return 0.0;
        }
    }

    @Override
    public Map<String, Double> retrieveCourseConversionRate(Long courseId) throws CourseNotFoundException {
        Map<String, Double> conversionRate = new HashMap<String, Double>();
        try {
            Course course = courseService.getCourseById(courseId);
            Organisation org = course.getOrganisation();
            Double currency = org.getCurrencyConversionNumber();
            Double points = org.getRewardPointsConversionNumber();

            conversionRate.put("points", points);
            conversionRate.put("currency", currency);

            return conversionRate;

        } catch(CourseNotFoundException ex) {
            conversionRate.put("points",1.0);
            conversionRate.put("currency", 0.0);
            return conversionRate;

        }
    }
}
