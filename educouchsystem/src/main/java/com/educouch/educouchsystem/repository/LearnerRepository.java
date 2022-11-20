package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearnerRepository extends CrudRepository<Learner, Long> {

    public List<Learner> findAll();
    public Learner findByUsername(String username);


    
}
