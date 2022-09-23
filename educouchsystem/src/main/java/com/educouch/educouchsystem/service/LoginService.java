package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.LoggedInUserDTO;
import com.educouch.educouchsystem.dto.LoggedInUserRequestDTO;

public interface LoginService {
    public LoggedInUserDTO login(LoggedInUserRequestDTO loggedInUserRequestDTO);
    public LoggedInUserDTO findUserByUsernameAndEntityType(String username, String entityType);

}
