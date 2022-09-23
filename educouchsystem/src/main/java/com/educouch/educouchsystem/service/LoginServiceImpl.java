package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.LoggedInUserDTO;
import com.educouch.educouchsystem.dto.LoggedInUserRequestDTO;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import com.educouch.educouchsystem.util.logger.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private LearnerService learnerService;

    private EducatorService educatorService;

    public LoginServiceImpl(LearnerService learnerService, EducatorService educatorService) {
        this.learnerService = learnerService;
        this.educatorService = educatorService;
    }

    @Override
    public LoggedInUserDTO login(LoggedInUserRequestDTO loggedInUserRequestDTO) {
        LoggedInUserDTO retrievedUser = findUserByUsernameAndEntityType(loggedInUserRequestDTO.getUsername(), loggedInUserRequestDTO.getUserType());
        if (retrievedUser.getPassword().equals(loggedInUserRequestDTO.getPassword())) {
            return retrievedUser;
        }
        else {
            throw new InvalidLoginCredentialsException("Invalid Username or Password!");
        }
    }

    @Override
    public LoggedInUserDTO findUserByUsernameAndEntityType(String username, String entityType) {
        LoggedInUserDTO retrievedUser = new LoggedInUserDTO();
        if (entityType.equals("LEARNER")) {
            Learner retrievedLearner = learnerService.findLearnerByUsername(username);
            retrievedUser = new LoggedInUserDTO(
                    retrievedLearner.getLearnerId(),
                    retrievedLearner.getName(),
                    retrievedLearner.getAddress(),
                    retrievedLearner.getEmail(),
                    retrievedLearner.getPassword(),
                    retrievedLearner.getUsername(),
                    retrievedLearner.getProfilePictureURL(),
                    "LEARNER",
                    "YOUNG");
        } else if (entityType.equals("INSTRUCTOR")) {
            Instructor retrievedInstructor = educatorService.findInstructorByUsername(username);
            retrievedUser = new LoggedInUserDTO(
                    retrievedInstructor.getInstructorId(),
                    retrievedInstructor.getName(),
                    retrievedInstructor.getEmail(),
                    null,
                    retrievedInstructor.getPassword(),
                    retrievedInstructor.getUsername(),
                    retrievedInstructor.getProfilePictureURL(),
                    "INSTRUCTOR",
                    "HEAD_INSTRUCTOR");
        } else if (entityType.equals("ORG_ADMIN")) {
            OrganisationAdmin retrievedOrganisationAdmin = educatorService.findOrganisationAdminByUsername(username);
            retrievedUser = new LoggedInUserDTO(
                    retrievedOrganisationAdmin.getOrganisationAdminId(),
                    retrievedOrganisationAdmin.getName(),
                    retrievedOrganisationAdmin.getEmail(),
                    null,
                    retrievedOrganisationAdmin.getPassword(),
                    retrievedOrganisationAdmin.getUsername(),
                    retrievedOrganisationAdmin.getProfilePictureURL(),
                    "ORG_ADMIN",
                    null);
        }
        return retrievedUser;
    }
}
