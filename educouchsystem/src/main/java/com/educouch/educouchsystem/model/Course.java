package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name="courseCode", unique = true, nullable = false, length = 10)
    private String courseCode;

    @Column(name="courseTitle", nullable = false)
    private String courseTitle;

    @Column(name="courseDescription", nullable = false)
    private String courseDescription;

    @Column(name="courseTimeline", nullable = false)
    private String courseTimeline;

    @Column(name="courseMaxScore", columnDefinition = "Decimal(10,2) default '100.0'")
    private Double courseMaxScore;
    @Enumerated(EnumType.STRING)
    @Column(name="ageGroup", nullable = false)
    private AgeGroupEnum ageGroup;
    @Enumerated(EnumType.STRING)
    @Column(name="courseApprovalStatus")
    private CourseApprovalStatusEnum courseApprovalStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="course_id")
    private List<Forum> forums;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Folder> folders;

    public Course() {
        this.folders = new ArrayList<>();
        this.forums = new ArrayList<>();
    }

    public Course(String courseCode, String courseTitle, String courseDescription, String courseTimeline,
                  Double courseMaxScore, AgeGroupEnum ageGroup, CourseApprovalStatusEnum courseApprovalStatus) {
        new Course();
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseTimeline = courseTimeline;
        this.courseMaxScore = courseMaxScore;
        this.ageGroup = ageGroup;
        this.courseApprovalStatus = courseApprovalStatus;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseTimeline() {
        return courseTimeline;
    }

    public void setCourseTimeline(String courseTimeline) {
        this.courseTimeline = courseTimeline;
    }

    public Double getCourseMaxScore() {
        return courseMaxScore;
    }

    public void setCourseMaxScore(Double courseMaxScore) {
        this.courseMaxScore = courseMaxScore;
    }

    public AgeGroupEnum getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroupEnum ageGroup) {
        this.ageGroup = ageGroup;
    }

    public CourseApprovalStatusEnum getCourseApprovalStatus() {
        return courseApprovalStatus;
    }

    public void setCourseApprovalStatus(CourseApprovalStatusEnum courseApprovalStatus) {
        this.courseApprovalStatus = courseApprovalStatus;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

}
