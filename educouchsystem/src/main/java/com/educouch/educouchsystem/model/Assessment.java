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

    private Double discountPointForAssessment;

    private Integer discountPointToTopPercent;

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

    @NotNull
    private boolean isPublished;


//
//    @ManyToOne(optional = false)
//    @JoinColumn(nullable = false)
//    private Course assessmentCourse;
//
//

    public Assessment() {
        isPublished=false;
    }

    public Assessment(String title, String description, Double maxScore, Date startDate, Date endDate, Double discountPointForAssessment, Integer discountPointToTopPercent) {
        this();
        this.title = title;
        this.description = description;
        this.maxScore = maxScore;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPointForAssessment = discountPointForAssessment;
        this.discountPointToTopPercent = discountPointToTopPercent;
    }

    public Double getDiscountPointForAssessment() {
        return discountPointForAssessment;
    }

    public void setDiscountPointForAssessment(Double discountPointForAssessment) {
        this.discountPointForAssessment = discountPointForAssessment;
    }

    public Integer getDiscountPointToTopPercent() {
        return discountPointToTopPercent;
    }

    public void setDiscountPointToTopPercent(Integer discountPointToTopPercent) {
        this.discountPointToTopPercent = discountPointToTopPercent;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
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
//
//    public Course getAssessmentCourse() {
//        return assessmentCourse;
//    }
//
//    public void setAssessmentCourse(Course assessmentCourse) {
//        this.assessmentCourse = assessmentCourse;
//    }

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
