package com.educouch.educouchsystem.scheduler;


import com.educouch.educouchsystem.service.*;
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

    @Autowired
    DepositRefundRequestService depositRefundRequestService;

    @Autowired
    TransactionService transactionService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void startOfMonthJob() {

//        organisationService.dayOneOrgScheduling();
        try {
            lmsRevenueReportService.createReportAtStartOfMonth();
            transactionService.dayOneTransactionGeneration();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        orgLmsRevenueMapService.DbPurgeAtStartOfMonth();
        organisationService.courseStatsRefresh();


    }

    @Scheduled(cron = "0 0 0 * * *")
    public void everyDayJob() {
        depositRefundRequestService.checkRefundDueDate(); 
    }



//    @Scheduled(cron = "0 0 0 8 * *")
//    public void endOfFirstWeekJob() {
//        organisationService.daySevenOrgScheduling();
//    }
}
