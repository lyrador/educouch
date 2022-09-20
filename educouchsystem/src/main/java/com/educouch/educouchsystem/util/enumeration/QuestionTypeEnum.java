package com.educouch.educouchsystem.util.enumeration;

public enum QuestionTypeEnum {

    TRUE_FALSE("TrueFalse"),
    MCQ("MultipleChoice"),
    MRQ("MultipleResponse"),
    OPEN_ENDED("OpenEnded");

    private final String name;

    private QuestionTypeEnum(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }

}
