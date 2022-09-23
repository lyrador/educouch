package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.EducatorRepository;
import com.educouch.educouchsystem.repository.InstructorRepository;
import com.educouch.educouchsystem.repository.OrganisationAdminRepository;
import com.educouch.educouchsystem.repository.OrganisationRepository;
import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//educator service handles instructors, organisation admin
@Service
public class EducatorServiceImpl implements EducatorService{

    @Autowired
    private EducatorRepository educatorRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private OrganisationAdminRepository organisationAdminRepository;
    @Autowired
    private InstructorRepository instructorRepository;


    public OrganisationAdmin saveOrganisationAdmin(OrganisationAdmin orgAdmin) {

        return organisationAdminRepository.save(orgAdmin);
    }

    public Instructor findInstructorByUsername(String username) {
        return instructorRepository.findInstructorByUsername(username);
    }

    //not done
    public Instructor findInstructorById(Long Id) {
        return new Instructor();
    }

    @Override
    public List<OrganisationAdmin> findAllOrgAdmin() {
        List<OrganisationAdmin> list = organisationAdminRepository.findAll();
        for(OrganisationAdmin admin : list) {
            admin.setOrganisation(null);
        }
        return list;
    }

    @Override
    public OrganisationAdmin updateOrganisationAdmin(OrganisationAdmin organisationAdmin) {
        OrganisationAdmin adminToUpdate = organisationAdminRepository.findOrganisationAdminById(organisationAdmin.getOrganisationAdminId());
        if(adminToUpdate.getUsername().equals(organisationAdmin.getUsername()) && adminToUpdate.getPassword().equals(organisationAdmin.getPassword())) {
            adminToUpdate.setActive(organisationAdmin.getActive());
            adminToUpdate.setOrganisation(organisationAdmin.getOrganisation());
            adminToUpdate.setName(organisationAdmin.getName());
            adminToUpdate.setEmail(organisationAdmin.getEmail());
            adminToUpdate.setProfilePictureURL(organisationAdmin.getProfilePictureURL());
            organisationAdminRepository.save(adminToUpdate);
            return adminToUpdate;
        }
        throw new InvalidLoginCredentialsException("Could not update as Organisation Admin object to update has a different password");
    }

    //not tested
    public OrganisationAdmin findOrganisationAdminById(Long Id) {
        return organisationAdminRepository.findOrganisationAdminById(Id);
    }

//    @Override
//    public Educator saveEducator(Educator educator) {
//
//        return educatorRepository.save(educator);
//    }
        //
//    public List<Educator> getAllEducators() {
//        return educatorRepository.findAll();
//    }
//
}
