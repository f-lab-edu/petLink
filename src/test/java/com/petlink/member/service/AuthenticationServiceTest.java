package com.petlink.member.service;

import com.petlink.common.util.jwt.JwtTokenProvider;
import com.petlink.member.domain.Member;
import com.petlink.member.exception.MemberException;
import com.petlink.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthenticationServiceTest 테스트")
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("회원은 로그인 할 수 있다.")
    void login() {
        // given
        String email = "io@gmail.com";
        String password = "password1234";
        String token = "fakeToken";
        Member member = Member.builder()
                .email(email)
                .password(password)
                .build();

        // when
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenProvider.createToken(member)).thenReturn(token);

        // then
        assertEquals(token, authenticationService.login(email, password));
    }

    @Test
    @DisplayName("로그인 시, 존재하지 않는 이메일로 로그인을 시도하면 NotFoundMemberException이 발생한다.")
    void login_with_non_existing_email() {
        // given
        String email = "non_existing@gmail.com";
        String password = "password1234";

        // when
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

        // then
        assertThrows(MemberException.class,
                () -> authenticationService.login(email, password));
    }

    @Test
    @DisplayName("로그인 시, 비밀번호가 일치하지 않으면 NotMatchedPasswordException이 발생한다.")
    void login_with_incorrect_password() {
        // given
        String email = "io@gmail.com";
        String password = "password1234";
        Member member = Member.builder()
                .email(email)
                .password(password)
                .build();

        // when
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // then
        assertThrows(MemberException.class, () -> authenticationService.login(email, password));
    }
}
