package com.educouch.educouchsystem.util.exception;

import com.educouch.educouchsystem.model.InteractivePage;

public class InteractivePageNotFoundException extends Exception{

    public InteractivePageNotFoundException() {
    }

    public InteractivePageNotFoundException(String msg) {
        super(msg);
    }
}
