package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Educator;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;

import java.util.List;

public interface EducatorService {
//    public Educator saveEducator(Educator educator);
//    public List<Educator> getAllEducators();
    public OrganisationAdmin saveOrganisationAdmin(OrganisationAdmin orgAdmin);
    public Organisation saveOrganisation(Organisation organisation);
    public Instructor addInstructor(Organisation organisation, Instructor instructor);
    public List<Instructor> getAllInstructors(Organisation organisation);
    public Instructor findInstructorById(Long Id);
}
