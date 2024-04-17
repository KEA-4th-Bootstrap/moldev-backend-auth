package org.bootstrap.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.common.SuccessResponse;
import org.bootstrap.auth.dto.request.LoginRequestDto;
import org.bootstrap.auth.dto.request.SendEmailRequestDto;
import org.bootstrap.auth.dto.request.SignUpRequestDto;
import org.bootstrap.auth.dto.request.VerifyEmailRequestDto;
import org.bootstrap.auth.dto.response.LoginResponseDto;
import org.bootstrap.auth.dto.response.SendEmailResponseDto;
import org.bootstrap.auth.dto.response.SignUpResponseDto;
import org.bootstrap.auth.dto.response.VerifyEmailResponseDto;
import org.bootstrap.auth.service.AuthService;
import org.bootstrap.auth.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<SuccessResponse<?>> sendEmail(@RequestBody @Valid SendEmailRequestDto sendEmailRequestDto) {
        SendEmailResponseDto sendEmailResponseDto = emailService.sendEmailVerificationForm(sendEmailRequestDto);
        return SuccessResponse.ok(sendEmailResponseDto);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<SuccessResponse<?>> verifyEmail(@RequestBody @Valid VerifyEmailRequestDto verifyEmailRequestDto) {
        VerifyEmailResponseDto verifyEmailResponseDto = emailService.verifyEmail(verifyEmailRequestDto);
        return SuccessResponse.ok(verifyEmailResponseDto);
    }


    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> signUp(@RequestPart SignUpRequestDto signUpRequestDto,
                                                     @RequestPart(required = false) MultipartFile profileImage) {
        SignUpResponseDto signUpResponseDto = authService.signUp(signUpRequestDto, profileImage);
        return SuccessResponse.created(signUpResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<?>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return SuccessResponse.ok(loginResponseDto);
    }
}
