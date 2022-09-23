package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.EditAccountDTO;
import com.educouch.educouchsystem.dto.EditAccountRequestDTO;

public interface AccountService {
    public EditAccountDTO updateAccount(EditAccountRequestDTO editAccountRequestDTO);
    public void deleteAccount(EditAccountRequestDTO editAccountRequestDTO);
}
