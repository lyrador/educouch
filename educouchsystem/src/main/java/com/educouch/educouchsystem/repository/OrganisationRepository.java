package com.educouch.educouchsystem.repository;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
    public List<Organisation> findAll();

    @Query("SELECT o FROM Organisation o WHERE o.organisationId=:Id")
    Organisation findOrganisationById(Long Id);

    @Query("SELECT o FROM Organisation o WHERE o.organisationName=:organisationName")
    Organisation findOrganisationByOrganisationName(String organisationName);
}


