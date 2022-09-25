package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.EducatorRepository;
import com.educouch.educouchsystem.repository.InstructorRepository;
import com.educouch.educouchsystem.repository.OrganisationAdminRepository;
import com.educouch.educouchsystem.repository.OrganisationRepository;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public Instructor findInstructorById(Long Id) {

        return instructorRepository.findById(Id).get();
    }

    //not tested
    public OrganisationAdmin findOrganisationAdminById(Long Id) {
        return organisationAdminRepository.findOrganisationAdminById(Id);
    }

    @Override
    public OrganisationAdmin findOrganisationAdminByUsername(String username) {
        OrganisationAdmin organisationAdmin = organisationAdminRepository.findByUsername(username);
        if (organisationAdmin != null) {
            return organisationAdmin;
        }
        throw new UsernameNotFoundException("Username not found");
    }

    @Override
    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }

    @Override
    public void deleteOrganisationAdmin(Long id) {
        organisationAdminRepository.deleteById(id);
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
