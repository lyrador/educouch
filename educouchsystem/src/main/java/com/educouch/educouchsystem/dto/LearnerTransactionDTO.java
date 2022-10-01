package com.educouch.educouchsystem.dto;

public class LearnerTransactionDTO {

    private String learnerId;
    private String classRunId;
    private String amount;

    public LearnerTransactionDTO(String learnerId, String classRunId, String amount) {
        this.learnerId = learnerId;
        this.classRunId = classRunId;
        this.amount = amount;
    }

    public LearnerTransactionDTO() {
    }

    public String getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(String learnerId) {
        this.learnerId = learnerId;
    }

    public String getClassRunId() {
        return classRunId;
    }

    public void setClassRunId(String classRunId) {
        this.classRunId = classRunId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
