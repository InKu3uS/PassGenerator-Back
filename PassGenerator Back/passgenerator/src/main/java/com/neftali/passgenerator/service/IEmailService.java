package com.neftali.passgenerator.service;

import com.neftali.passgenerator.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface IEmailService {

    public void sendMail(EmailDTO emailDTO) throws MessagingException;
}
