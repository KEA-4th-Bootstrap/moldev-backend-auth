package org.bootstrap.auth.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.common.error.EntityNotFoundException;
import org.bootstrap.auth.common.error.GlobalErrorCode;
import org.bootstrap.auth.common.error.UnAuthenticationException;
import org.bootstrap.auth.dto.request.SendEmailRequestDto;
import org.bootstrap.auth.dto.request.VerifyEmailRequestDto;
import org.bootstrap.auth.dto.response.SendEmailResponseDto;
import org.bootstrap.auth.dto.response.VerifyEmailResponseDto;
import org.bootstrap.auth.redis.entity.EmailVerificationCode;
import org.bootstrap.auth.redis.repository.EmailVerificationCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Value("${spring.mail.username}")
    private String sender;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final JavaMailSender javaMailSender;

    public SendEmailResponseDto sendEmailVerificationForm(SendEmailRequestDto sendEmailRequestDto) {
        try {
            String verificationCode = createRandomEmailVerificationCode();
            MimeMessage message = createEmailVerificationForm(sendEmailRequestDto.email(), verificationCode);
            javaMailSender.send(message);
            emailVerificationCodeRepository.save(EmailVerificationCode.of(sendEmailRequestDto.email(), verificationCode));
            return SendEmailResponseDto.of(true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public VerifyEmailResponseDto verifyEmail(VerifyEmailRequestDto verifyEmailRequestDto) {
        log.info("verifyEmailRequestDto: {}", verifyEmailRequestDto);
        if (!emailVerificationCodeRepository.existsById(verifyEmailRequestDto.email())) {
            throw new EntityNotFoundException(GlobalErrorCode.ENTITY_NOT_FOUND);
        }

        String savedVerificationCode = getSavedVerificationCode(verifyEmailRequestDto.email());
        emailVerificationCodeRepository.deleteById(verifyEmailRequestDto.email());

        if (!Objects.equals(verifyEmailRequestDto.code(), savedVerificationCode)) {
            throw new UnAuthenticationException(GlobalErrorCode.INVALID_EMAIL_CODE);
        }

        return VerifyEmailResponseDto.of(true);
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

    private String getSavedVerificationCode(String email) {
        return emailVerificationCodeRepository.findById(email).get().getCode();
    }

}
