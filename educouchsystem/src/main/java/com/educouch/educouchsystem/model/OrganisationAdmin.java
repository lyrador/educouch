package com.educouch.educouchsystem.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class OrganisationAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organisationAdminId;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String username;
    private String profilePictureURL;

    private Boolean isActive;

    @OneToOne(mappedBy = "organisationAdmin")
    private Organisation organisation;

    public OrganisationAdmin() {
    }

    public OrganisationAdmin(@NotBlank(message = "Name is mandatory") String name, String email, String password, String username) {
        this.isActive = true;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Long getOrganisationAdminId() {
        return organisationAdminId;
    }

    public void setOrganisationAdminId(Long organisationAdminId) {
        this.organisationAdminId = organisationAdminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
