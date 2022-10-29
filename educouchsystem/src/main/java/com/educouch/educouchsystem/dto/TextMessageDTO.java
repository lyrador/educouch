package com.educouch.educouchsystem.dto;

public class TextMessageDTO {

    private String message;
    private String author;

    public TextMessageDTO() {
    }

    public TextMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
