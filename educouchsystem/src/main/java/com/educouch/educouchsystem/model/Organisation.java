package com.educouch.educouchsystem.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organisationId;
    @Column(nullable = false)
    private String organisationName;

    @OneToOne
    @JoinColumn(name = "organisationAdminId")
    private OrganisationAdmin organisationAdmin;
    @OneToMany(mappedBy = "organisation")
    private List<Instructor> instructors;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organisation")
    private List<Course> courses;

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
    @JsonBackReference
    public OrganisationAdmin getOrganisationAdmin() {
        return organisationAdmin;
    }

    public void setOrganisationAdmin(OrganisationAdmin organisationAdmin) {
        this.organisationAdmin = organisationAdmin;
    }
    @JsonManagedReference
    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    @JsonIgnore
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
