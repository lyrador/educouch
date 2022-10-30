package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import com.educouch.educouchsystem.util.exception.InvalidLoginCredentialsException;

import java.util.List;

public interface LearnerService {
    public Learner saveLearner(Learner learner);
    public List<Learner> getAllLearners();

    public Learner getLearnerById(Long learnerId);

    public Learner findLearnerByUsername(String username) throws UsernameNotFoundException;

    public void deleteLearner(Long id);

    public Learner updateLearner(Learner learner) throws InvalidLoginCredentialsException;

    public Learner findLearnerByUsernameNonException(String username);

}
