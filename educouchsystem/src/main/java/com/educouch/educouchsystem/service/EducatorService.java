package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Educator;

import java.util.List;

public interface EducatorService {
    public Educator saveEducator(Educator educator);
    public List<Educator> getAllEducators();
}
