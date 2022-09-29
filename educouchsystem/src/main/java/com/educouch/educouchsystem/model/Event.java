package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(name="eventName", nullable = false)
    private String eventName;

    @Column(name="eventDescription", nullable = false)
    private String eventDescription;

    @Column(name="eventStartDate", nullable = false)
    private LocalDateTime eventStartDate;

    @Column(name="eventEndDate", nullable = false)
    private LocalDateTime eventEndDate;

    public Event(String eventName, String eventDescription, LocalDateTime eventStartDate, LocalDateTime eventEndDate) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }

    public Event() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDateTime getEventStartDate() {
        return this.eventStartDate;
    }

    public void setEventStartDate(LocalDateTime eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public LocalDateTime getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(LocalDateTime eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
}
