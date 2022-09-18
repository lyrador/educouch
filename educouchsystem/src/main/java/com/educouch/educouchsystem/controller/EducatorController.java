package com.educouch.educouchsystem.controller;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/educator")
@CrossOrigin
public class EducatorController {

    @Autowired
    private EducatorService educatorService;
    @Autowired
    private OrganisationService organisationService;

    @PostMapping("/addOrganisation")
    public String addOrganisation(@RequestBody Organisation organisation) {
        organisationService.saveOrganisation(organisation);
        return "New Organisation is added";
    }

    @PostMapping("/addOrganisationAdmin")
    public String addOrganisationAdmin(@RequestBody OrganisationAdmin organisationAdmin) {
        educatorService.saveOrganisationAdmin(organisationAdmin);
        return "New Org Admin is added";
    }

    @PostMapping("/addInstructor")
    public String addInstructor(@RequestBody String organisationId, Instructor instructor) {
        organisationService.addInstructor(organisationId, instructor);
        return "New Instructor is added";
    }

    @GetMapping("/findAllInstructors")
    public List<Instructor> findAllInstructors(@RequestBody String organisationId) {
        return organisationService.findAllInstructors(organisationId);
    }

}
