package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.LearnerRepository;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import org.apache.catalina.User;
import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//to annotate that it is a service class
@Service
public class LearnerServiceImpl implements LearnerService {

    //this annotation injects the student repo into the service class
    @Autowired
    private LearnerRepository learnerRepository;

    @Override
    public Learner saveLearner(Learner learner) {
        return learnerRepository.save(learner);
    }

    @Override
    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }

    @Override
    public Learner getLearnerById(Long learnerId) {
        return learnerRepository.findById(learnerId).get();
    }

    @Override
    public Learner findLearnerByUsername(String username) throws UsernameNotFoundException {
        Learner learner = learnerRepository.findByUsername(username);
        if(learner != null) {
            return learner;
        }
        throw new UsernameNotFoundException("Username not found");
    }

    @Override
    public void deleteLearner(Long id) {
        learnerRepository.deleteById(id);
    }

    @Override
    public Learner updateLearner(Learner learner) throws InvalidLoginCredentialsException{
        Learner updateLearner = learnerRepository.findById(learner.getLearnerId()).get();
        if(updateLearner.getUsername().equals(learner.getUsername()) && updateLearner.getPassword().equals(learner.getPassword())) {
            updateLearner.setActive(learner.getActive());
//            updateLearner.setAddress(learner.getAddress());
            updateLearner.setEmail(learner.getEmail());
            updateLearner.setName(learner.getName());
            updateLearner.setProfilePictureURL(learner.getProfilePictureURL());
            learnerRepository.save(updateLearner);
            return updateLearner;
        }
        throw new InvalidLoginCredentialsException("Could not update as Lms Admin object to update has a different password");

    }
}
