package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.educouch.educouchsystem.util.enumeration.CourseApprovalStatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;

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

    @Column(name="courseDescription", nullable = false, columnDefinition = "TEXT")
    private String courseDescription;

    @Column(name="courseTimeline", nullable = false, columnDefinition = "TEXT")
    private String courseTimeline;

    @Column(name="courseMaxScore", columnDefinition = "Decimal(10,2) default '100.0'")
    private Double courseMaxScore;

    @Column(name="rejectionReason")
    private String rejectionReason;

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

    @OneToMany
    @JoinColumn(name = "id")
    private List<CategoryTag> categoryTags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assessment> assessments;

    @ManyToMany(mappedBy = "courses")
    private List<Instructor> instructors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    public Course() {
        this.categoryTags = new ArrayList<>();
        this.folders = new ArrayList<>();
        this.forums = new ArrayList<>();
        this.assessments = new ArrayList<>();
        this.courseApprovalStatus = CourseApprovalStatusEnum.UNDERCONSTRUCTION;
    }

    /*public Course(String courseCode, String courseTitle, String courseDescription, String courseTimeline,
                  Double courseMaxScore, AgeGroupEnum ageGroup, CourseApprovalStatusEnum courseApprovalStatus) {
        new Course();
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseTimeline = courseTimeline;
        this.courseMaxScore = courseMaxScore;
        this.ageGroup = ageGroup;
        this.courseApprovalStatus = courseApprovalStatus;
    }*/

    public Course(String courseCode, String courseTitle, String courseDescription, String courseTimeline, Double courseMaxScore, AgeGroupEnum ageGroup) {
        new Course();
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseTimeline = courseTimeline;
        this.courseMaxScore = courseMaxScore;
        this.ageGroup = ageGroup;
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

    @JsonIgnore
    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    @JsonIgnore
    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public List<CategoryTag> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(List<CategoryTag> categoryTags) {
        this.categoryTags = categoryTags;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }
    @JsonManagedReference
    @JsonIgnore
    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    @JsonIgnore
    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}
