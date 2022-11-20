package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.OrganisationDTO;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/organisation")
@CrossOrigin
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @GetMapping("/get/{organisationId}")
    public ResponseEntity<Organisation> retrieveOrganisationById(@PathVariable(value = "organisationId") Long organisationId) {
        try {
            Organisation existingOrganisation = organisationService.findOrganisationById(organisationId);
            existingOrganisation.setInstructors(null);
            existingOrganisation.setCourses(null);
            return new ResponseEntity<Organisation>(existingOrganisation, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/put/{organisationId}")
    public ResponseEntity<Organisation> updateOrganisation(@PathVariable(value = "organisationId") Long organisationId, @RequestBody OrganisationDTO organisationDTORequest) {
        try {
            Organisation existingOrganisation = organisationService.findOrganisationById(organisationId);
            existingOrganisation.setRewardPointsConversionNumber(organisationDTORequest.getRewardPointsConversionNumber());
            existingOrganisation.setCurrencyConversionNumber(organisationDTORequest.getCurrencyConversionNumber());
            existingOrganisation.setMaxAssignmentPoints(organisationDTORequest.getMaxAssignmentPoints());
            organisationService.saveOrganisation(existingOrganisation);
            existingOrganisation.setInstructors(null);
            existingOrganisation.setCourses(null);
            return new ResponseEntity<Organisation>(existingOrganisation, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
