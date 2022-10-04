package com.educouch.educouchsystem.scheduler;

import com.educouch.educouchsystem.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskConfig {

    @Autowired
    OrganisationService organisationService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void startOfMonthJob() {
        organisationService.dayOneOrgScheduling();
    }


    @Scheduled(cron = "0 0 0 8 * *")
    public void endOfFirstWeekJob() {
        organisationService.dayOneOrgScheduling();
    }
}
