package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import com.educouch.educouchsystem.util.exception.InvalidInstructorAccessRight;
import com.educouch.educouchsystem.util.exception.OngoingClassRunException;

import java.util.List;


public interface EducatorService {
//    public Educator saveEducator(Educator educator);
//    public List<Educator> getAllEducators();
    public OrganisationAdmin saveOrganisationAdmin(OrganisationAdmin orgAdmin);
    public OrganisationAdmin findOrganisationAdminById(Long Id);
    public Instructor findInstructorByUsername(String username);
    public Instructor findInstructorById(Long Id) throws InstructorNotFoundException;
    public Instructor updateInstructor(Instructor updatedInstructor) throws InstructorNotFoundException;
    public Instructor updateInstructorAccessRight(String instructorId, String accessRight) throws InstructorNotFoundException, InvalidInstructorAccessRight;
    public OrganisationAdmin findOrganisationAdminByUsername(String username);
    public Instructor saveInstructor(Instructor instructor);

    public List<OrganisationAdmin> findAllOrgAdmin();

    public OrganisationAdmin updateOrganisationAdmin(OrganisationAdmin organisationAdmin);
    public void deleteOrganisationAdmin(Long id);

    public Long deleteInstructor(String instructorId) throws InstructorNotFoundException, OngoingClassRunException;

    public OrganisationAdmin findOrganisationAdminByUsernameNonException(String username);
}
