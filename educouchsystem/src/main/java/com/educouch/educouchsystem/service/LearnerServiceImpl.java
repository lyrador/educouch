package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.LearnerRepository;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
