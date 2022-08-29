package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Learner;

import java.util.List;

public interface LearnerService {
    public Learner saveLearner(Learner learner);
    public List<Learner> getAllLearners();
}
