package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.model.PointsWallet;
import com.educouch.educouchsystem.service.OrgLmsRevenueMapService;
import com.educouch.educouchsystem.service.PointsWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
