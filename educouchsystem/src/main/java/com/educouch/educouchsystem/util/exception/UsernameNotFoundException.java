package com.educouch.educouchsystem.util.exception;

public class UsernameNotFoundException extends RuntimeException{


    public UsernameNotFoundException() {
    }
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
