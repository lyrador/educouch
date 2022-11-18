package com.educouch.educouchsystem.model;

import javax.persistence.*;

@Entity
public class PointsWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointsWalletId;

    @Column(nullable = false)
    private Long learnerId;

    @Column(nullable = false)
    private Long organisationId;

    @Column(nullable = false)
    private String orgName;

    @Column(nullable = false)
    private Long discountPoints;

    public PointsWallet() {
    }

    public PointsWallet(Long learnerId, Long organisationId, String orgName) {
        this.learnerId = learnerId;
        this.organisationId = organisationId;
        this.orgName = orgName;
        this.discountPoints = 0L;

    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getPointsWalletId() {
        return pointsWalletId;
    }

    public void setPointsWalletId(Long pointsWalletId) {
        this.pointsWalletId = pointsWalletId;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public Long getDiscountPoints() {
        return discountPoints;
    }

    public void setDiscountPoints(Long discountPoints) {
        this.discountPoints = discountPoints;
    }
}
