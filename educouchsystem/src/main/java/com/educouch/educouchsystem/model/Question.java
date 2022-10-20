package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.QuestionTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    @NotNull
    private String localid;
    @NotNull
    private String questionTitle;
    @Enumerated(EnumType.STRING)
    @NotNull
    private QuestionTypeEnum questionType;
    @NotNull
    private String questionContent;
    private String questionHint;
    @NotNull
    private Double questionMaxScore;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="question_id")
    private List<Option> options;

    private Option correctOption;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="question_id")
    private List<QuestionAttempt> questionAttempts;

    @OneToOne
    private OpenEnded openEnded;

    @OneToOne(mappedBy = "question")
    private PageItem pageItem;


    public Question() {
        this.options = new ArrayList<>();
        this.questionAttempts = new ArrayList<>();

    }

    public Question(String questionContent, String questionHint, Double questionMaxScore, QuestionTypeEnum questionType) {
        this();
        this.questionContent = questionContent;
        this.questionHint = questionHint;
        this.questionMaxScore = questionMaxScore;
        this.questionType = questionType;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionHint() {
        return questionHint;
    }

    public void setQuestionHint(String questionHint) {
        this.questionHint = questionHint;
    }

    public Double getQuestionMaxScore() {
        return questionMaxScore;
    }

    public void setQuestionMaxScore(Double questionMaxScore) {
        this.questionMaxScore = questionMaxScore;
    }

    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public OpenEnded getOpenEnded() {
        return openEnded;
    }

    public void setOpenEnded(OpenEnded openEnded) {
        this.openEnded = openEnded;
    }

//    public List<Option> getOptions() {
//        return options;
//    }
//
//    public void setOptions(List<Option> options) {
//        this.options = options;
//    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<QuestionAttempt> getQuestionAttempts() {
        return questionAttempts;
    }

    public void setQuestionAttempts(List<QuestionAttempt> questionAttempts) {
        this.questionAttempts = questionAttempts;
    }

    public Option getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(Option correctOption) {
        this.correctOption = correctOption;
    }

    public String getLocalid() {
        return localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public PageItem getPageItem() {
        return pageItem;
    }

    public void setPageItem(PageItem pageItem) {
        this.pageItem = pageItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Question[ id=" + questionId + " ]";
    }

}
