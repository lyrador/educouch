package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.util.enumeration.ReelApprovalStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class ReelDTO {

    private Long reelId;
    private String reelTitle;
    private String reelCaption;
    private Integer numLikes;
    private ReelApprovalStatusEnum reelApprovalStatusEnum;
    private Course courseTag;
    private Long courseId;     //attribute not in Reel entity
    private Instructor reelCreator;
    private Long instructorId;     //attribute not in Reel entity
    private Attachment video;
    private Set<Learner> likers;
    private Set<Learner> viewers;
    private boolean isLiked;     //attribute not in Reel entity



    public ReelDTO() {
    }

    public ReelDTO(Long reelId, String reelTitle, String reelCaption, Integer numLikes, ReelApprovalStatusEnum reelApprovalStatusEnum, Long instructorId, Long courseId, Attachment attachment) {
        this.reelId = reelId;
        this.reelTitle = reelTitle;
        this.reelCaption = reelCaption;
        this.numLikes = numLikes;
        this.reelApprovalStatusEnum = reelApprovalStatusEnum;
        this.instructorId = instructorId;
        this.courseId = courseId;
        this.video = attachment;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Instructor getReelCreator() {
        return reelCreator;
    }

    public void setReelCreator(Instructor reelCreator) {
        this.reelCreator = reelCreator;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Attachment getVideo() {
        return video;
    }

    public void setVideo(Attachment video) {
        this.video = video;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Set<Learner> getLikers() {
        return likers;
    }

    public void setLikers(Set<Learner> likers) {
        this.likers = likers;
    }

    public Set<Learner> getViewers() {
        return viewers;
    }

    public void setViewers(Set<Learner> viewers) {
        this.viewers = viewers;
    }
}
