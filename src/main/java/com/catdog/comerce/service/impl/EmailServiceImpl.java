package com.catdog.comerce.service.impl;


import com.catdog.comerce.dto.response.ResponseSelling;

import com.catdog.comerce.service.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements IEmailService {
    private  JavaMailSender mailSender;
    @Value("${mail.enterprise}")
    private String enterpriseMail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmailBySelling(ResponseSelling responseSelling, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(enterpriseMail);
        message.setTo(responseSelling.getResponseUserSellingDto().getEmail());
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
