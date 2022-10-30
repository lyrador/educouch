package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.InstructorRepository;
import com.educouch.educouchsystem.repository.OrganisationAdminRepository;
import com.educouch.educouchsystem.repository.OrganisationRepository;
import com.educouch.educouchsystem.util.enumeration.InstructorAccessRight;
import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import com.educouch.educouchsystem.util.exception.InvalidInstructorAccessRight;
import com.educouch.educouchsystem.util.exception.OngoingClassRunException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

//educator service handles instructors, organisation admin
@Service
public class EducatorServiceImpl implements EducatorService{

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
    public Instructor findInstructorById(Long Id) throws InstructorNotFoundException {
        try {
            Instructor instructor = instructorRepository.findInstructorById(Id);
            if (instructor==null) {
                throw new InstructorNotFoundException("No Instructor with id: " + Id + " exists!");
            } else {
                return instructor;
            }
        } catch (NoResultException exception) {
            throw new InstructorNotFoundException("No Instructor with id: " + Id + " exists!");
        }
    }

    public Instructor updateInstructor(Instructor updatedInstructor) throws InstructorNotFoundException {

        //can update everything except InstructorId, password, organisation
        try {
            Instructor managedInstructor = findInstructorById(updatedInstructor.getInstructorId());
            managedInstructor.setEmail(updatedInstructor.getEmail());
            managedInstructor.setName(updatedInstructor.getName());
            managedInstructor.setProfilePictureURL(updatedInstructor.getProfilePictureURL());
            managedInstructor.setUsername(updatedInstructor.getUsername());
            managedInstructor.setInstructorAccessRight(updatedInstructor.getInstructorAccessRight());
            instructorRepository.save(managedInstructor);
            return managedInstructor;
        } catch (InstructorNotFoundException exception) {
            throw new InstructorNotFoundException("No Instructor with id: " + updatedInstructor.getInstructorId() + " exists!");
        }
    }


    public Instructor updateInstructorAccessRight(String instructorId, String accessRight) throws InstructorNotFoundException, InvalidInstructorAccessRight {

        try {
            Instructor managedInstructor = findInstructorById(Long.valueOf(instructorId));
            if (accessRight.equals("INSTRUCTOR")) {
                managedInstructor.setInstructorAccessRight(InstructorAccessRight.INSTRUCTOR);
            } else if (accessRight.equals("HEADINSTRUCTOR")){
                managedInstructor.setInstructorAccessRight(InstructorAccessRight.HEADINSTRUCTOR);
            } else {
                throw new InvalidInstructorAccessRight();
            }
            instructorRepository.save(managedInstructor);
            return managedInstructor;
        } catch (InstructorNotFoundException exception) {
            throw new InstructorNotFoundException("Instructor with id: " + instructorId + " does not exists!");
        }
    }

    //not complete, validations required
    public Long deleteInstructor(String instructorId) throws InstructorNotFoundException, OngoingClassRunException {

        try {
            Instructor instructorToDelete = findInstructorById(Long.valueOf(instructorId));
            //check if instructor exists in any courses
            if(false) {
                throw new OngoingClassRunException();
            } else {
                instructorRepository.delete(instructorToDelete);
                return Long.valueOf(instructorId);
            }
        } catch (InstructorNotFoundException exception) {
            throw new InstructorNotFoundException("Instructor with id: " + instructorId + " does not exists!");
        }
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

    @Override
    public OrganisationAdmin findOrganisationAdminByUsername(String username) {
        OrganisationAdmin organisationAdmin = organisationAdminRepository.findByUsername(username);
        if (organisationAdmin != null) {
            return organisationAdmin;
        }
        throw new UsernameNotFoundException("Username not found");
    }

    @Override
    public OrganisationAdmin findOrganisationAdminByUsernameNonException(String username) {
        OrganisationAdmin organisationAdmin = organisationAdminRepository.findByUsername(username);
        return organisationAdmin;
    }

    @Override
    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
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
