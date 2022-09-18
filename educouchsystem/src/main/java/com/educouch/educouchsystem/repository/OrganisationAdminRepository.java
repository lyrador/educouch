package com.educouch.educouchsystem.repository;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationAdminRepository extends JpaRepository<OrganisationAdmin, Integer>{
    public List<OrganisationAdmin> findAll();

    @Query("SELECT o FROM OrganisationAdmin o WHERE o.organisationAdminId=:Id")
    OrganisationAdmin findOrganisationAdminById(Long Id);
}
