package com.catdog.comerce.service;


import com.catdog.comerce.dto.response.ResponseSelling;

public interface IEmailService {
    void sendEmailBySelling(ResponseSelling responseSelling, String subject, String text);
}
