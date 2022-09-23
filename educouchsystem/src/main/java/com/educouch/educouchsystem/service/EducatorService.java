package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.OrganisationAdmin;

import java.util.List;


public interface EducatorService {
//    public Educator saveEducator(Educator educator);
//    public List<Educator> getAllEducators();
    public OrganisationAdmin saveOrganisationAdmin(OrganisationAdmin orgAdmin);
    public OrganisationAdmin findOrganisationAdminById(Long Id);
    public Instructor findInstructorByUsername(String username);
    public Instructor findInstructorById(Long Id);
    public OrganisationAdmin findOrganisationAdminByUsername(String username);
    public Instructor saveInstructor(Instructor instructor);

    public List<OrganisationAdmin> findAllOrgAdmin();

    public OrganisationAdmin updateOrganisationAdmin(OrganisationAdmin organisationAdmin);
    public void deleteOrganisationAdmin(Long id);

    public void deleteInstructor(Long id);
}
