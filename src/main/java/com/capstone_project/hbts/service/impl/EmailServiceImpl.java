package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    public EmailServiceImpl(JavaMailSender sender){
        this.sender = sender;
    }

    @Override
    public void send(String to, String subject, String content) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("travesily.no.reply@travesily.software");
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        sender.send(mail);
    }

    @Override
    public void sendHTMLMail(String to, String subject, String content) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom("travesily.no.reply@travesily.software");
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
