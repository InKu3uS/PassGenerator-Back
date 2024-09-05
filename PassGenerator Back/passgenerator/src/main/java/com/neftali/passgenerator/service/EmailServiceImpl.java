package com.neftali.passgenerator.service;
import com.neftali.passgenerator.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

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
            String template = templateEngine.process("welcome", context);
            helper.setText(template, true);

            ClassPathResource logo = new ClassPathResource("static/images/logo.png");
            helper.addInline("logo", logo, "image/png");

            javaMailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
