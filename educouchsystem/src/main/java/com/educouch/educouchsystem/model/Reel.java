package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.ReelApprovalStatusEnum;
import com.sun.istack.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Reel implements Serializable   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reelId;

    @NotNull
    @Size(min = 1, max = 100)
    private String reelTitle;

    @NotNull
    @Size(min = 1, max = 250)
    private String reelCaption;

    @NotNull
    private Integer numLikes;

    @NotNull
    private ReelApprovalStatusEnum reelApprovalStatusEnum;

    @OneToMany
    private Set<Learner> likers;

    @OneToMany
    private Set<Learner> viewers;

    @NotNull
    @OneToOne
    @JoinColumn
    private Course courseTag;

    @NotNull
    @OneToOne
    @JoinColumn
    private Instructor reelCreator;

    @OneToOne
    @JoinColumn
    private Attachment video;



    public Reel() {
        this.likers = new HashSet<>();
        this.viewers = new HashSet<>();
        this.reelApprovalStatusEnum = ReelApprovalStatusEnum.PENDING;
    }

    public Reel(String reelTitle, String reelCaption) {
        this();
        this.reelTitle = reelTitle;
        this.reelCaption = reelCaption;
        this.numLikes = 0;
    }

    public Long getReelId() {
        return reelId;
    }

    public void setReelId(Long reelId) {
        this.reelId = reelId;
    }

    public String getReelTitle() {
        return reelTitle;
    }

    public void setReelTitle(String reelTitle) {
        this.reelTitle = reelTitle;
    }

    public String getReelCaption() {
        return reelCaption;
    }

    public void setReelCaption(String reelCaption) {
        this.reelCaption = reelCaption;
    }

    public Integer getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(Integer numLikes) {
        this.numLikes = numLikes;
    }

    public ReelApprovalStatusEnum getReelApprovalStatusEnum() {
        return reelApprovalStatusEnum;
    }

    public void setReelApprovalStatusEnum(ReelApprovalStatusEnum reelApprovalStatusEnum) {
        this.reelApprovalStatusEnum = reelApprovalStatusEnum;
    }

    public Course getCourseTag() {
        return courseTag;
    }

    public void setCourseTag(Course courseTag) {
        this.courseTag = courseTag;
    }

    public Instructor getReelCreator() {
        return reelCreator;
    }

    public void setReelCreator(Instructor reelCreator) {
        this.reelCreator = reelCreator;
    }

    public Set<Learner> getLikers() {
        return likers;
    }

    public void setLikers(Set<Learner> likers) {
        this.likers = likers;
    }

    public Attachment getVideo() {
        return video;
    }

    public void setVideo(Attachment video) {
        this.video = video;
    }

    public Set<Learner> getViewers() {
        return viewers;
    }

    public void setViewers(Set<Learner> viewers) {
        this.viewers = viewers;
    }
}
