package com.educouch.educouchsystem.repository;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface InstructorRepository extends JpaRepository<OrganisationAdmin, Integer> {

    public List<OrganisationAdmin> findAll();
}
