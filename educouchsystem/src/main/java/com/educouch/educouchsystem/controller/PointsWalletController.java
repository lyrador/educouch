package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.PurchaseItemDTO;
import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.model.PointsWallet;
import com.educouch.educouchsystem.service.OrgLmsRevenueMapService;
import com.educouch.educouchsystem.service.PointsWalletService;
import com.educouch.educouchsystem.util.enumeration.ItemSizeEnum;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.InsufficientTreePointBalanceException;
import com.educouch.educouchsystem.util.exception.LocationOccupiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pointsWallet")
@CrossOrigin
public class PointsWalletController {

    private final PointsWalletService pointsWalletService;

    @Autowired
    public PointsWalletController(PointsWalletService pointsWalletService){
        this.pointsWalletService = pointsWalletService;
    }

    @GetMapping("/getPointsWalletByLearner")
    public ResponseEntity<List<PointsWallet>> getForOrgName(@RequestParam Long learnerId) {
        return ResponseEntity.status(HttpStatus.OK).body(pointsWalletService.findAllWalletForLearner(learnerId));
    }

    @GetMapping("/getPointsByLearnerAndCourse")
    public ResponseEntity<String> findLearnerPoint(@RequestParam Long learnerId, @RequestParam Long courseId) {
        try {
            Long points = pointsWalletService.findLearnerPoints(learnerId, courseId);
            return new ResponseEntity<>(points.toString(), HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>("0", HttpStatus.OK);
        }

    }

    @GetMapping("/getPointsDiscount")
    public ResponseEntity<Double> findPointsDiscount(@RequestParam Long learnerId, @RequestParam Long courseId) {
        try {
            Double discount = pointsWalletService.retrievePriceDiscount(learnerId, courseId);
            return new ResponseEntity<>(discount,HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>(0.0, HttpStatus.OK);
        }

    }

    @GetMapping("/retrieveCourseConversionRate")
    @ResponseBody
    public ResponseEntity<Map<String,Double>> retrieveCourseConversionRate(@RequestParam Long courseId){
        try{
            Map<String, Double> result = pointsWalletService.retrieveCourseConversionRate(courseId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            Map<String, Double> conversionRate = new HashMap<>();
            conversionRate.put("currency", 0.0);
            conversionRate.put("points", 1.0);
            return new ResponseEntity<>(conversionRate, HttpStatus.OK);
        }
    }


}
