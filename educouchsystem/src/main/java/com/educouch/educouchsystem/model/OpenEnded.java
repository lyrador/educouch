package com.educouch.educouchsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class OpenEnded implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long openEndedId;

    @NotNull
    private String content;

    @OneToOne
    private Question question;

    public OpenEnded() {
    }

    public OpenEnded(String content, Question question) {
        this();
        this.content = content;
        this.question = question;
    }

    public Long getOpenEndedId() {
        return openEndedId;
    }

    public void setOpenEndedId(Long openEndedId) {
        this.openEndedId = openEndedId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (openEndedId != null ? openEndedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof OpenEnded)) {
            return false;
        }
        OpenEnded other = (OpenEnded) object;
        if ((this.openEndedId == null && other.openEndedId != null) || (this.openEndedId != null && !this.openEndedId.equals(other.openEndedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OpenEnded[ id=" + openEndedId + " ]";
    }

}
