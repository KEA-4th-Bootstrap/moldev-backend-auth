package org.bootstrap.auth.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
