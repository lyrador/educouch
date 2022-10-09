package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.LmsRevenueReport;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface LmsRevenueReportService {
    public LmsRevenueReport createReport(LmsRevenueReport lmsRevenueReport);

    public LmsRevenueReport createReportAtStartOfMonth() throws FileNotFoundException, JRException;


        public List<LmsRevenueReport> findAllLmsRevenueReport();
}
