package com.educouch.educouchsystem.dto;

public class ChangeScheduleCourseEnrolment {
    private String currClassRunId;
    private String newClassRunId;
    private String learnerId;
    private String amount;

    public ChangeScheduleCourseEnrolment(String currClassRunId, String newClassRunId, String learnerId, String amount) {
        this.currClassRunId = currClassRunId;
        this.newClassRunId = newClassRunId;
        this.learnerId = learnerId;
        this.amount = amount;
    }

    public String getCurrClassRunId() {
        return currClassRunId;
    }

    public void setCurrClassRunId(String currClassRunId) {
        this.currClassRunId = currClassRunId;
    }

    public String getNewClassRunId() {
        return newClassRunId;
    }

    public void setNewClassRunId(String newClassRunId) {
        this.newClassRunId = newClassRunId;
    }

    public String getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(String learnerId) {
        this.learnerId = learnerId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
