package com.educouch.educouchsystem.dto;

import javax.persistence.Column;

public class LearnerDTO {
    private Long learnerId;
    private String name;
    private String email;
    private String password;
    private String username;
    private String profilePictureURL;
    private String isActive;
    private String isKid;

    public LearnerDTO() {
    }

    public LearnerDTO(Long learnerId, String name, String email, String password, String username, String profilePictureURL, String isActive, String isKid) {
        this.learnerId = learnerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profilePictureURL = profilePictureURL;
        this.isActive = isActive;
        this.isKid = isKid;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsKid() {
        return isKid;
    }

    public void setIsKid(String isKid) {
        this.isKid = isKid;
    }
}
