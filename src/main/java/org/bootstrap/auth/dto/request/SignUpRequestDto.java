package org.bootstrap.auth.dto.request;

public record SignUpRequestDto(
    String email,
    String password,
    String moldevId,
    String nickname,
    String islandName,
    Boolean isMarketingAgree
) {
}
