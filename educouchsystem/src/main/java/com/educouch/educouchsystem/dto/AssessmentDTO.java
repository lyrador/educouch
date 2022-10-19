package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.util.enumeration.AssessmentStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class AssessmentDTO {


    private Long assessmentId;
    private String title;
    private String description;
    private Double maxScore;
    private String startDate;
    private String endDate;
    private String isOpen;
    private AssessmentStatusEnum assessmentStatus = AssessmentStatusEnum.PENDING;
    private String assessmentType;
    private String isExpired;

    public AssessmentDTO() {
    }

    public AssessmentDTO(Long assessmentId, String title, String description, Double maxScore, String startDate, String endDate, String isOpen, AssessmentStatusEnum assessmentStatus, String assessmentType) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.description = description;
        this.maxScore = maxScore;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOpen = isOpen;
        this.assessmentStatus = assessmentStatus;
        this.assessmentType = assessmentType;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOpen() {
        return isOpen;
    }

    public void setOpen(String open) {
        isOpen = open;
    }

    public AssessmentStatusEnum getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(AssessmentStatusEnum assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(String isExpired) {
        this.isExpired = isExpired;
    }
}
