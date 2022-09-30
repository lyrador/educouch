package com.educouch.educouchsystem.util.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum RecurringEnum {

    NONRECURRING("NONRECURRING"),
    ALTERNATE("ALTERNATE"),
    WEEKLY("WEEKLY"),
    CUSTOM("CUSTOM");

    private final String group;

    RecurringEnum(String group) {
        this.group = group;
    }

    @JsonCreator
    public static RecurringEnum ageGroup(final String group) {
        return Stream.of(RecurringEnum.values()). filter(targetEnum -> targetEnum.group.equals(group)).findFirst().orElse(null);
    }

    @JsonValue
    public String getGroup() {
        return group;
    }
}
