package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.GradeBookEntry;
import com.educouch.educouchsystem.repository.GradeBookEntryRepository;
import com.educouch.educouchsystem.util.exception.GradeBookEntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GradeBookEntryServiceImpl implements GradeBookEntryService {

    @Autowired
    private GradeBookEntryRepository gradeBookEntryRepository;

    @Override
    public GradeBookEntry createGradeBookEntry(GradeBookEntry gradeBookEntry) {
        return gradeBookEntryRepository.save(gradeBookEntry);
    }

    @Override
    public GradeBookEntry updateGradeBookEntry(GradeBookEntry gradeBookEntry) throws GradeBookEntryNotFoundException {
        GradeBookEntry entryToUpdate = findById(gradeBookEntry.getGradeBookEntryId());
        entryToUpdate.setAssessmentMax(gradeBookEntry.getAssessmentMax());
        entryToUpdate.setAssessmentName(gradeBookEntry.getAssessmentName());
        entryToUpdate.setCourseId(gradeBookEntry.getCourseId());
        entryToUpdate.setLearnerScore(gradeBookEntry.getLearnerScore());
        gradeBookEntryRepository.save(entryToUpdate);
        return entryToUpdate;
    }

    @Override
    public List<GradeBookEntry> findAllGradeBookEntries() {
        return gradeBookEntryRepository.findAll();
    }

    @Override
    public List<GradeBookEntry> findAllGradeBookEntriesByLearnerIdAndCourseId(Long learnerId, Long courseId) {
        return gradeBookEntryRepository.findByLearnerIDAndCourseId(learnerId, courseId);
    }

    @Override
    public GradeBookEntry findById(Long gradeBookEntryId) throws GradeBookEntryNotFoundException{
        if(gradeBookEntryRepository.findById(gradeBookEntryId).isPresent()) {
            return gradeBookEntryRepository.findById(gradeBookEntryId).get();
        } else {
            throw new GradeBookEntryNotFoundException("Grade Book Entry not found!");
        }
    }
}
