package com.educouch.educouchsystem.repository;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    public List<Instructor> findAll();

    @Query("SELECT i FROM Instructor i WHERE i.username=:username")
    Instructor findInstructorByUsername(String username);

    @Query("SELECT i FROM Instructor i WHERE i.organisation.organisationId=:organisationId")
    List<Instructor> findAllInstructorsInOrganisation(Long organisationId);
}
