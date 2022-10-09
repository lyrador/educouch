package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.OrgLmsRevenueMap;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrgLmsRevenueMapRepository extends CrudRepository<OrgLmsRevenueMap, Long> {
    public List<OrgLmsRevenueMap> findAll();

    @Query("SELECT o FROM OrgLmsRevenueMap o WHERE o.orgName =:orgName")
    public OrgLmsRevenueMap findByOrgName(String orgName);
}
