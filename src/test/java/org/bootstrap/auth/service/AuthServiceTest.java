package org.bootstrap.auth.service;

import jakarta.transaction.Transactional;
import org.bootstrap.auth.common.error.DuplicateException;
import org.bootstrap.auth.dto.request.SignUpRequestDto;
import org.bootstrap.auth.entity.Member;
import org.bootstrap.auth.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void signUp_Fail_DuplicateEmail() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("test@test.com", "password", "moldevId", "nickname", "islandName");
        MultipartFile profileImage = null;

        when(memberRepository.existsByEmail(signUpRequestDto.email())).thenThrow(DuplicateException.class);

        assertThrows(DuplicateException.class, () -> authService.signUp(signUpRequestDto, profileImage));
        verify(memberRepository, times(0)).save(any(Member.class));
    }

}