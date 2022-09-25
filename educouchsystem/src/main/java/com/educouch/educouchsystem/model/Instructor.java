package com.educouch.educouchsystem.model;
import com.educouch.educouchsystem.util.enumeration.InstructorAccessRight;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private InstructorAccessRight instructorAccessRight;
    private String profilePictureURL;

    @ManyToOne(optional = false)
    private Organisation organisation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "instructor_course",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "instructorId"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id", referencedColumnName = "courseId"))
    private List<Course> courses;

    public Instructor() {
    }

    public Instructor(@NotBlank(message = "Name is mandatory") String name, String email, String username, String password, InstructorAccessRight instructorAccessRight) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.instructorAccessRight = instructorAccessRight;
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

    @JsonIgnore
    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public InstructorAccessRight getInstructorAccessRight() {
        return instructorAccessRight;
    }

    public void setInstructorAccessRight(InstructorAccessRight instructorAccessRight) {
        this.instructorAccessRight = instructorAccessRight;
    }

    @JsonIgnore
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
