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

public class ReelDTO {

    private Long reelId;

    private String reelTitle;

    private String reelCaption;

    private Integer numLikes;

    private ReelApprovalStatusEnum reelApprovalStatusEnum;

    private Course courseTag;

    //attribute not in Reel entity
    private Long courseId;

    private Instructor reelCreator;

    //attribute not in Reel entity
    private Long instructorId;

    private Attachment video;

    private List<Learner> likers;

    public ReelDTO() {
    }

    public ReelDTO(Long reelId, String reelTitle, String reelCaption, Integer numLikes, ReelApprovalStatusEnum reelApprovalStatusEnum, Long instructorId, Long courseId) {
        this.reelId = reelId;
        this.reelTitle = reelTitle;
        this.reelCaption = reelCaption;
        this.numLikes = numLikes;
        this.reelApprovalStatusEnum = reelApprovalStatusEnum;
        this.instructorId = instructorId;
        this.courseId = courseId;
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

    public List<Learner> getLikers() {
        return likers;
    }

    public void setLikers(List<Learner> likers) {
        this.likers = likers;
    }
}
