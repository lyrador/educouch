package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.LearnerRepository;
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
}
