package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.LmsAdmin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmsAdminRepository extends CrudRepository<LmsAdmin, Long> {
    public List<LmsAdmin> findAll();
    public LmsAdmin findByUsername(String username);
}
