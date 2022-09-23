package com.educouch.educouchsystem.dto;

public class EditAccountDTO {

    private Long userId;
    private String name;
    private String address;
    private String email;
    private String password;
    private String username;
    private String profilePictureURL;
    private String userType;
    private String userEnum;

    public EditAccountDTO() {
    }

    public EditAccountDTO(Long userId, String name, String address, String email, String password, String username, String profilePictureURL, String userType, String userEnum) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profilePictureURL = profilePictureURL;
        this.userType = userType;
        this.userEnum = userEnum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserEnum() {
        return userEnum;
    }

    public void setUserEnum(String userEnum) {
        this.userEnum = userEnum;
    }
}
