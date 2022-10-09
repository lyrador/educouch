package com.educouch.educouchsystem.model;

import javax.persistence.*;

public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;
//
//    @Column(nullable = false)
//    private String moduleName;
//
//    @ManyToOne
//    @JoinColumn(name="course_id")
//    private Course course;
//
//    @OneToOne
//    @JoinColumn(name="assessment_id")
//    private Assessment assessment;
//
//    @OneToOne
//    @JoinColumn(name="folder_id")
//    private Folder folder;
//
//    public Module() {
//    }
//
//    public Module(String moduleName, Course course, Assessment assessment, Folder folder) {
//        this.moduleName = moduleName;
//        this.course = course;
//        this.assessment = assessment;
//        this.folder = folder;
//    }
//
//    public void setModuleName(String moduleName) {
//        this.moduleName = moduleName;
//    }
//
//    public void setCourse(Course course) {
//        this.course = course;
//    }
//
//    public void setAssessment(Assessment assessment) {
//        this.assessment = assessment;
//    }
//
//    public void setFolder(Folder folder) {
//        this.folder = folder;
//    }
}
