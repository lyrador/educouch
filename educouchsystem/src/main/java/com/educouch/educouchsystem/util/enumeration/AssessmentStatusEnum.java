package com.educouch.educouchsystem.util.enumeration;

public enum AssessmentStatusEnum {
    PENDING("Pending"),
    INCOMPLETE("Incomplete"),
    COMPLETE("Complete");

    private final String name;

    private AssessmentStatusEnum(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }

}
