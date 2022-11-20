package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.s3.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailSenderServiceImpl implements EmailSenderService{
@Autowired
private JavaMailSender mailSender;

@Autowired
private StorageService storageService;


    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("educouchsystem@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail sent successfully");
    }

    public void sendEmailWithAttachment(String toEmail, String subject, String body, byte[] data, String fileName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            File file = storageService.convertByteArrToFile(data, fileName);
            helper.setFrom("educouchsystem@gmail.com");
            helper.setTo(toEmail);
            helper.setText(body);
            helper.setSubject(subject);
            helper.addAttachment(fileName,file);
            mailSender.send(mimeMessage);

            System.out.println("Mail sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
