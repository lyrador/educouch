package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.LmsAdmin;
import com.educouch.educouchsystem.service.LmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lmsadmin")
@CrossOrigin
public class LmsAdminController {
    private final LmsAdminService lmsAdminService;

    @Autowired
    public LmsAdminController(LmsAdminService lmsAdminService) {
        this.lmsAdminService = lmsAdminService;
    }

    @PostMapping("/addLmsAdmin")
    public ResponseEntity<LmsAdmin> createLmsAdmin(@RequestBody LmsAdmin lmsAdmin) {
        LmsAdmin newAdmin = lmsAdminService.saveLmsAdmin(lmsAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(newAdmin);
    }

    @PostMapping("/updateLmsAdmin")
    public ResponseEntity<LmsAdmin> updateLmsAdmin(@RequestBody LmsAdmin lmsAdmin) {
        LmsAdmin updatedAdmin = lmsAdminService.updateLmsAdmin(lmsAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAdmin);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<LmsAdmin>> getAllLmsAdmins() {
        return ResponseEntity.status(HttpStatus.OK).body(lmsAdminService.getAllLmsAdmins());
    }

    @PostMapping("/login")
    public ResponseEntity<LmsAdmin> loginLmsAdmin(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK).body(lmsAdminService.login(username,password));

    }

    @PostMapping("/deleteLmsAdmin")
    public ResponseEntity<Long> deleteLmsAdmin(@RequestBody LmsAdmin lmsAdmin) {
        lmsAdminService.deleteLmsAdmin(lmsAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(lmsAdmin.getLmsAdminId());
    }

}
