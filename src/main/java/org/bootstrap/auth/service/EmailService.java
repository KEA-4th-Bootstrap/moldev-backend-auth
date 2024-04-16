package org.bootstrap.auth.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.dto.response.SendEmailResponseDto;
import org.bootstrap.auth.redis.entity.EmailVerificationCode;
import org.bootstrap.auth.redis.repository.EmailVerificationCodeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final JavaMailSender javaMailSender;

    public SendEmailResponseDto sendEmailVerificationForm(String email) {
        try {
            String verificationCode = createRandomEmailVerificationCode();
            MimeMessage message = createEmailVerificationForm(email, verificationCode);
            javaMailSender.send(message);
            emailVerificationCodeRepository.save(EmailVerificationCode.of(email, verificationCode));
            return SendEmailResponseDto.of(true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private MimeMessage createEmailVerificationForm(String email, String verificationCode) throws MessagingException {
        String subject = "Email Verification Code";
        String text = "Your email verification code is " + verificationCode;

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(Message.RecipientType.TO, email);
        message.setFrom(sender);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }

    private String createRandomEmailVerificationCode() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;

        int verificationCode = random.nextInt((max - min) + 1) + min;

        return String.valueOf(verificationCode);
    }

}
