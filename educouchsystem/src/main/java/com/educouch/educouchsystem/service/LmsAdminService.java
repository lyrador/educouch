package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.exception.InvalidLoginCredentialsException;
import com.educouch.educouchsystem.exception.LmsAdminNotFoundException;
import com.educouch.educouchsystem.exception.UsernameNotFoundException;
import com.educouch.educouchsystem.model.LmsAdmin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface LmsAdminService {
    public LmsAdmin saveLmsAdmin(LmsAdmin lmsAdmin);

    public List<LmsAdmin> getAllLmsAdmins();

    public LmsAdmin findLmsAdminByUsername(String username) throws UsernameNotFoundException;

    public LmsAdmin updateLmsAdmin(LmsAdmin lmsAdmin) throws UsernameNotFoundException, InvalidLoginCredentialsException;

    public LmsAdmin findLmsAdminById(Long LmsAdminId) throws LmsAdminNotFoundException;

    public LmsAdmin login(String username, String password) throws UsernameNotFoundException, InvalidLoginCredentialsException;
}
