package com.educouch.educouchsystem.model;

import javax.persistence.*;

@Entity
public class LmsAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lmsAdminId;

    private String name;

    private String email;

    private String password;
    @Column(unique = true)
    private String username;

    private boolean isActive;

    private String profilePicture;

    public LmsAdmin() {
    }

    public LmsAdmin(String name, String email, String password, String username) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isActive = true;
    }

    public Long getLmsAdminId() {
        return lmsAdminId;
    }

    public void setLmsAdminId(Long lmsAdminId) {
        this.lmsAdminId = lmsAdminId;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }


}
