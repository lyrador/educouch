package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.GradeBookEntry;
import com.educouch.educouchsystem.repository.GradeBookEntryRepository;
import com.educouch.educouchsystem.util.exception.GradeBookEntryNotFoundException;

import java.util.List;

public interface GradeBookEntryService {

    public GradeBookEntry createGradeBookEntry(GradeBookEntry gradeBookEntry);

    public GradeBookEntry updateGradeBookEntry(GradeBookEntry gradeBookEntry) throws GradeBookEntryNotFoundException;

    public List<GradeBookEntry> findAllGradeBookEntries();

    public List<GradeBookEntry> findAllGradeBookEntriesByLearnerIdAndCourseId(Long learnerId, Long courseId);

    public GradeBookEntry findById(Long gradeBookEntryId) throws GradeBookEntryNotFoundException;

}
