package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.LmsAdmin;
import com.educouch.educouchsystem.repository.LmsAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LmsAdminServiceImpl implements LmsAdminService {
    @Autowired
    private LmsAdminRepository lmsAdminRepository;
    @Override
    public LmsAdmin saveLmsAdmin(LmsAdmin lmsAdmin) {
        return lmsAdminRepository.save(lmsAdmin);
    }

    @Override
    public List<LmsAdmin> getAllLmsAdmins() {
        return lmsAdminRepository.findAll();
    }

    @Override
    public Optional<LmsAdmin> findLmsAdminById(Long lmsAdminId) {
        return lmsAdminRepository.findById(lmsAdminId);
    }

    @Override
    public LmsAdmin findLmsAdminByUsername(String username) {
        return lmsAdminRepository.findByUsername(username);
    }
    @Override
    public LmsAdmin login(String username, String password) {
        LmsAdmin retrieveUser = findLmsAdminByUsername(username);
        if (retrieveUser.getPassword().equals(password)) {
            return retrieveUser;
        }
        else {
            return null;
        }
    }

    public LmsAdmin updateLmsAdmin(LmsAdmin lmsAdmin) {
        LmsAdmin adminToUpdate = lmsAdminRepository.findByUsername(lmsAdmin.getUsername());
        if(lmsAdmin.getPassword().equals(adminToUpdate.getPassword())) {
            adminToUpdate.setEmail(lmsAdmin.getEmail());
            adminToUpdate.setName(lmsAdmin.getName());
            adminToUpdate.setProfilePicture(lmsAdmin.getProfilePicture());
            lmsAdminRepository.save(adminToUpdate);
            return adminToUpdate;
        }
        return null;
    }
}
