package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;

    @NotNull
    private String announcementTitle;

    @NotNull
    private String announcementBody;

    private LocalDateTime timestamp;

    @OneToOne
    @JoinColumn(name="instructor_id")
    private Instructor createdByInstructor;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    public Announcement() {
    }

    public Announcement(String announcementTitle, String announcementBody, LocalDateTime timestamp) {
        this.announcementTitle = announcementTitle;
        this.announcementBody = announcementBody;
        this.timestamp = timestamp;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementBody() {
        return announcementBody;
    }

    public void setAnnouncementBody(String announcementBody) {
        this.announcementBody = announcementBody;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Instructor getCreatedByInstructor() {
        return createdByInstructor;
    }

    public void setCreatedByInstructor(Instructor createdByInstructor) {
        this.createdByInstructor = createdByInstructor;
    }

    @JsonIgnore
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (announcementId != null ? announcementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Announcement)) {
            return false;
        }
        Announcement other = (Announcement) object;
        if ((this.announcementId == null && other.announcementId != null) || (this.announcementId != null && !this.announcementId.equals(other.announcementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Announcement[ id=" + announcementId + " ]";
    }
}
