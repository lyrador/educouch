package com.educouch.educouchsystem.dto;

public class IsEnrolled {
    private boolean isEnrolled;

    public IsEnrolled(boolean isEnrolled) {
        this.isEnrolled = isEnrolled;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
    }
}
