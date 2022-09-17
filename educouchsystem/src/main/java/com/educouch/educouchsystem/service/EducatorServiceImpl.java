package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Educator;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.repository.EducatorRepository;
import com.educouch.educouchsystem.repository.InstructorRepository;
import com.educouch.educouchsystem.repository.OrganisationAdminRepository;
import com.educouch.educouchsystem.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Organisation saveOrganisation(Organisation organisation) {

        return organisationRepository.save(organisation);
    }

    public List<Instructor> getAllInstructors(Organisation organisation) {
        return new ArrayList<>();
    }

    public Instructor addInstructor(Organisation organisation, Instructor instructor) {
        return new Instructor();
    }

    public Instructor findInstructorById(Long Id) {
        return new Instructor();
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
