package org.bootstrap.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bootstrap.auth.aws.S3Service;
import org.bootstrap.auth.common.error.*;
import org.bootstrap.auth.dto.request.LoginRequestDto;
import org.bootstrap.auth.dto.request.SignUpRequestDto;
import org.bootstrap.auth.dto.response.BannedUserDetailResponseDto;
import org.bootstrap.auth.dto.response.DuplicateMoldevIdResponseDto;
import org.bootstrap.auth.dto.response.LoginResponseDto;
import org.bootstrap.auth.dto.response.SignUpResponseDto;
import org.bootstrap.auth.entity.Member;
import org.bootstrap.auth.jwt.Token;
import org.bootstrap.auth.jwt.TokenProvider;
import org.bootstrap.auth.redis.entity.RefreshToken;
import org.bootstrap.auth.redis.repository.RefreshTokenRepository;
import org.bootstrap.auth.repository.BanRepository;
import org.bootstrap.auth.repository.MemberRepository;
import org.bootstrap.auth.utils.CookieUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final BanRepository banRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    public static final String PROFILE_IMAGE_DIRECTORY = "profile";

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto, @Nullable MultipartFile profileImage) {
        validateDuplicateEmail(signUpRequestDto.email());
        validateDuplicateMoldevId(signUpRequestDto.moldevId());
        String encodedPassword = encodePassword(signUpRequestDto.password());

        String profileImgUrl = checkProfileImageAndGetUrl(profileImage, signUpRequestDto.moldevId());

        Member member = Member.of(signUpRequestDto, encodedPassword, profileImgUrl);
        memberRepository.save(member);

        return SignUpResponseDto.of(member.getId());
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = getUserByEmail(loginRequestDto.email());
        validatePassword(loginRequestDto.password(), member.getPassword());
        validateBan(member);
        Token token = generateToken(member.getId());
        saveRefreshToken(member.getId(), token.getRefreshToken());
        CookieUtils.addCookie(response, TokenProvider.REFRESH_TOKEN, token.getRefreshToken());
        return LoginResponseDto.of(member.getId(), token);
    }

    public DuplicateMoldevIdResponseDto checkDuplicateMoldevId(String moldevId) {
        if (memberRepository.existsByMoldevId(moldevId)) {
            return DuplicateMoldevIdResponseDto.of(true);
        }
        return DuplicateMoldevIdResponseDto.of(false);
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

    private void validateBan(Member member) {
        banRepository.findByMemberId(member.getId())
                .ifPresent(ban -> {
                    GlobalErrorCode bannedUserErorrCode = GlobalErrorCode.BANNED_USER;
                    BannedUserDetailResponseDto bannedUserDetailResponseDto = BannedUserDetailResponseDto.of(ban);
                    bannedUserErorrCode.setDetail(bannedUserDetailResponseDto);
                    throw new BannedUserException(bannedUserErorrCode);
                });
    }

    private Token generateToken(Long userId) {
        return Token.of(tokenProvider.createAccessToken(userId), tokenProvider.createRefreshToken());
    }

    private void saveRefreshToken(Long userId, String newRefreshToken) {
        if (refreshTokenRepository.existsById(userId)) {
            RefreshToken originalRefreshToken = refreshTokenRepository.findById(userId).get();
            originalRefreshToken.update(newRefreshToken);
        } else {
            refreshTokenRepository.save(RefreshToken.of(userId, newRefreshToken));
        }
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateException(GlobalErrorCode.DUPLICATE_EMAIL);
        }
    }

    private void validateDuplicateMoldevId(String moldevId){
        if(memberRepository.existsByMoldevId(moldevId)){
            throw new DuplicateException(GlobalErrorCode.DUPLICATE_MOLDEVID);
        }
    }

    private String checkProfileImageAndGetUrl(MultipartFile profileImage, String moldevId) {
        if (Objects.isNull(profileImage))
            return getDefaultProfileImgUrl();
        else
            return uploadProfileImage(profileImage, moldevId);

    }

    private String uploadProfileImage(MultipartFile profileImage, String moldevId) {
        String imageFileName = generateProfileImageFileName(Objects.requireNonNull(profileImage.getOriginalFilename()), moldevId);
        return s3Service.uploadFile(profileImage, imageFileName, PROFILE_IMAGE_DIRECTORY);
    }

    private String getDefaultProfileImgUrl() {
        return s3Service.getFileUrl(PROFILE_IMAGE_DIRECTORY + "/default.png");
    }

    private String generateProfileImageFileName(String originalFileName, String moldevId) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return moldevId + extension;
    }

}
