package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OrgLmsRevenueMap;

import java.math.BigDecimal;
import java.util.List;

public interface OrgLmsRevenueMapService {

    public OrgLmsRevenueMap addRevenue(OrgLmsRevenueMap orgLmsRevenueMap);

    public List<OrgLmsRevenueMap> findAllOrgLmsRevenueMap();

    public OrgLmsRevenueMap findByOrgName(String orgName);

    public void DbPurgeAtStartOfMonth();
}
