package org.bootstrap.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.dto.request.*;
import org.bootstrap.auth.dto.response.*;
import org.bootstrap.auth.service.AuthService;
import org.bootstrap.auth.service.EmailService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SendEmailResponseDto> sendEmail(@RequestBody @Valid SendEmailRequestDto sendEmailRequestDto) {
        final SendEmailResponseDto response = emailService.sendEmailVerificationForm(sendEmailRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/send-reset-email")
    public ResponseEntity<SendEmailResponseDto> sendEmailForPasswordChange(@RequestBody @Valid SendEmailRequestDto sendEmailRequestDto) {
        final SendEmailResponseDto response = emailService.sendEmailForPasswordChangeForm(sendEmailRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<VerifyEmailResponseDto> verifyEmail(@RequestBody @Valid VerifyEmailRequestDto verifyEmailRequestDto) {
        final VerifyEmailResponseDto response = emailService.verifyEmail(verifyEmailRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestPart SignUpRequestDto signUpRequestDto,
                                                     @RequestPart(required = false) MultipartFile profileImage) {
        final SignUpResponseDto response = authService.signUp(signUpRequestDto, profileImage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                                    HttpServletResponse response) {
        final LoginResponseDto loginResponseDto = authService.login(loginRequestDto, response);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponseDto> reissue(@RequestBody ReissueRequestDto reissueRequestDto) throws JsonProcessingException {
        final ReissueResponseDto reissueResponseDto = authService.reissue(reissueRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(reissueResponseDto);
    }


    @GetMapping("/check-duplicate/{moldevId}")
    public ResponseEntity<DuplicateMoldevIdResponseDto> checkId(@PathVariable("moldevId") String moldevId) {
        final DuplicateMoldevIdResponseDto response = authService.checkDuplicateMoldevId(moldevId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
