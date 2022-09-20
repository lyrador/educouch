package com.educouch.educouchsystem.util.exception;

public class InvalidLoginCredentialsException extends RuntimeException{


    public InvalidLoginCredentialsException() {
    }
    public InvalidLoginCredentialsException(String message) {
        super(message);
    }
}
