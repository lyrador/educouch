package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ClassRun;

import java.util.List;

public interface ClassRunService {
    public ClassRun saveClassRun(ClassRun classRun);

    public List<ClassRun> getAllClassRuns();

    public ClassRun retrieveClassRunById(Long id);

    public void deleteClassRun(Long id);
}
