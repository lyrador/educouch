package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="notes", nullable = false)
    private String notes;

    @Column(name="startDate", nullable = false)
    private Date startDate;

    @Column(name="endDate", nullable = false)
    private Date endDate;

    @Column(name="allDay", nullable = false)
    private Boolean allDay;

    @ManyToOne
    @JoinColumn(name="classRun_id")
    private ClassRun classRun;

    public Event(String title, String notes, Date startDate, Date endDate, Boolean allDay) {
        this.title = title;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allDay = allDay;
    }

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date eventEndDate) {
        this.endDate = eventEndDate;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }
    @JsonIgnore
    public ClassRun getClassRun() {
        return classRun;
    }

    public void setClassRun(ClassRun classRun) {
        this.classRun = classRun;
    }
}
