package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.TechnicalSupportRequestStatusEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TechnicalSupportRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalSupportRequestId;

    @Column(name="technicalSupportRequestTitle", nullable = false, length = 128)
    private String title;

    @Column(name="technicalSupportRequestDescription", nullable = false, length = 128)
    private String description;

    private LocalDateTime timestamp;

    private String imageUrl;

    @Enumerated
    @Column(name="technicalSupportRequestStatus")
    private TechnicalSupportRequestStatusEnum technicalSupportRequestStatus;

    @ManyToOne
    private Learner createdByLearner;

    @ManyToOne
    private Instructor createdByInstructor;

    @ManyToOne
    private OrganisationAdmin createdByOrganisationAdmin;

    public TechnicalSupportRequest() {
        this.technicalSupportRequestStatus = TechnicalSupportRequestStatusEnum.PENDING;
    }

    public TechnicalSupportRequest(String title, String description, LocalDateTime timestamp, String imageUrl) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
    }

    public Long getTechnicalSupportRequestId() {
        return technicalSupportRequestId;
    }

    public void setTechnicalSupportRequestId(Long technicalSupportRequestId) {
        this.technicalSupportRequestId = technicalSupportRequestId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public TechnicalSupportRequestStatusEnum getTechnicalSupportRequestStatus() {
        return technicalSupportRequestStatus;
    }

    public void setTechnicalSupportRequestStatus(TechnicalSupportRequestStatusEnum technicalSupportRequestStatus) {
        this.technicalSupportRequestStatus = technicalSupportRequestStatus;
    }

    public Learner getCreatedByLearner() {
        return createdByLearner;
    }

    public void setCreatedByLearner(Learner createdByLearner) {
        this.createdByLearner = createdByLearner;
    }

    public Instructor getCreatedByInstructor() {
        return createdByInstructor;
    }

    public void setCreatedByInstructor(Instructor createdByInstructor) {
        this.createdByInstructor = createdByInstructor;
    }

    public OrganisationAdmin getCreatedByOrganisationAdmin() {
        return createdByOrganisationAdmin;
    }

    public void setCreatedByOrganisationAdmin(OrganisationAdmin createdByOrganisationAdmin) {
        this.createdByOrganisationAdmin = createdByOrganisationAdmin;
    }
}
