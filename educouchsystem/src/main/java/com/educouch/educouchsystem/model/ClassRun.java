package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClassRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classRunId;

    @Column(nullable = false)
    private LocalDate classRunStart;

    @Column(nullable = false)
    private LocalDate classRunEnd;

    private Integer minClassSize;

    private Integer maximumCapacity;

    @OneToOne
    @JoinColumn(name = "calendar")
    private Calendar calendar;

    @ManyToOne
    private Instructor instructor;

    @ManyToOne
    private Course course;

    @ManyToMany(mappedBy = "classRuns")
    private List<Learner> enrolledLearners;

    public ClassRun() {
        this.enrolledLearners = new ArrayList<>();
    }

    public ClassRun(LocalDate classRunStart, LocalDate classRunEnd) {
        new ClassRun();
        this.classRunStart = classRunStart;
        this.classRunEnd = classRunEnd;
    }

    public ClassRun(LocalDate classRunStart, LocalDate classRunEnd, Integer minClassSize, Integer maximumCapacity) {
        this.classRunStart = classRunStart;
        this.classRunEnd = classRunEnd;
        this.minClassSize = minClassSize;
        this.maximumCapacity = maximumCapacity;
    }

    public ClassRun(LocalDate c, LocalDate e, Instructor instructor, Calendar calendar) {
        new ClassRun(c, e);
        this.instructor = instructor;
        this.calendar = calendar;
    }

    public Long getClassRunId() {
        return classRunId;
    }

    public void setClassRunId(Long classRunId) {
        this.classRunId = classRunId;
    }

    public LocalDate getClassRunStart() {
        return classRunStart;
    }

    public void setClassRunStart(LocalDate classRunStart) {
        this.classRunStart = classRunStart;
    }

    public LocalDate getClassRunEnd() {
        return classRunEnd;
    }

    public void setClassRunEnd(LocalDate classRunEnd) {
        this.classRunEnd = classRunEnd;
    }

    public List<Learner> getEnrolledLearners() {
        return enrolledLearners;
    }

    public void setEnrolledLearners(List<Learner> enrolledLearners) {
        this.enrolledLearners = enrolledLearners;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getMinClassSize() {
        return minClassSize;
    }

    public void setMinClassSize(Integer minClassSize) {
        this.minClassSize = minClassSize;
    }

    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }
}
