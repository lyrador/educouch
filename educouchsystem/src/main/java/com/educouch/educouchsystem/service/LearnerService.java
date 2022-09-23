package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;

import java.util.List;

public interface LearnerService {
    public Learner saveLearner(Learner learner);
    public List<Learner> getAllLearners();

    public Learner updateLearner(Learner learner) throws InvalidLoginCredentialsException;
}
