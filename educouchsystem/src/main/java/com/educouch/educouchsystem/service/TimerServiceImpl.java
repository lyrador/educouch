package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.EnrolmentStatusTracker;
import com.educouch.educouchsystem.timer.ScheduledTask;
import com.educouch.educouchsystem.util.enumeration.EnrolmentStatusTrackerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Service("timerService")
public class TimerServiceImpl implements TimerService{

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassRunService classRunService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private EnrolmentStatusTrackerService enrolmentStatusTrackerService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Async
    @Override
    public void convertEnrolmentStatus() {
        List<Course> listOfAllCourses = courseService.getAllCourses();
        for(Course course: listOfAllCourses) {
            LocalDate startDate = course.getStartDate();
            if(startDate != null) {
                if(startDate.minusDays(7).equals(LocalDate.now())) {
                    List<ClassRun> listOfClassRuns = course.getClassRuns();

                    for(ClassRun classRun: listOfClassRuns) {
                        int minClassSize = classRun.getMinClassSize();
                        List<EnrolmentStatusTracker> statusTrackers = classRun.getEnrolmentStatusTrackers();
                        int numberOfReservations = statusTrackers.size();

                        if (numberOfReservations >= minClassSize) {
                            int capacity = classRun.getMaximumCapacity();
                            int numOfEnrolment = 0;
                            int i = 0;
                            while(numOfEnrolment < capacity) {
                                EnrolmentStatusTracker statusTracker = statusTrackers.get(i);
                                if(statusTracker.getEnrolmentStatus() == EnrolmentStatusTrackerEnum.DEPOSITPAID) {
                                    statusTracker.setEnrolmentStatus(EnrolmentStatusTrackerEnum.RESERVED);
                                    enrolmentStatusTrackerService.saveEnrolmentStatusTracker(statusTracker);
                                    String learnerEmail = statusTracker.getLearner().getEmail();
                                    emailSenderService.sendEmail(learnerEmail, "Your requested class run is available.",
                                            "Please visit EduCouch portal to pay the course fee in full to confirm your enrolment.");
                                    log.info("We are currently putting " + statusTracker.getLearner().getUsername() + " under reserved. ");
                                    numOfEnrolment = numOfEnrolment + 1;
                                }

                                i = i  + 1;
                            }



                            while( i < numberOfReservations) {
                                EnrolmentStatusTracker statusTracker = statusTrackers.get(i);
                                if(statusTracker.getEnrolmentStatus() == EnrolmentStatusTrackerEnum.DEPOSITPAID) {
                                    statusTracker.setEnrolmentStatus(EnrolmentStatusTrackerEnum.DROPPED);
                                    enrolmentStatusTrackerService.saveEnrolmentStatusTracker(statusTracker);
                                    String learnerEmail = statusTracker.getLearner().getEmail();
                                    emailSenderService.sendEmail(learnerEmail, "Your requested class run is not available.",
                                            "Please visit EduCouch portal to either choose another class run, or request for refund deposit.");
                                    log.info("We are putting " + statusTracker.getLearner().getUsername() + " under dropped status.");
                                }

                                i = i  + 1;

                            }
                        } else {
                            for(EnrolmentStatusTracker statusTracker: statusTrackers) {
                                if(statusTracker.getEnrolmentStatus() == EnrolmentStatusTrackerEnum.DEPOSITPAID) {
                                    statusTracker.setEnrolmentStatus(EnrolmentStatusTrackerEnum.DROPPED);
                                    enrolmentStatusTrackerService.saveEnrolmentStatusTracker(statusTracker);
                                    String learnerEmail = statusTracker.getLearner().getEmail();
                                    log.info("Dropping student " + statusTracker.getLearner().getUsername());
                                    emailSenderService.sendEmail(learnerEmail, "Your requested class run is not available.",
                                            "Please visit EduCouch portal to either choose another class run, or request for refund deposit.");
                                }
                            }
                        }




                    }
                }
            }
        }

        log.info("Daily task is done. ");
    }
}
