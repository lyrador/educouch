package com.educouch.educouchsystem.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.educouch.educouchsystem.util.enumeration.RecurringEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Column(nullable = false)
    private LocalTime classRunStartTime;

    @Column(nullable = false)
    private LocalTime classRunEndTime;

    private Integer minClassSize;

    private Integer maximumCapacity;

    private Integer[] classRunDaysOfTheWeek;

    private RecurringEnum recurringEnum;
//    @Column(unique = true)
    private String classRunName;

    private String classRunDescription;

    private String color;

    @OneToOne
    @JoinColumn(name = "calendar")
    private Calendar calendar;

    @OneToMany(mappedBy = "classRun", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Event> events;

    @ManyToOne
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToMany(mappedBy = "classRuns")
    private List<Learner> enrolledLearners;

    @OneToMany(mappedBy = "classRun", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EnrolmentStatusTracker> enrolmentStatusTrackers;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "classRunId")
    private List<LearnerTransaction> learnerTransactions;

    @OneToMany(mappedBy = "classRun", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TriviaQuiz> triviaQuizzes;

    @OneToMany(mappedBy = "classRun", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Poll> polls;

    public ClassRun() {
        this.enrolmentStatusTrackers = new ArrayList<>();
        this.enrolledLearners = new ArrayList<>();
        this.learnerTransactions = new ArrayList<>();
        this.events = new ArrayList<>();
        this.triviaQuizzes = new ArrayList<>();
        this.polls = new ArrayList<>();
    }

    public ClassRun(LocalDate classRunStart, LocalDate classRunEnd, LocalTime classRunStartTime, LocalTime classRunEndTime, int minClassSize, int maximumCapacity, Integer[] daysOfWeek, RecurringEnum recurringEnum) {
        new ClassRun();
        this.classRunStart = classRunStart;
        this.classRunEnd = classRunEnd;
        this.classRunStartTime = classRunStartTime;
        this.classRunEndTime = classRunEndTime;
        this.minClassSize = minClassSize;
        this.maximumCapacity = maximumCapacity;
        this.classRunDaysOfTheWeek = daysOfWeek;
        this.recurringEnum = recurringEnum;
    }
    public ClassRun(LocalDate classRunStart, LocalDate classRunEnd) {
        new ClassRun();
        this.classRunStart = classRunStart;
        this.classRunEnd = classRunEnd;
    }

    public ClassRun(LocalDate classRunStart, LocalDate classRunEnd, Integer minClassSize, Integer maximumCapacity) {
        new ClassRun();
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

    public ClassRun(LocalDate classRunStart, LocalDate classRunEnd, Integer minClassSize, Integer maximumCapacity, Integer[] classRunDaysOfTheWeek, RecurringEnum recurringEnum, String classRunName, String classRunDescription, Calendar calendar, Instructor instructor, Course course, List<Learner> enrolledLearners) {
        this.classRunStart = classRunStart;
        this.classRunEnd = classRunEnd;
        this.minClassSize = minClassSize;
        this.maximumCapacity = maximumCapacity;
        this.classRunDaysOfTheWeek = classRunDaysOfTheWeek;
        this.recurringEnum = recurringEnum;
        this.classRunName = classRunName;
        this.classRunDescription = classRunDescription;
        this.calendar = calendar;
        this.instructor = instructor;
        this.course = course;
        this.enrolledLearners = enrolledLearners;
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

    @JsonIgnore
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

    public List<EnrolmentStatusTracker> getEnrolmentStatusTrackers() {
        return enrolmentStatusTrackers;
    }

    public void setEnrolmentStatusTrackers(List<EnrolmentStatusTracker> enrolmentStatusTrackers) {
        this.enrolmentStatusTrackers = enrolmentStatusTrackers;
    }

    public Integer[] getClassRunDaysOfTheWeek() {
        return classRunDaysOfTheWeek;
    }

    public void setClassRunDaysOfTheWeek(Integer[] classRunDaysOfTheWeek) {
        this.classRunDaysOfTheWeek = classRunDaysOfTheWeek;
    }

    public RecurringEnum getRecurringEnum() {
        return recurringEnum;
    }

    public void setRecurringEnum(RecurringEnum recurringEnum) {
        this.recurringEnum = recurringEnum;
    }

    public String getClassRunName() {
        return classRunName;
    }

    public void setClassRunName(String classRunName) {
        this.classRunName = classRunName;
    }

    public String getClassRunDescription() {
        return classRunDescription;
    }

    public void setClassRunDescription(String classRunDescription) {
        this.classRunDescription = classRunDescription;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public LocalTime getClassRunStartTime() {
        return classRunStartTime;
    }

    public void setClassRunStartTime(LocalTime classRunStartTime) {
        this.classRunStartTime = classRunStartTime;
    }

    public LocalTime getClassRunEndTime() {
        return classRunEndTime;
    }

    public void setClassRunEndTime(LocalTime classRunEndTime) {
        this.classRunEndTime = classRunEndTime;
    }

    public List<LearnerTransaction> getLearnerTransactions() {
        return learnerTransactions;
    }

    public void setLearnerTransactions(List<LearnerTransaction> learnerTransactions) {
        this.learnerTransactions = learnerTransactions;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<TriviaQuiz> getTriviaQuizzes() {
        return triviaQuizzes;
    }

    public void setTriviaQuizzes(List<TriviaQuiz> triviaQuizzes) {
        this.triviaQuizzes = triviaQuizzes;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }
}
