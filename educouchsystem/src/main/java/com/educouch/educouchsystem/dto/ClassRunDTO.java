package com.educouch.educouchsystem.dto;
import com.educouch.educouchsystem.model.Event;

import java.util.List;

public class ClassRunDTO {

    private Long id;

    private String classRunStart;

    private String classRunEnd;

    private Integer minClassSize;

    private Integer maximumCapacity;

    private Integer[] classRunDaysOfTheWeek;

    private String recurringEnumString;

    private Long calendarId;

    private String instructorUsername;

    private List<String> enrolledLearnersUsernames;

    private String classRunName;
    private String classRunDescription;

    private String classRunStartTime;

    private String classRunEndTime;

    private List<Event> classEvents;

    private String text;

    private String color;

    public ClassRunDTO() {
    }

    public ClassRunDTO(Long id, String classRunStart, String classRunEnd, Integer minClassSize, Integer maximumCapacity, Integer[] classRunDaysOfTheWeek, String recurringEnumString, Long calendarId, String instructorUsername, List<String> enrolledLearnersUsernames, String classRunName, String classRunDescription, String classRunStartTime, String classRunEndTime) {
        this.id = id;
        this.classRunStart = classRunStart;
        this.classRunEnd = classRunEnd;
        this.minClassSize = minClassSize;
        this.maximumCapacity = maximumCapacity;
        this.classRunDaysOfTheWeek = classRunDaysOfTheWeek;
        this.recurringEnumString = recurringEnumString;
        this.calendarId = calendarId;
        this.instructorUsername = instructorUsername;
        this.enrolledLearnersUsernames = enrolledLearnersUsernames;
        this.classRunName = classRunName;
        this.classRunDescription = classRunDescription;
        this.classRunStartTime = classRunStartTime;
        this.classRunEndTime = classRunEndTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassRunStart() {
        return classRunStart;
    }

    public void setClassRunStart(String classRunStart) {
        this.classRunStart = classRunStart;
    }

    public String getClassRunEnd() {
        return classRunEnd;
    }

    public void setClassRunEnd(String classRunEnd) {
        this.classRunEnd = classRunEnd;
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

    public Integer[] getClassRunDaysOfTheWeek() {
        return classRunDaysOfTheWeek;
    }

    public void setClassRunDaysOfTheWeek(Integer[] classRunDaysOfTheWeek) {
        this.classRunDaysOfTheWeek = classRunDaysOfTheWeek;
    }

    public String getRecurringEnumString() {
        return recurringEnumString;
    }

    public void setRecurringEnumString(String recurringEnumString) {
        this.recurringEnumString = recurringEnumString;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public String getInstructorUsername() {
        return instructorUsername;
    }

    public void setInstructorUsername(String instructorUsername) {
        this.instructorUsername = instructorUsername;
    }

    public List<String> getEnrolledLearnersUsernames() {
        return enrolledLearnersUsernames;
    }

    public void setEnrolledLearnersUsernames(List<String> enrolledLearnersUsernames) {
        this.enrolledLearnersUsernames = enrolledLearnersUsernames;
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

    public String getClassRunStartTime() {
        return classRunStartTime;
    }

    public void setClassRunStartTime(String classRunStartTime) {
        this.classRunStartTime = classRunStartTime;
    }

    public String getClassRunEndTime() {
        return classRunEndTime;
    }

    public void setClassRunEndTime(String classRunEndTime) {
        this.classRunEndTime = classRunEndTime;
    }

    public List<Event> getClassEvents() {
        return classEvents;
    }

    public void setClassEvents(List<Event> classEvents) {
        this.classEvents = classEvents;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
