package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class CourseStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseStatsId;

    @Column(nullable = false)
    private Long courseId;
    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private BigDecimal courseFee;

    @Column(nullable = false)
    private Integer learnerEnrolled;

    @Column(nullable = false)
    private BigDecimal totalRevenue;

    @Column(nullable = false)
    private BigDecimal lmsCut;

    @Column(nullable = false)
    private BigDecimal netRevenue;


    public CourseStats() {
        this.learnerEnrolled = 0;
        this.totalRevenue = new BigDecimal(0);
        this.lmsCut = new BigDecimal(0);
        this.netRevenue = new BigDecimal(0);

    }

    public CourseStats(Long courseId, String courseName, BigDecimal courseFee) {
        this();
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseFee = courseFee;
    }

    public Long getCourseStatsId() {
        return courseStatsId;
    }

    public void setCourseStatsId(Long courseStatsId) {
        this.courseStatsId = courseStatsId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(BigDecimal courseFee) {
        this.courseFee = courseFee;
    }

    public Integer getLearnerEnrolled() {
        return learnerEnrolled;
    }

    public void setLearnerEnrolled(Integer learnerEnrolled) {
        this.learnerEnrolled = learnerEnrolled;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getLmsCut() {
        return lmsCut;
    }

    public void setLmsCut(BigDecimal lmsCut) {
        this.lmsCut = lmsCut;
    }

    public BigDecimal getNetRevenue() {
        return netRevenue;
    }

    public void setNetRevenue(BigDecimal netRevenue) {
        this.netRevenue = netRevenue;
    }
}
