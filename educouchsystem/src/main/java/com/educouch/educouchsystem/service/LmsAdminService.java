package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.LmsAdmin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface LmsAdminService {
    public LmsAdmin saveLmsAdmin(LmsAdmin lmsAdmin);

    public List<LmsAdmin> getAllLmsAdmins();

    public LmsAdmin findLmsAdminByUsername(String username);

    public LmsAdmin updateLmsAdmin(LmsAdmin lmsAdmin);

    public Optional<LmsAdmin> findLmsAdminById(Long LmsAdminId);

    public LmsAdmin login(String username, String password);
}
