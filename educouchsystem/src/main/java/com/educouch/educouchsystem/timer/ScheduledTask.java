package com.educouch.educouchsystem.timer;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.service.ClassRunService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.EnrolmentStatusTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassRunService classRunService;

    @Autowired
    private EnrolmentStatusTrackerService enrolmentStatusTrackerService;


    // this program will run everyday at 1am
    @Scheduled(cron = "0 0 1 * * ?")
    public void checkAvailabilityDaily() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    private void checkAvailability() {
        List<Course> listOfAllCourses = courseService.getAllCourses();
        for(Course course: listOfAllCourses) {
            LocalDate startDate = course.get
        }
    }

}
