package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.EducatorRepository;
import com.educouch.educouchsystem.repository.InstructorRepository;
import com.educouch.educouchsystem.repository.OrganisationAdminRepository;
import com.educouch.educouchsystem.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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


    //not done
    public Instructor findInstructorById(Long Id) {
        return new Instructor();
    }

    //not tested done
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
