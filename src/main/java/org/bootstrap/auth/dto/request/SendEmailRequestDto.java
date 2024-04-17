package org.bootstrap.auth.dto.request;


import jakarta.validation.constraints.Email;

public record SendEmailRequestDto(
    @Email String email
) {
}
