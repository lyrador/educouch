package com.educouch.educouchsystem.model;


import javax.persistence.*;

@Entity
public class Learner {

    //@id makes this a primary key, GenerationType.IDENTITY lets PK auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learnerId;
    @Column(nullable = false)
    private String name;
//    private String address;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String username;

    private String profilePictureURL;

    private Boolean isActive;
    @Column(nullable = false)
    private Boolean isKid;

    public Learner() {
    }

//    public Learner(String name, String address, String email, String password, String username, String profilePictureURL) {
//        this.name = name;
//        this.address = address;
//        this.email = email;
//        this.password = password;
//        this.username = username;
//        this.profilePictureURL = profilePictureURL;
//        isActive = true;
//    }

//    public Learner(String name, String address, String email, String password, String username, String profilePictureURL, Boolean isKid) {
//        this.name = name;
//        this.address = address;
//        this.email = email;
//        this.password = password;
//        this.username = username;
//        this.profilePictureURL = profilePictureURL;
//        this.isActive = true;
//        this.isKid = isKid;
//    }


    public Learner(String name, String email, String password, String username, String profilePictureURL, Boolean isKid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profilePictureURL = profilePictureURL;
        this.isActive = true;
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

//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public Boolean getIsKid() {
        return isKid;
    }

    public void setIsKid(Boolean isKid) {
        isKid = isKid;
    }
}
