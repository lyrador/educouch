package com.educouch.educouchsystem.scheduler;

import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.service.LmsRevenueReportService;
import com.educouch.educouchsystem.service.OrgLmsRevenueMapService;
import com.educouch.educouchsystem.service.OrganisationService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class TaskConfig {

    @Autowired
    OrganisationService organisationService;

    @Autowired
    LmsRevenueReportService lmsRevenueReportService;

    @Autowired
    OrgLmsRevenueMapService orgLmsRevenueMapService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void startOfMonthJob() {

        organisationService.dayOneOrgScheduling();
        try {
            lmsRevenueReportService.createReportAtStartOfMonth();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        orgLmsRevenueMapService.DbPurgeAtStartOfMonth();



    }


    @Scheduled(cron = "0 0 0 8 * *")
    public void endOfFirstWeekJob() {
        organisationService.dayOneOrgScheduling();
    }
}
