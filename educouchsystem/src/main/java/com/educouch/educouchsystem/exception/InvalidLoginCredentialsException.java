package com.educouch.educouchsystem.exception;

public class InvalidLoginCredentialsException extends RuntimeException{


    public InvalidLoginCredentialsException() {
    }
    public InvalidLoginCredentialsException(String message) {
        super(message);
    }
}
