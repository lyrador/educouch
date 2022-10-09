package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrgLmsRevenueMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgLmsRevenueMapId;

    @Column(nullable = false, unique = true)
    private String orgName;

    @Column(nullable = false)
    private BigDecimal lmsRevenue;

    public OrgLmsRevenueMap() {
    }

    public OrgLmsRevenueMap(String orgName, BigDecimal lmsRevenue) {
        this.orgName = orgName;
        this.lmsRevenue = lmsRevenue;
    }

    public Long getOrgLmsRevenueMapId() {
        return orgLmsRevenueMapId;
    }

    public void setOrgLmsRevenueMapId(Long orgLmsRevenueMapId) {
        this.orgLmsRevenueMapId = orgLmsRevenueMapId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public BigDecimal getLmsRevenue() {
        return lmsRevenue;
    }

    public void setLmsRevenue(BigDecimal lmsRevenue) {
        this.lmsRevenue = lmsRevenue;
    }
}
