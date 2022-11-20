package com.educouch.educouchsystem.service;

public interface EmailSenderService {
    public void sendEmail(String toEmail, String subject, String body);

    public void sendEmailWithAttachment(String toEmail, String subject, String body, byte[] arr, String fileStorageName);

    }
