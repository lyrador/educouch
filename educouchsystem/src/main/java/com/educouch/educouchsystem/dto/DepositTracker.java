package com.educouch.educouchsystem.dto;

public class DepositTracker {

    private String classRunId;
    private String learnerId;
    private String amount;

    public DepositTracker() {
    }

    public DepositTracker(String classRunId, String learnerId, String amount) {
        this.classRunId = classRunId;
        this.learnerId = learnerId;
        this.amount = amount;
    }

    public String getClassRunId() {
        return classRunId;
    }

    public void setClassRunId(String classRunId) {
        this.classRunId = classRunId;
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
