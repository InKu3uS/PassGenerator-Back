package com.neftali.passgenerator.service;

import com.neftali.passgenerator.dto.EmailDTO;
import com.neftali.passgenerator.entity.Cuenta;
import com.neftali.passgenerator.repository.CuentaRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine){
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    @Override
    public void sendMail(EmailDTO emailDTO) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emailDTO.getRecipent());
            helper.setSubject(emailDTO.getSubject());

            Context context = new Context();
            context.setVariable("recipent", emailDTO.getRecipent());
            context.setVariable("message", emailDTO.getMessage());
            context.setVariable("year", Year.now().getValue());
            String template = templateEngine.process("welcome", context);
            helper.setText(template, true);

            ClassPathResource logo = new ClassPathResource("static/images/logo.png");
            helper.addInline("logo", logo, "image/png");

            javaMailSender.send(message);
        } catch (Exception e){
            throw new MessagingException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }

    @Override
    public void sendExpirationWarning(String email, String name, String content) throws MessagingException {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("PassGenerator - aviso de expiración");

            Context context = new Context();
            context.setVariable("recipent", email);
            context.setVariable("message", name+content);
            context.setVariable("year", Year.now().getValue());
            String template = templateEngine.process("expirationWarning", context);
            helper.setText(template, true);

            ClassPathResource logo = new ClassPathResource("static/images/logo.png");
            helper.addInline("logo", logo, "image/png");

            javaMailSender.send(message);
        } catch (Exception e){
            throw new MessagingException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }

    @Override
    public void sendFarewellEmail(String email, String name) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("PassGenerator - Esperamos verte pronto");

            Context context = new Context();
            context.setVariable("recipent", email);
            context.setVariable("name", name);
            context.setVariable("year", Year.now().getValue());
            String template = templateEngine.process("farewell", context);
            helper.setText(template, true);

            ClassPathResource logo = new ClassPathResource("static/images/logo.png");
            helper.addInline("logo", logo, "image/png");

            javaMailSender.send(message);
        } catch (Exception e){
            throw new MessagingException("Error al enviar el correo: "+ e.getMessage());
        }
    }


    @Override
    @Scheduled(cron = "0 00 10 * * ?")
    public void checkExpirationAndSendEmail() throws MessagingException {

        LocalDate now = LocalDate.now();
        List<Cuenta> allAccounts = cuentaRepository.findAll();
        Map<String, Boolean> usersNotified = new HashMap<>();

        for(Cuenta cuenta : allAccounts){

            LocalDate expirationTime = dateParser(cuenta.getExpirationTime());

            if(expirationTime != null){

                LocalDate notificationThreshold = expirationTime.minusDays(7);
                String user = cuenta.getUser().getEmail();

                if(!cuenta.isNotifiedForExpiration() && now.isAfter(notificationThreshold) && now.isBefore(expirationTime)){

                    if(!usersNotified.containsKey(user)) {
                        sendExpirationWarning(cuenta.getUser().getEmail(), cuenta.getUser().getUsername(), ", una de tus contraseñas esta a punto de expirar.");
                        usersNotified.put(user, true);
                    }

                    cuenta.setNotifiedForExpiration(true);
                    cuentaRepository.save(cuenta);
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "0 00 10 * * ?")
    public void checkPasswordExpiredAndSendEmail() throws MessagingException {

        LocalDate now = LocalDate.now();

        List<Cuenta> allAccounts = cuentaRepository.findAll();
        Map<String, Boolean> usersNotified = new HashMap<>();

        for(Cuenta cuenta : allAccounts){

            LocalDate expirationTime = dateParser(cuenta.getExpirationTime());
            String user = cuenta.getUser().getEmail();

            if(expirationTime != null){

                if(!cuenta.isNotifiedForExpired() && expirationTime.isBefore(now)){

                    if(!usersNotified.containsKey(user)){
                    sendExpirationWarning(cuenta.getUser().getEmail(), cuenta.getUser().getUsername(), ", una de tus contraseñas ha expirado.");
                    usersNotified.put(user, true);
                    }
                    cuenta.setNotifiedForExpired(true);
                    cuentaRepository.save(cuenta);
                }
            }
        }
    }



    public LocalDate dateParser(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e){
            System.out.println("Error al parsear la fecha: " +e.getMessage());
            return null;
        }
    }
}
