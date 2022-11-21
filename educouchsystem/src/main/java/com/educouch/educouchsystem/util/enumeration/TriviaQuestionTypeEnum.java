package com.educouch.educouchsystem.util.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum TriviaQuestionTypeEnum {

    FOURS("Four Options"),
    TRUEFALSE("True or False");

    private final String group;

    TriviaQuestionTypeEnum(String group) {
        this.group = group;
    }

    @JsonCreator
    public static TriviaQuestionTypeEnum ageGroup(final String group) {
        return Stream.of(TriviaQuestionTypeEnum.values()). filter(targetEnum -> targetEnum.group.equals(group)).findFirst().orElse(null);
    }

    @JsonValue
    public String getGroup() {
        return group;
    }
}
