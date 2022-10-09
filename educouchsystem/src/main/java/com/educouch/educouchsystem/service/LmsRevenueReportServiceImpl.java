package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.LmsAdmin;
import com.educouch.educouchsystem.model.LmsRevenueReport;
import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.repository.LmsRevenueReportRepository;
import com.educouch.educouchsystem.repository.OrgLmsRevenueMapRepository;
import com.educouch.educouchsystem.s3.service.StorageService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LmsRevenueReportServiceImpl implements LmsRevenueReportService{

    @Autowired
    LmsRevenueReportRepository lmsRevenueReportRepository;

    @Autowired
    OrgLmsRevenueMapRepository orgLmsRevenueMapRepository;

    @Autowired
    StorageService storageService;

    @Override
    public LmsRevenueReport createReport(LmsRevenueReport lmsRevenueReport) {
        return lmsRevenueReportRepository.save(lmsRevenueReport);
    }
@Override
    public LmsRevenueReport createReportAtStartOfMonth() throws FileNotFoundException, JRException {
        List<OrgLmsRevenueMap> list = orgLmsRevenueMapRepository.findAll();
        //Load file and compile
        File file = ResourceUtils.getFile("classpath:LmsReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        Map<String,Object> params = new HashMap<>();
        params.put("createdBy", "Lms");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params, dataSource);
        byte[] arr = JasperExportManager.exportReportToPdf(jasperPrint);
        LocalDate monthYear = LocalDate.now().minusMonths(1);
        String fileName = monthYear.getMonth().toString() + "_"+ monthYear.getYear();
        String[] strArr = storageService.uploadFile(arr,fileName);

        LmsRevenueReport newReport = new LmsRevenueReport(strArr[1],strArr[0],monthYear);
        return lmsRevenueReportRepository.save(newReport);
    }

    @Override
    public List<LmsRevenueReport> findAllLmsRevenueReport() {
        return lmsRevenueReportRepository.findAll();
    }
}
