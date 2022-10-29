package com.educouch.educouchsystem.util.exception;

public class UsernameExistException extends RuntimeException{


    public UsernameExistException() {
    }
    public UsernameExistException(String message) {
        super(message);
    }
}
