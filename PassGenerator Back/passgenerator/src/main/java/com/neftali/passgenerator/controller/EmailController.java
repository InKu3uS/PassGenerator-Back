package com.neftali.passgenerator.controller;

import com.neftali.passgenerator.dto.EmailDTO;
import com.neftali.passgenerator.service.IEmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailController {
    @Autowired
    IEmailService emailService;

    @PostMapping(value = {"/send"})
    private ResponseEntity<String> sendEmail(@RequestBody EmailDTO email) throws MessagingException {
        emailService.sendMail(email);
        return new ResponseEntity<>("Correo enviado con exito", HttpStatus.OK);
    }
}
