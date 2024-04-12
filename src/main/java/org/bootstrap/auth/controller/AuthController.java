package org.bootstrap.auth.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.common.SuccessCode;
import org.bootstrap.auth.common.SuccessResponse;
import org.bootstrap.auth.dto.request.LoginRequestDto;
import org.bootstrap.auth.dto.response.LoginResponseDto;
import org.bootstrap.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<?>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return SuccessResponse.ok(loginResponseDto);
    }
}
