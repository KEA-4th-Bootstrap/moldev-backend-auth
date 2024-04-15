package org.bootstrap.auth.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.common.SuccessResponse;
import org.bootstrap.auth.dto.request.LoginRequestDto;
import org.bootstrap.auth.dto.request.SignUpRequestDto;
import org.bootstrap.auth.dto.response.LoginResponseDto;
import org.bootstrap.auth.dto.response.SignUpResponseDto;
import org.bootstrap.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

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
