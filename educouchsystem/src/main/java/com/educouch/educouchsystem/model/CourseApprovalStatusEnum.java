package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum CourseApprovalStatusEnum {

    PENDINGAPPROVAL("Pending Approval"),
    LIVE("Live"),
    UNDERCONSTRUCTION("Under Construction"),
    REJECTED("Rejected"),
    APPEALED("Appealed");

    private final String approvalStatus;

    CourseApprovalStatusEnum(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @JsonCreator
    public static CourseApprovalStatusEnum status(final String approvalStatus) {
        return Stream.of(CourseApprovalStatusEnum.values()). filter(targetEnum -> targetEnum.approvalStatus.equals(approvalStatus)).findFirst().orElse(null);
    }

    @JsonValue
    public String getApprovalStatus() {
        return approvalStatus;
    }
}
