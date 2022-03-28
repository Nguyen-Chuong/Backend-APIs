package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Log4j2
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    public EmailServiceImpl(JavaMailSender sender){
        this.sender = sender;
    }

    @Override
    public void send(String to, String subject, String content) {
        log.info("Request to send an email to user");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("travesily.no.reply@gmail.com");
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        sender.send(mail);
    }

    @Override
    public void sendHTMLMail(String to, String subject, String content) {
        log.info("Request to send html email");
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom("travesily.no.reply@gmail.com");
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
