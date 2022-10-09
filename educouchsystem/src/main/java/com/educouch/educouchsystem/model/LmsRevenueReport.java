package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class LmsRevenueReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lmsRevenueReportId;

    @Column(nullable = false)
    private String reportUrl;

    @Column(nullable = false)
    private String fileStorageName;

    @Column(nullable = false, unique = true)
    private String monthYear;

    public LmsRevenueReport() {
    }

    public LmsRevenueReport(String reportUrl, String fileStorageName, String monthYear) {
        this.reportUrl = reportUrl;
        this.monthYear = monthYear;
        this.fileStorageName = fileStorageName;
    }

    public String getFileStorageName() {
        return fileStorageName;
    }

    public void setFileStorageName(String fileStorageName) {
        this.fileStorageName = fileStorageName;
    }

    public Long getLmsRevenueReportId() {
        return lmsRevenueReportId;
    }

    public void setLmsRevenueReportId(Long lmsRevenueReportId) {
        this.lmsRevenueReportId = lmsRevenueReportId;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
}
