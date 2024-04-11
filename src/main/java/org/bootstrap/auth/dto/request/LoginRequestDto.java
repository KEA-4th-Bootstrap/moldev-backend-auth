package org.bootstrap.auth.dto.request;

import lombok.Getter;

public record LoginRequestDto (
    String email,
    String password
) {
}
