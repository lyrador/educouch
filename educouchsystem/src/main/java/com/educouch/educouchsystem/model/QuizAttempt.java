package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.AssessmentAttemptStatusEnum;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class QuizAttempt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizAttemptId;

    @NotNull
    private Integer attemptCounter;

    @NotNull
    private Double learnerMcqScore;
    @NotNull
    private Double obtainedScore;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAttemptTime;

    @NotNull
    private Integer timeLimitRemaining;

    @NotNull
    private boolean hasOpenEnded;
    @NotNull
    private AssessmentAttemptStatusEnum assessmentAttemptStatusEnum;  //INCOMPLETE, SUBMITTED, GRADED

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAttempt> questionAttempts;

    @ManyToOne
    @JoinColumn
    private Learner learner;

    @ManyToOne
    @JoinColumn
    private Quiz attemptedQuiz;

    public QuizAttempt() {
        this.attemptCounter = 1;
        this.obtainedScore = 0.0;
        this.lastAttemptTime = new Date();
        this.assessmentAttemptStatusEnum = AssessmentAttemptStatusEnum.INCOMPLETE;
        this.questionAttempts = new ArrayList<>();
        this.hasOpenEnded = false;
        this.learnerMcqScore = 0.0;
    }

    public Double getLearnerMcqScore() {
        return learnerMcqScore;
    }

    public void setLearnerMcqScore(Double learnerMcqScore) {
        this.learnerMcqScore = learnerMcqScore;
    }

    public boolean isHasOpenEnded() {
        return hasOpenEnded;
    }

    public void setHasOpenEnded(boolean hasOpenEnded) {
        this.hasOpenEnded = hasOpenEnded;
    }

    public QuizAttempt(String quizAttemptDescription) {
        this();
    }

    public Long getQuizAttemptId() {
        return quizAttemptId;
    }

    public void setQuizAttemptId(Long quizAttemptId) {
        this.quizAttemptId = quizAttemptId;
    }

    public Double getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScore(Double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public List<QuestionAttempt> getQuestionAttempts() {
        return questionAttempts;
    }

    public void setQuestionAttempts(List<QuestionAttempt> questionAttempts) {
        this.questionAttempts = questionAttempts;
    }

    public Integer getAttemptCounter() {
        return attemptCounter;
    }

    public void setAttemptCounter(Integer attemptCounter) {
        this.attemptCounter = attemptCounter;
    }

    public Date getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void setLastAttemptTime(Date lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }

    public AssessmentAttemptStatusEnum getAssessmentAttemptStatusEnum() {
        return assessmentAttemptStatusEnum;
    }

    public void setAssessmentAttemptStatusEnum(AssessmentAttemptStatusEnum assessmentAttemptStatusEnum) {
        this.assessmentAttemptStatusEnum = assessmentAttemptStatusEnum;
    }

    public Integer getTimeLimitRemaining() {
        return timeLimitRemaining;
    }

    public void setTimeLimitRemaining(Integer timeLimitRemaining) {
        this.timeLimitRemaining = timeLimitRemaining;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public Quiz getAttemptedQuiz() {
        return attemptedQuiz;
    }

    public void setAttemptedQuiz(Quiz attemptedQuiz) {
        this.attemptedQuiz = attemptedQuiz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quizAttemptId != null ? quizAttemptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof QuizAttempt)) {
            return false;
        }
        QuizAttempt other = (QuizAttempt) object;
        if ((this.quizAttemptId == null && other.quizAttemptId != null) || (this.quizAttemptId != null && !this.quizAttemptId.equals(other.quizAttemptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.QuizAttempt[ id=" + quizAttemptId + " ]";
    }


}
