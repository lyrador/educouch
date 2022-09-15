package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String courseCode;
    private String courseTitle;
    private String courseDescription;
    private String courseTimeline;
    private Double courseMaxScore;
    @Enumerated(EnumType.STRING)
    private AgeGroupEnum ageGroup;
    @Enumerated(EnumType.STRING)
    private CourseApprovalStatusEnum courseApprovalStatus;

    @OneToMany(mappedBy = "course")
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
