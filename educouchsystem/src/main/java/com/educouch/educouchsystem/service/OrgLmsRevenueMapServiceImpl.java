package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import com.educouch.educouchsystem.repository.OrgLmsRevenueMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class OrgLmsRevenueMapServiceImpl implements OrgLmsRevenueMapService{

    @Autowired
    OrgLmsRevenueMapRepository orgLmsRevenueMapRepository;

    @Override
    public OrgLmsRevenueMap addRevenue(OrgLmsRevenueMap orgLmsRevenueMap) {
        String orgName = orgLmsRevenueMap.getOrgName();
        BigDecimal revenue = orgLmsRevenueMap.getLmsRevenue();
        OrgLmsRevenueMap obj = findByOrgName(orgName);
        if(obj != null) {
            obj.setLmsRevenue(obj.getLmsRevenue().add(revenue));
            orgLmsRevenueMapRepository.save(obj);
            return obj;
        } else {
            obj = new OrgLmsRevenueMap(orgName,revenue);
            orgLmsRevenueMapRepository.save(obj);
            return obj;
        }
    }

    @Override
    public List<OrgLmsRevenueMap> findAllOrgLmsRevenueMap() {
        return orgLmsRevenueMapRepository.findAll();
    }

    @Override
    public OrgLmsRevenueMap findByOrgName(String orgName) {
        return orgLmsRevenueMapRepository.findByOrgName(orgName);
    }

    @Override
    public void DbPurgeAtStartOfMonth() {
        orgLmsRevenueMapRepository.deleteAll();
    }
}
