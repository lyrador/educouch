package com.educouch.educouchsystem.controller;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.service.EducatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/educator")
@CrossOrigin
public class EducatorController {

    @Autowired
    EducatorService educatorService;

    @PostMapping("/addOrganisation")
    public String addOrganisation(@RequestBody Organisation organisation) {
        educatorService.saveOrganisation(organisation);
        return "New Organisation is added";
    }

    @PostMapping("/addOrganisationAdmin")
    public String addOrganisationAdmin(@RequestBody OrganisationAdmin organisationAdmin) {
        educatorService.saveOrganisationAdmin(organisationAdmin);
        return "New Org Admin is added";
    }

    @PostMapping("/addInstructor")
    public String addInstructor(@RequestBody Instructor instructor) {

        return "New Instructor is added";
    }

}
