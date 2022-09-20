package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;
import com.educouch.educouchsystem.util.exception.LmsAdminNotFoundException;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import com.educouch.educouchsystem.model.LmsAdmin;
import com.educouch.educouchsystem.repository.LmsAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public LmsAdmin findLmsAdminById(Long lmsAdminId) throws LmsAdminNotFoundException {
        if(lmsAdminRepository.findById(lmsAdminId).isPresent()) {
            return lmsAdminRepository.findById(lmsAdminId).get();
        }
        throw new LmsAdminNotFoundException("Lms admin with this ID does not exist!");
    }

    @Override
    public LmsAdmin findLmsAdminByUsername(String username) throws UsernameNotFoundException {
        LmsAdmin lmsAdmin = lmsAdminRepository.findByUsername(username);
        if(lmsAdmin != null) {
            return lmsAdmin;
        }
        throw new UsernameNotFoundException("Username not found!");
    }
    @Override
    public LmsAdmin login(String username, String password) throws UsernameNotFoundException, InvalidLoginCredentialsException {
        LmsAdmin retrieveUser = findLmsAdminByUsername(username);
        if (retrieveUser.getPassword().equals(password)) {
            return retrieveUser;
        }
        else {
            throw new InvalidLoginCredentialsException("Invalid Username or Password!");
        }
    }

    @Override
    public void deleteLmsAdmin(LmsAdmin lmsAdmin) throws UsernameNotFoundException, InvalidLoginCredentialsException{
        LmsAdmin adminToDelete = lmsAdminRepository.findByUsername(lmsAdmin.getUsername());
        if(lmsAdmin.getPassword().equals(adminToDelete.getPassword())) {
            lmsAdminRepository.delete(adminToDelete);
        } else {
            throw new InvalidLoginCredentialsException("Could not update as Lms Admin object to update has a different password");
        }
    }

    @Override
    public LmsAdmin updateLmsAdmin(LmsAdmin lmsAdmin) throws UsernameNotFoundException, InvalidLoginCredentialsException{
        LmsAdmin adminToUpdate = lmsAdminRepository.findByUsername(lmsAdmin.getUsername());
        if(lmsAdmin.getPassword().equals(adminToUpdate.getPassword())) {
            adminToUpdate.setEmail(lmsAdmin.getEmail());
            adminToUpdate.setName(lmsAdmin.getName());
            adminToUpdate.setProfilePicture(lmsAdmin.getProfilePicture());
            lmsAdminRepository.save(adminToUpdate);
            return adminToUpdate;
        }
        throw new InvalidLoginCredentialsException("Could not update as Lms Admin object to update has a different password");
    }
}
