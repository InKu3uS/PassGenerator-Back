package com.neftali.passgenerator.service;

import com.neftali.passgenerator.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface IEmailService {

    void sendMail(EmailDTO emailDTO) throws MessagingException;

    void sendExpirationWarning(String email, String name, String content) throws MessagingException;

    void checkExpirationAndSendEmail() throws MessagingException;

    void checkPasswordExpiredAndSendEmail() throws MessagingException;

    void sendFarewellEmail(String email, String name) throws MessagingException;
}
