package com.educouch.educouchsystem.util.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum AgeGroupEnum {

    KIDS("Kids"),
    ADULTS("Adults");

    private final String group;

    AgeGroupEnum(String group) {
        this.group = group;
    }

    @JsonCreator
    public static AgeGroupEnum ageGroup(final String group) {
        return Stream.of(AgeGroupEnum.values()). filter(targetEnum -> targetEnum.group.equals(group)).findFirst().orElse(null);
    }

    @JsonValue
    public String getGroup() {
        return group;
    }
}
