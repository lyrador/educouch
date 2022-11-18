package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.TechnicalSupportRequestStatus;

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

    @Enumerated
    @Column(name="technicalSupportRequestStatus")
    private TechnicalSupportRequestStatus technicalSupportRequestStatus;

    @OneToOne
    @JoinColumn(name="learner_id")
    private Learner createdByLearner;

    @OneToOne
    @JoinColumn(name="instructor_id")
    private Instructor createdByInstructor;

    @OneToOne
    @JoinColumn(name="organisationAdmin_id")
    private OrganisationAdmin createdByOrganisationAdmin;

    public TechnicalSupportRequest() {
        this.technicalSupportRequestStatus = TechnicalSupportRequestStatus.PENDING;
    }

    public TechnicalSupportRequest(String title, String description, LocalDateTime timestamp) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
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

    public TechnicalSupportRequestStatus getTechnicalSupportRequestStatus() {
        return technicalSupportRequestStatus;
    }

    public void setTechnicalSupportRequestStatus(TechnicalSupportRequestStatus technicalSupportRequestStatus) {
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
