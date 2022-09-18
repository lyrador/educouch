package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private EducatorService educatorService;

    public Organisation findOrganisationById(Long Id) {
        return organisationRepository.findOrganisationById(Id);
    }

    public Organisation saveOrganisation(Organisation organisation) {

        return organisationRepository.save(organisation);
    }

    public Organisation instantiateOrganisation(OrganisationAdmin organisationAdmin, Organisation organisation) {

        educatorService.saveOrganisationAdmin(organisationAdmin);
        saveOrganisation(organisation);

        Organisation managedOrganisation = findOrganisationById(organisation.getOrganisationId());
        OrganisationAdmin managedOrganisationAdmin = educatorService.findOrganisationAdminById(organisationAdmin.getOrganisationAdminId());

        managedOrganisation.setOrganisationAdmin(organisationAdmin);
        managedOrganisation.setInstructors(new ArrayList<>());
        saveOrganisation(managedOrganisation);

        managedOrganisationAdmin.setOrganisation(managedOrganisation);
        educatorService.saveOrganisationAdmin(managedOrganisationAdmin);

        return organisation;
    }


    //not done
    public List<Instructor> getAllInstructors(Organisation organisation) {
        return new ArrayList<>();
    }


    //not done
    public Instructor addInstructor(Organisation organisation, Instructor instructor) {
        return new Instructor();
    }




}
