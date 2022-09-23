package com.educouch.educouchsystem.dto;

public class LoggedInUserRequestDTO {
    private String password;
    private String username;
    private String userType;

    public LoggedInUserRequestDTO() {
    }

    public LoggedInUserRequestDTO(String password, String username, String userType) {
        this.password = password;
        this.username = username;
        this.userType = userType;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
