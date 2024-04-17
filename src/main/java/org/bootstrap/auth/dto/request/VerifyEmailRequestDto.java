package org.bootstrap.auth.dto.request;

import jakarta.validation.constraints.Email;

public record VerifyEmailRequestDto(
    @Email String email,
    String code
) {
}
