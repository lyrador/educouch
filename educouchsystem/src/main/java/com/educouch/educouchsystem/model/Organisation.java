package com.educouch.educouchsystem.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organisationId;
    private String organisationName;

    @OneToOne
    @JoinColumn(name = "organisationAdminId")
    private OrganisationAdmin organisationAdmin;
    @OneToMany(mappedBy = "organisation")
    private List<Instructor> instructors;

    public Organisation() {
        this.instructors = new ArrayList<>();
    }

    public Organisation(String organisationName) {
        this();
        this.organisationName = organisationName;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public OrganisationAdmin getOrganisationAdmin() {
        return organisationAdmin;
    }

    public void setOrganisationAdmin(OrganisationAdmin organisationAdmin) {
        this.organisationAdmin = organisationAdmin;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }
}
