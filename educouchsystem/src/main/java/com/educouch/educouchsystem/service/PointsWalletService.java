package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.model.PointsWallet;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.PointsWalletNotFoundException;

import java.util.List;
import java.util.Map;

public interface PointsWalletService {

    public PointsWallet createWallet(Long learnerId, Long organisationId, String orgName);

    public PointsWallet saveWallet(PointsWallet wallet);

    public List<PointsWallet> findAllWalletForLearner(Long learnerId);

    public PointsWallet updatePointsWallet(PointsWallet wallet)  throws PointsWalletNotFoundException;

    public PointsWallet findParticularWallet(Long learnerId, Long organisationId) throws PointsWalletNotFoundException;

    public Long findLearnerPoints(Long learnerId, Long courseId) throws CourseNotFoundException, PointsWalletNotFoundException;

    public Double retrievePriceDiscount(Long learnerId, Long courseId) throws CourseNotFoundException, PointsWalletNotFoundException;

    public Map<String, Double> retrieveCourseConversionRate(Long courseId) throws CourseNotFoundException;


}
