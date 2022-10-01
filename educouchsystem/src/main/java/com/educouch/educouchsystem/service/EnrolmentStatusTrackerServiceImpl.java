package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.EnrolmentStatusTracker;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.repository.EnrolmentStatusTrackerRepository;
import com.educouch.educouchsystem.util.enumeration.EnrolmentStatusTrackerEnum;
import com.educouch.educouchsystem.util.exception.ClassRunNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.EnrolmentStatusTrackerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrolmentStatusTrackerServiceImpl implements EnrolmentStatusTrackerService{

    @Autowired
    EnrolmentStatusTrackerRepository enrolmentStatusTrackerRepository;

    @Autowired
    ClassRunService classRunService;

    @Override
    public EnrolmentStatusTracker findTrackerById(Long id) throws EnrolmentStatusTrackerNotFoundException{
        Optional<EnrolmentStatusTracker> tracker = enrolmentStatusTrackerRepository.findById(id);
        if(tracker.isPresent()) {
            return tracker.get();
        } else {
            throw new EnrolmentStatusTrackerNotFoundException("Unable to find tracker.");
        }
    }

    @Override
    public EnrolmentStatusTracker saveEnrolmentStatusTracker(EnrolmentStatusTracker e) {
        e = enrolmentStatusTrackerRepository.saveAndFlush(e);
        return e;
    }

    @Override
    public List<EnrolmentStatusTracker> retrieveListOfEnrolmentStatusTrackersByCourse(Long classRunId)
    throws ClassRunNotFoundException {
        ClassRun c = classRunService.retrieveClassRunById(classRunId);

        if(c != null) {
            List<EnrolmentStatusTracker> enrolmentStatusTrackers = c.getEnrolmentStatusTrackers();

            return enrolmentStatusTrackers;
        } else {
            throw new ClassRunNotFoundException("Unable to find class run. ");
        }

    }

    public String retrieveLearnerStatusWithCourse(Long courseId, Long learnerId) throws
            EnrolmentStatusTrackerNotFoundException{
        List<EnrolmentStatusTracker> listOfTrackers = enrolmentStatusTrackerRepository.retrieveEnrolmentStatusTrackerByLearnerId(learnerId);
        for(EnrolmentStatusTracker e: listOfTrackers) {
            System.out.println("Tracker ID" + e.getEnrolmentStatusTrackerId());
            ClassRun c = e.getClassRun();
            Course course = c.getCourse();
            System.out.println("Course is " + course.getCourseCode());
            if (course.getCourseId().equals(courseId)) {
                System.out.println(e.getEnrolmentStatus());
                EnrolmentStatusTrackerEnum status = e.getEnrolmentStatus();
                System.out.println("Returning status " + status.toString());
                return status.toString();
            }
        }

        throw new EnrolmentStatusTrackerNotFoundException("Unable to find status.");
    }
}
