package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.EditAccountDTO;
import com.educouch.educouchsystem.dto.EditAccountRequestDTO;
import com.educouch.educouchsystem.dto.LoggedInUserDTO;
import com.educouch.educouchsystem.dto.LoggedInUserRequestDTO;
import com.educouch.educouchsystem.service.AccountService;
import com.educouch.educouchsystem.service.AttachmentService;
import com.educouch.educouchsystem.service.LoginService;
import com.educouch.educouchsystem.util.logger.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class AccountController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/account/edit")
    public ResponseEntity<EditAccountDTO> updateAccount(@RequestBody EditAccountRequestDTO editAccountRequestDTO) throws Exception {
        try {
            EditAccountDTO editAccountDTO = accountService.updateAccount(editAccountRequestDTO);
            return new ResponseEntity<>(editAccountDTO, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/account/delete")
    public ResponseEntity<HttpStatus> deleteAccount(@RequestBody EditAccountRequestDTO editAccountRequestDTO) {
        accountService.deleteAccount(editAccountRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

