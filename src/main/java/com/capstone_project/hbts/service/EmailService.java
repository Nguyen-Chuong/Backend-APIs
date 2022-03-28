package com.capstone_project.hbts.service;

public interface EmailService {

    /**
     * send email
     * @param to
     * @param subject
     * @param content
     */
    void send(String to, String subject, String content);

    /**
     * send html email
     * @param to
     * @param subject
     * @param content
     */
    void sendHTMLMail(String to, String subject, String content);

}
