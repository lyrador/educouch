package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="eventDescription", nullable = false)
    private String eventDescription;

    @Column(name="startDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name="endDate", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name="classRun_id")
    private ClassRun classRun;

    public Event(String title, String eventDescription, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime eventEndDate) {
        this.endDate = eventEndDate;
    }
    @JsonIgnore
    public ClassRun getClassRun() {
        return classRun;
    }

    public void setClassRun(ClassRun classRun) {
        this.classRun = classRun;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
