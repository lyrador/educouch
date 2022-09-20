package com.educouch.educouchsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Option {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    @NotNull
    private String optionContent;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Question optionQuestion;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private QuestionAttempt learnerQuestionAttempt;

    public Option() {
    }

    public Option(String optionContent, Question optionQuestion, QuestionAttempt learnerQuestionAttempt) {
        this();
        this.optionContent = optionContent;
        this.optionQuestion = optionQuestion;
        this.learnerQuestionAttempt = learnerQuestionAttempt;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public Question getOptionQuestion() {
        return optionQuestion;
    }

    public void setOptionQuestion(Question optionQuestion) {
        this.optionQuestion = optionQuestion;
    }

    public QuestionAttempt getLearnerQuestionAttempt() {
        return learnerQuestionAttempt;
    }

    public void setLearnerQuestionAttempt(QuestionAttempt learnerQuestionAttempt) {
        this.learnerQuestionAttempt = learnerQuestionAttempt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optionId != null ? optionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Option)) {
            return false;
        }
        Option other = (Option) object;
        if ((this.optionId == null && other.optionId != null) || (this.optionId != null && !this.optionId.equals(other.optionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Option[ id=" + optionId + " ]";
    }

}
