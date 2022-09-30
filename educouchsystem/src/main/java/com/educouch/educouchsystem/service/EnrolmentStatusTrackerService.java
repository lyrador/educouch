package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.EnrolmentStatusTracker;
import com.educouch.educouchsystem.util.exception.ClassRunNotFoundException;
import com.educouch.educouchsystem.util.exception.EnrolmentStatusTrackerNotFoundException;

import java.util.List;

public interface EnrolmentStatusTrackerService {
    public EnrolmentStatusTracker findTrackerById(Long id) throws EnrolmentStatusTrackerNotFoundException;

    public EnrolmentStatusTracker saveEnrolmentStatusTracker(EnrolmentStatusTracker e);

    public List<EnrolmentStatusTracker> retrieveListOfEnrolmentStatusTrackersByCourse(Long classRunId)
            throws ClassRunNotFoundException;
}
