package com.educouch.educouchsystem.service;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;

import java.util.List;

public interface OrganisationService {
    public Organisation saveOrganisation(Organisation organisation);
    public Organisation instantiateOrganisation(OrganisationAdmin organisationAdmin, Organisation organisation);
    public Organisation findOrganisationById(Long Id);
    public List<Organisation> findAllOrganisation();
    public Organisation findOrganisationByOrganisationName(String organisationName);
    public Instructor addInstructor(String organisationId, Instructor instructor);
    public List<Instructor> findAllInstructors(String organisationId);

    public void dayOneOrgScheduling();

    public void daySevenOrgScheduling();

    public List<Organisation> findAllDue();


}
