package org.bootstrap.auth.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.common.error.EntityNotFoundException;
import org.bootstrap.auth.common.error.GlobalErrorCode;
import org.bootstrap.auth.common.error.UnAuthenticationException;
import org.bootstrap.auth.dto.request.SendEmailRequestDto;
import org.bootstrap.auth.dto.request.VerifyEmailRequestDto;
import org.bootstrap.auth.dto.response.SendEmailResponseDto;
import org.bootstrap.auth.dto.response.VerifyEmailResponseDto;
import org.bootstrap.auth.mail.MailProvider;
import org.bootstrap.auth.redis.entity.EmailVerificationCode;
import org.bootstrap.auth.redis.repository.EmailVerificationCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final MailProvider mailProvider;

    public SendEmailResponseDto sendEmailVerificationForm(SendEmailRequestDto sendEmailRequestDto) {
        String verificationCode = mailProvider.createRandomEmailVerificationCode();
        MimeMessage message = mailProvider.createEmailVerificationForm(sendEmailRequestDto.email(), verificationCode);
        mailProvider.sendEmail(message);
        emailVerificationCodeRepository.save(EmailVerificationCode.of(sendEmailRequestDto.email(), verificationCode));
        return SendEmailResponseDto.of(true);
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

    private String getSavedVerificationCode(String email) {
        return emailVerificationCodeRepository.findById(email).get().getCode();
    }

}
