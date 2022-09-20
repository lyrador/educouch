package com.educouch.educouchsystem.util.enumeration;

public enum DocumentSubmissionEnum {
    INDIVIDUAL("Individual"),
    GROUP("Group");

    private final String name;

    private DocumentSubmissionEnum(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }
}
