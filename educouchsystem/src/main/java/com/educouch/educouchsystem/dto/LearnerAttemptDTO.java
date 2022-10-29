package com.educouch.educouchsystem.dto;

public class LearnerAttemptDTO {
    private Long learnerId;

    private String learnerName;

    private boolean didAttempt;

    private boolean isQuiz;

    private boolean isOpenEnded;

    private boolean isGraded;

    private double quizMax;

    private double learnerMcqScore;
    private double obtainedScore;


    public LearnerAttemptDTO() {
    }

    public double getLearnerMcqScore() {
        return learnerMcqScore;
    }

    public void setLearnerMcqScore(double learnerMcqScore) {
        this.learnerMcqScore = learnerMcqScore;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
    }

    public boolean isDidAttempt() {
        return didAttempt;
    }

    public void setDidAttempt(boolean didAttempt) {
        this.didAttempt = didAttempt;
    }

    public boolean isQuiz() {
        return isQuiz;
    }

    public void setQuiz(boolean quiz) {
        isQuiz = quiz;
    }

    public boolean isOpenEnded() {
        return isOpenEnded;
    }

    public void setOpenEnded(boolean openEnded) {
        isOpenEnded = openEnded;
    }

    public double getQuizMax() {
        return quizMax;
    }

    public void setQuizMax(double quizMax) {
        this.quizMax = quizMax;
    }

    public double getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScore(double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }
}
