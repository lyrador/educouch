package com.educouch.educouchsystem.timer;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.service.ClassRunService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.EnrolmentStatusTrackerService;
import com.educouch.educouchsystem.service.TimerService;
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
    private TimerService timerService;


    // this program will run everyday at 1am
    @Scheduled(cron = "0 0 1 * * ?")
    public void checkAvailabilityDaily() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        log.info("We are currently updating the enrolment status tracker.");
        timerService.convertEnrolmentStatus();
    }



}
