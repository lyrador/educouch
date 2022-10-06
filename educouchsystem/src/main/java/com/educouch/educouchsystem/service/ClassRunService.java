package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.ClassRunDTO;
import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.Event;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.EventNotFoundException;

import java.text.ParseException;
import java.util.List;

public interface ClassRunService {
    public ClassRun saveClassRun(ClassRun classRun);

    public List<ClassRun> getAllClassRuns();

    public ClassRun retrieveClassRunById(Long id);

    public void deleteClassRun(Long id);

    public ClassRun addClassRunFromCourseId(Long courseId, ClassRunDTO classRunDTORequest) throws ParseException;

    public ClassRun updateClassRun(Long courseId, ClassRunDTO classRunDTORequest) throws ParseException;

    public List<ClassRunDTO> findClassRunsFromCourseId (Long courseId);

    public List<Event> generateClassEventsFromClassRunId(Long classRunId);

    public void deleteClassEvent(Long id) throws EventNotFoundException;

    public Event addClassEvent(Long classRunId, Event eventRequest) throws EventNotFoundException;
}
