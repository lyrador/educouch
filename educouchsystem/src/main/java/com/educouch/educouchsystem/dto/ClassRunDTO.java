package com.educouch.educouchsystem.dto;
import java.util.List;

public class ClassRunDTO {

    private Long classRunId;

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

    public ClassRunDTO() {
    }

    public ClassRunDTO(Long classRunId, String classRunStart, String classRunEnd, Integer minClassSize, Integer maximumCapacity, Integer[] classRunDaysOfTheWeek, String recurringEnumString, Long calendarId, String instructorUsername, List<String> enrolledLearnersUsernames, String classRunName, String classRunDescription) {
        this.classRunId = classRunId;
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
    }

    public Long getClassRunId() {
        return classRunId;
    }

    public void setClassRunId(Long classRunId) {
        this.classRunId = classRunId;
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
}
