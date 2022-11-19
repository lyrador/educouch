package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.EditAccountDTO;
import com.educouch.educouchsystem.dto.EditAccountRequestDTO;
import com.educouch.educouchsystem.dto.LoggedInUserDTO;
import com.educouch.educouchsystem.dto.LoggedInUserRequestDTO;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.OrganisationAdmin;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;
import com.educouch.educouchsystem.util.exception.OngoingClassRunException;
import com.educouch.educouchsystem.util.logger.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private LearnerService learnerService;

    private EducatorService educatorService;

    public AccountServiceImpl(LearnerService learnerService, EducatorService educatorService) {
        this.learnerService = learnerService;
        this.educatorService = educatorService;
    }

    @Override
    public EditAccountDTO updateAccount(EditAccountRequestDTO editAccountRequestDTO) {
        String username = editAccountRequestDTO.getUsername();
        String entityType = editAccountRequestDTO.getUserType();

        EditAccountDTO updatedUser = new EditAccountDTO();

        if (entityType.equals("LEARNER")) {
            Learner retrievedLearner = learnerService.findLearnerByUsername(username);
            retrievedLearner.setProfilePictureURL(editAccountRequestDTO.getProfilePictureURL());
            retrievedLearner.setEmail(editAccountRequestDTO.getEmail());
            retrievedLearner.setPassword(editAccountRequestDTO.getPassword());
            Learner updatedLearner = learnerService.saveLearnerWithoutGallery(retrievedLearner);
            updatedUser = new EditAccountDTO(
                    updatedLearner.getLearnerId(),
                    updatedLearner.getName(),
                    null,
                    updatedLearner.getEmail(),
                    updatedLearner.getPassword(),
                    updatedLearner.getUsername(),
                    updatedLearner.getProfilePictureURL(),
                    "LEARNER",
                    "YOUNG");
        } else if (entityType.equals("INSTRUCTOR")) {
            Instructor retrievedInstructor = educatorService.findInstructorByUsername(username);
            retrievedInstructor.setProfilePictureURL(editAccountRequestDTO.getProfilePictureURL());
            retrievedInstructor.setEmail(editAccountRequestDTO.getEmail());
            retrievedInstructor.setPassword(editAccountRequestDTO.getPassword());
            Instructor updatedInstructor = educatorService.saveInstructor(retrievedInstructor);
            updatedUser = new EditAccountDTO(
                    updatedInstructor.getInstructorId(),
                    updatedInstructor.getName(),
                    updatedInstructor.getEmail(),
                    null,
                    updatedInstructor.getPassword(),
                    updatedInstructor.getUsername(),
                    updatedInstructor.getProfilePictureURL(),
                    "INSTRUCTOR",
                    "HEAD_INSTRUCTOR");
        } else if (entityType.equals("ORG_ADMIN")) {
            OrganisationAdmin retrievedOrganisationAdmin = educatorService.findOrganisationAdminByUsername(username);
            retrievedOrganisationAdmin.setProfilePictureURL(editAccountRequestDTO.getProfilePictureURL());
            retrievedOrganisationAdmin.setEmail(editAccountRequestDTO.getEmail());
            retrievedOrganisationAdmin.setPassword(editAccountRequestDTO.getPassword());
            OrganisationAdmin updatedOrganisationAdmin = educatorService.saveOrganisationAdmin(retrievedOrganisationAdmin);
            updatedUser = new EditAccountDTO(
                    updatedOrganisationAdmin.getOrganisationAdminId(),
                    updatedOrganisationAdmin.getName(),
                    updatedOrganisationAdmin.getEmail(),
                    null,
                    updatedOrganisationAdmin.getPassword(),
                    updatedOrganisationAdmin.getUsername(),
                    updatedOrganisationAdmin.getProfilePictureURL(),
                    "ORG_ADMIN",
                    null);
        }
        return updatedUser;
    }

    @Override
    public void deleteAccount(EditAccountRequestDTO editAccountRequestDTO) {
        String username = editAccountRequestDTO.getUsername();
        String entityType = editAccountRequestDTO.getUserType();

        EditAccountDTO userToDelete = new EditAccountDTO();

        try {
            if (entityType.equals("LEARNER")) {
                learnerService.deleteLearner(editAccountRequestDTO.getUserId());
            } else if (entityType.equals("INSTRUCTOR")) {
                educatorService.deleteInstructor(String.valueOf(editAccountRequestDTO.getUserId()));
            } else if (entityType.equals("ORG_ADMIN")) {
                educatorService.deleteOrganisationAdmin(editAccountRequestDTO.getUserId());
            }
        } catch (OngoingClassRunException ex) {

        } catch (InstructorNotFoundException ex) {

        }
    }
}
