package org.bootstrap.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.common.error.DuplicateException;
import org.bootstrap.auth.common.error.EntityNotFoundException;
import org.bootstrap.auth.common.error.GlobalErrorCode;
import org.bootstrap.auth.common.error.UnAuthenticationException;
import org.bootstrap.auth.dto.request.LoginRequestDto;
import org.bootstrap.auth.dto.request.SignUpRequestDto;
import org.bootstrap.auth.dto.response.LoginResponseDto;
import org.bootstrap.auth.dto.response.SignUpResponseDto;
import org.bootstrap.auth.entity.Member;
import org.bootstrap.auth.jwt.Token;
import org.bootstrap.auth.jwt.TokenProvider;
import org.bootstrap.auth.redis.entity.RefreshToken;
import org.bootstrap.auth.redis.repository.RefreshTokenRepository;
import org.bootstrap.auth.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto, MultipartFile profileImage) {
        validateDuplicateEmail(signUpRequestDto.email());
        String encodedPassword = encodePassword(signUpRequestDto.password());
        Member member = Member.of(signUpRequestDto, encodedPassword);
        memberRepository.save(member);

        // profileImage 저장 로직 구현

        return SignUpResponseDto.of(member.getId());
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Member member = getUserByEmail(loginRequestDto.email());
        validatePassword(loginRequestDto.password(), member.getPassword());
        Token token = generateToken(member.getId());
        saveRefreshToken(member.getId(), token.getRefreshToken());
        return LoginResponseDto.of(member.getId(), token);
    }

    private Member getUserByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(GlobalErrorCode.USER_NOT_FOUND));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validatePassword(String inputPassword, String encodedPassword) {
        if (!passwordEncoder.matches(inputPassword, encodedPassword)) {
            throw new UnAuthenticationException(GlobalErrorCode.WRONG_PASSWORD);
        }
    }

    private Token generateToken(Long userId) {
        return Token.of(tokenProvider.createAccessToken(userId), tokenProvider.createRefreshToken());
    }

    private void saveRefreshToken(Long userId, String newRefreshToken) {
        if (refreshTokenRepository.existsById(userId)) {
            RefreshToken originalRefreshToken = refreshTokenRepository.findById(userId).get();
            originalRefreshToken.update(newRefreshToken);
        }
        else {
            refreshTokenRepository.save(RefreshToken.of(userId, newRefreshToken));
        }
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateException(GlobalErrorCode.DUPLICATE_EMAIL);
        }
    }
}
