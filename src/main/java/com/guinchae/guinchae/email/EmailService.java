package com.guinchae.guinchae.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.*;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    // Email de confirmação de usuário!
    @Async
    public void sendConfirmationEmail(String to,
                          String username,
                          EmailTemplateName emailTemplate,
                          String confirmationUrl,
                          String activationCode,
                          String subject) throws MessagingException {
        String templateName;
        if(emailTemplate == null){
            templateName = "activate_account";
        }else {
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name());

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_Code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        mimeMessageHelper.setFrom("guinchae@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        mimeMessageHelper.setText(template, true);

        mailSender.send(mimeMessage);
    }
    // **********************************************************************************************************
    // *Metodos para simular um sistema de notificações, visto que eu ja havia implementado um sistema de email!*
    // **********************************************************************************************************
    @Async
    public void sendVehicleAddedEmail(String to,
                                         String username,
                                         EmailTemplateName emailTemplate,
                                         String vehicleModel,
                                         String licensePlate,
                                         String subject) throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "vehicle_added";
        } else {
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );


        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("carModel", vehicleModel);
        properties.put("licensePlate", licensePlate);

        Context context = new Context();
        context.setVariables(properties);

        mimeMessageHelper.setFrom("guinchae@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);


        String templateContent = templateEngine.process(templateName, context);
        mimeMessageHelper.setText(templateContent, true);

        mailSender.send(mimeMessage);
    }

    @Async
    public void sendTowTruckDriverEmail(String to,
                                      String username,
                                      EmailTemplateName emailTemplate,
                                      String subject) throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "ttdriver_added";
        } else {
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );


        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);

        Context context = new Context();
        context.setVariables(properties);

        mimeMessageHelper.setFrom("guinchae@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);


        String templateContent = templateEngine.process(templateName, context);
        mimeMessageHelper.setText(templateContent, true);

        mailSender.send(mimeMessage);
    }

}
