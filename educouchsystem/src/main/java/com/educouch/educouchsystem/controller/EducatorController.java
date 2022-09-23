package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.OrganisationService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/educator")
@CrossOrigin
public class EducatorController {

    @Autowired
    private EducatorService educatorService;
    @Autowired
    private OrganisationService organisationService;

    @GetMapping("/getAllOrgAdmin")
    public ResponseEntity<List<OrganisationAdmin>> getAllOrgAdmin() {
        return new ResponseEntity<List<OrganisationAdmin>>(educatorService.findAllOrgAdmin(), HttpStatus.OK);
    }

    @PostMapping("/updateOrgAdmin")
    public ResponseEntity<OrganisationAdmin> updateOrgAdmin(@RequestBody OrganisationAdmin organisationAdmin) {
        OrganisationAdmin updatedAdmin =educatorService.updateOrganisationAdmin(organisationAdmin);
        return new ResponseEntity<OrganisationAdmin>(updatedAdmin, HttpStatus.OK);
    }
    @PutMapping("/instantiateOrganisation")
    public ResponseEntity<Organisation> instantiateOrganisation(@RequestBody OrganisationInstantiation organisationInstantiation) {

        Organisation orgToInstantiate = organisationInstantiation.getOrganisation();
        OrganisationAdmin orgAdminToInstantiate = organisationInstantiation.getOrganisationAdmin();

        organisationService.instantiateOrganisation(orgAdminToInstantiate, orgToInstantiate);

        Organisation instantiatedOrganisation = organisationService.findOrganisationByOrganisationName(orgToInstantiate.getOrganisationName());
        instantiatedOrganisation = deserializeOrganisation(instantiatedOrganisation);
        return new ResponseEntity<Organisation>(instantiatedOrganisation, HttpStatus.OK);
    }

    @GetMapping("/findAllInstructors")
    public ResponseEntity<List<Instructor>> findAllInstructors(@RequestParam String organisationId) {
        List<Instructor> instr = new ArrayList<>();
        if (!organisationService.findAllInstructors(organisationId).isEmpty()) {
            instr = organisationService.findAllInstructors(organisationId);
            for (Instructor i : instr) {
                i = deserializeInstructor(i);
            }
            return new ResponseEntity<>(instr, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findInstructor")
    public ResponseEntity<Instructor> findInstructor(@RequestParam String instructorUsername) {

        Instructor instructor = educatorService.findInstructorByUsername(instructorUsername);
        if(instructor!=null) {
            instructor = deserializeInstructor(instructor);
            return new ResponseEntity<Instructor>(instructor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/addInstructor")
    public String addInstructor(@RequestParam String organisationId, @RequestBody Instructor instructor) {
        organisationService.addInstructor(organisationId, instructor);
        return instructor.getUsername() + " user successfully added";
    }

    //need to do updateInstructor

    public Instructor deserializeInstructor(Instructor i) {

        Organisation tempOrg = i.getOrganisation();
        tempOrg.getOrganisationAdmin().setOrganisation(null);
        tempOrg.setInstructors(null);
        return i;
    }

    public Organisation deserializeOrganisation(Organisation o) {

        List<Instructor> instr = o.getInstructors();
        for(Instructor i : instr) {
            i.setOrganisation(null);
        }
        o.getOrganisationAdmin().setOrganisation(null);
        return o;
    }
}

class OrganisationInstantiation {

    private Organisation organisation;
    private OrganisationAdmin organisationAdmin;

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public OrganisationAdmin getOrganisationAdmin() {
        return organisationAdmin;
    }

    public void setOrganisationAdmin(OrganisationAdmin organisationAdmin) {
        this.organisationAdmin = organisationAdmin;
    }
}
