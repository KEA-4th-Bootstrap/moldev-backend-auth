package org.bootstrap.auth.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@RequiredArgsConstructor
@Component
public class MailProvider {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(MimeMessage message) {
        javaMailSender.send(message);
    }

    public MimeMessage createMimeMessage(String receiver, String subject, String type, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setRecipients(Message.RecipientType.TO, receiver);
            message.setFrom(sender);
            message.setSubject(subject);
            message.setText(setContext(text, type), "utf-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public MimeMessage createEmailVerificationForm(String email, String verificationCode) {
        String subject = "Email Verification Code";
        String text = "Your email verification code is " + verificationCode;

        return createMimeMessage(email, subject, "email", text);
    }

    public String createRandomEmailVerificationCode() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;

        int verificationCode = random.nextInt((max - min) + 1) + min;

        return String.valueOf(verificationCode);
    }

    private String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }
}
