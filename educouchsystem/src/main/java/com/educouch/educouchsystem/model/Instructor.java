package com.educouch.educouchsystem.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String email;
    private String password;
    private String username;
    private String profilePictureURL;

    @ManyToOne
    private Organisation organisation;

    public Instructor() {
    }

    public Instructor(@NotBlank(message = "Name is mandatory") String name, String email, String password, String username) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
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
}
