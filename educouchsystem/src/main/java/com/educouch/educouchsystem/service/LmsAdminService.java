package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;
import com.educouch.educouchsystem.util.exception.LmsAdminNotFoundException;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import com.educouch.educouchsystem.model.LmsAdmin;

import java.util.List;

public interface LmsAdminService {
    public LmsAdmin saveLmsAdmin(LmsAdmin lmsAdmin);

    public List<LmsAdmin> getAllLmsAdmins();

    public LmsAdmin findLmsAdminByUsername(String username) throws UsernameNotFoundException;

    public LmsAdmin updateLmsAdmin(LmsAdmin lmsAdmin) throws UsernameNotFoundException, InvalidLoginCredentialsException;

    public LmsAdmin findLmsAdminById(Long LmsAdminId) throws LmsAdminNotFoundException;

    public LmsAdmin login(String username, String password) throws UsernameNotFoundException, InvalidLoginCredentialsException;

    public void deleteLmsAdmin(LmsAdmin lmsAdmin);
}
