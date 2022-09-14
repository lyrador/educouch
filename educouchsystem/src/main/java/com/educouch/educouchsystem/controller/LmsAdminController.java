package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.LmsAdmin;
import com.educouch.educouchsystem.service.LmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Long createLmsAdmin(@RequestBody LmsAdmin lmsAdmin) {
        LmsAdmin newAdmin = lmsAdminService.saveLmsAdmin(lmsAdmin);
        return newAdmin.getLmsAdminId();
    }

    @PostMapping("/updateLmsAdmin")
    public Long updateLmsAdmin(@RequestBody LmsAdmin lmsAdmin) {
        LmsAdmin updatedAdmin = lmsAdminService.updateLmsAdmin(lmsAdmin);
        return updatedAdmin.getLmsAdminId();
    }


    @GetMapping("/getAll")
    public List<LmsAdmin> getAllLmsAdmins() {
        return lmsAdminService.getAllLmsAdmins();
    }
}
