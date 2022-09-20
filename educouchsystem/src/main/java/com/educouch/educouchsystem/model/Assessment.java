package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.AssessmentStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Assessment implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Double maxScore;

    @NotNull
    private Double actualScore;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date endDate;

    @NotNull
    private Boolean isOpen = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AssessmentStatusEnum assessmentStatus = AssessmentStatusEnum.PENDING;

    public Assessment() {
    }

    public Assessment(String title, String description, Double maxScore, Double actualScore, Date startDate, Date endDate) {
        this();
        this.title = title;
        this.description = description;
        this.maxScore = maxScore;
        this.actualScore = actualScore;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public Double getActualScore() {
        return actualScore;
    }

    public void setActualScore(Double actualScore) {
        this.actualScore = actualScore;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public AssessmentStatusEnum getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(AssessmentStatusEnum assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentId != null ? assessmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Assessment)) {
            return false;
        }
        Assessment other = (Assessment) object;
        if ((this.assessmentId == null && other.assessmentId != null) || (this.assessmentId != null && !this.assessmentId.equals(other.assessmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Assessment[ id=" + assessmentId + " ]";
    }
}
