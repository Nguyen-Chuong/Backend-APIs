package com.capstone_project.hbts.service;

public interface EmailService {

    /**
     * send email
     */
    void send(String to, String subject, String content);

    /**
     * send html email
     */
    void sendHTMLMail(String to, String subject, String content);

}
