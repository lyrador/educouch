package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Educator;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;

import java.util.List;

public interface OrganisationService {
    public Organisation saveOrganisation(Organisation organisation);
    public Organisation instantiateOrganisation(OrganisationAdmin organisationAdmin, Organisation organisation);
    public Organisation findOrganisationById(Long Id);
    public List<Instructor> getAllInstructors(Organisation organisation);
    public Instructor addInstructor(Organisation organisation, Instructor instructor);
}
