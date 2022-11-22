package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.util.enumeration.ReelApprovalStatusEnum;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ReelDTO {

    private Long reelId;
    private String reelTitle;
    private String reelCaption;
    private Integer numLikes;
    private Integer numViews;
    private ReelApprovalStatusEnum reelApprovalStatusEnum;
    private Course courseTag;
    private Long courseId;     //attribute not in Reel entity
    private Instructor reelCreator;
    private Long instructorId;     //attribute not in Reel entity
    private String creatorName;    //attribute not in Reel entity
    private Date reelTimeStamp;
    private Attachment video;
    private Attachment thumbnail;
    private List<Learner> likers;
    private List<Learner> viewers;
    private boolean isLiked;     //attribute not in Reel entity
    private String rejectionReason;



    public ReelDTO() {
    }

    public ReelDTO(Long reelId, String reelTitle, String reelCaption,
                   Integer numLikes, Integer numViews,
                   ReelApprovalStatusEnum reelApprovalStatusEnum,
                   Long instructorId, String creatorName, Long courseId,
                   Attachment attachment, Date reelTimeStamp,
                   String rejectionReason) {
        this.reelId = reelId;
        this.reelTitle = reelTitle;
        this.reelCaption = reelCaption;
        this.numLikes = numLikes;
        this.numViews = numViews;
        this.reelApprovalStatusEnum = reelApprovalStatusEnum;
        this.instructorId = instructorId;
        this.creatorName = creatorName;
        this.courseId = courseId;
        this.video = attachment;
        this.reelTimeStamp = reelTimeStamp;
        this.rejectionReason = rejectionReason;
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

    public List<Learner> getLikers() {
        return likers;
    }

    public void setLikers(List<Learner> likers) {
        this.likers = likers;
    }

    public List<Learner> getViewers() {
        return viewers;
    }

    public void setViewers(List<Learner> viewers) {
        this.viewers = viewers;
    }

    public Date getReelTimeStamp() {
        return reelTimeStamp;
    }

    public void setReelTimeStamp(Date reelTimeStamp) {
        this.reelTimeStamp = reelTimeStamp;
    }

    public Integer getNumViews() {
        return numViews;
    }

    public void setNumViews(Integer numViews) {
        this.numViews = numViews;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Attachment getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Attachment thumbnail) {
        this.thumbnail = thumbnail;
    }
}
