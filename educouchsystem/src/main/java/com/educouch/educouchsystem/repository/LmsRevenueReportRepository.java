package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.LmsRevenueReport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LmsRevenueReportRepository extends CrudRepository<LmsRevenueReport, Long> {

    public List<LmsRevenueReport> findAll();
}
