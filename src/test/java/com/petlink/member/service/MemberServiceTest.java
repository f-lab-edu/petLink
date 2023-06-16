package com.petlink.member.service;

import com.petlink.common.exception.TokenException;
import com.petlink.common.util.jwt.JwtTokenProvider;
import com.petlink.member.domain.Member;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.exception.MemberException;
import com.petlink.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Test
    @DisplayName("회원가입을 진행할 수 있다.")
    void testSignUp() {
        // Given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .name("TestName")
                .email("test@example.com")
                .password("password")
                .tel("1234567890")
                .zipCode("12345")
                .address("TestAddress")
                .detailAddress("TestDetailAddress")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(memberRepository.existsByEmail(anyString())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        MemberInfoResponseDto result = memberService.signUp(signUpRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(signUpRequestDto.getName(), result.getName());
        assertEquals(signUpRequestDto.getEmail(), result.getEmail());
        verify(memberRepository, times(1)).existsByEmail(signUpRequestDto.getEmail());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 회원가입을 진행할 수 없다.")
    void testSignUpWithAlreadyRegisteredEmail() {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .name("TestName")
                .email("test@example.com")
                .password("password")
                .tel("1234567890")
                .zipCode("12345")
                .address("TestAddress")
                .detailAddress("TestDetailAddress")
                .build();

        when(memberRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(MemberException.class, () -> memberService.signUp(signUpRequestDto));
        verify(memberRepository, times(1)).existsByEmail(signUpRequestDto.getEmail());
    }

    @Test
    @DisplayName("이미 존재하는 이름으로 회원가입을 진행할 수 없다.")
    void testSignUpWithAlreadyUsedName() {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .name("TestName")
                .email("test@example.com")
                .password("password")
                .tel("1234567890")
                .zipCode("12345")
                .address("TestAddress")
                .detailAddress("TestDetailAddress")
                .build();

        when(memberRepository.existsByName(anyString())).thenReturn(true);
        assertThrows(MemberException.class, () -> memberService.signUp(signUpRequestDto));
        verify(memberRepository, times(1)).existsByEmail(signUpRequestDto.getEmail());
    }

    @Test
    @DisplayName("비밀번호가 제대로 인코딩되는지 확인")
    void testPasswordEncoding() {
        // Given
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .name("TestName")
                .email("test@example.com")
                .password(rawPassword)
                .tel("1234567890")
                .zipCode("12345")
                .address("TestAddress")
                .detailAddress("TestDetailAddress")
                .build();
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        // When
        memberService.signUp(signUpRequestDto);

        // Then
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, times(1)).save(memberCaptor.capture());
        Member savedMember = memberCaptor.getValue();
        assertEquals(encodedPassword, savedMember.getPassword());
    }

    @Test
    @DisplayName("이름이 중복되는 경우")
    void testNameDuplicated() {
        // Given
        String testName = "TestName";

        when(memberRepository.existsByName(testName)).thenReturn(true);

        // When
        Boolean isDuplicated = memberService.isNameDuplicated(testName);

        // Then
        assertTrue(isDuplicated);
        verify(memberRepository, times(1)).existsByName(testName);
    }

    @Test
    @DisplayName("이름이 중복되지 않는 경우")
    void testNameNotDuplicated() {
        // Given
        String testName = "TestName";

        when(memberRepository.existsByName(testName)).thenReturn(false);

        // When
        Boolean isDuplicated = memberService.isNameDuplicated(testName);

        // Then
        assertFalse(isDuplicated);
        verify(memberRepository, times(1)).existsByName(testName);
    }

    @Test
    @DisplayName("유효한 토큰이 제공되고 해당 사용자가 존재하는 경우")
    void testWithdrawalWithValidTokenAndExistingUser() {
        // Given
        String testToken = "testToken";
        Long userId = 1L;
        Member member = mock(Member.class);

        when(tokenProvider.isTokenValid(testToken)).thenReturn(false);
        when(tokenProvider.getIdByToken(testToken)).thenReturn(userId);
        when(memberRepository.findById(userId)).thenReturn(Optional.of(member));

        // When
        memberService.withdrawal(testToken);

        // Then
        verify(member, times(1)).withdrawal();
    }

    @Test
    @DisplayName("유효하지 않은 토큰이 제공되는 경우")
    void testWithdrawalWithInvalidToken() {
        // Given
        String testToken = "testToken";

        when(tokenProvider.isTokenValid(testToken)).thenReturn(true);

        // When
        assertThrows(TokenException.class, () -> memberService.withdrawal(testToken));
    }

    @Test
    @DisplayName("유효한 토큰이 제공되었지만 해당 사용자가 존재하지 않는 경우")
    void testWithdrawalWithValidTokenAndNonExistingUser() {
        // Given
        String testToken = "testToken";
        Long userId = 1L;

        when(tokenProvider.isTokenValid(testToken)).thenReturn(false);
        when(tokenProvider.getIdByToken(testToken)).thenReturn(userId);
        when(memberRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        assertThrows(MemberException.class, () -> memberService.withdrawal(testToken));
    }

    @Test
    @DisplayName("토큰으로부터 얻은 사용자 ID가 null인 경우")
    void testWithdrawalWithNullUserId() {
        // Given
        String testToken = "testToken";

        when(tokenProvider.isTokenValid(testToken)).thenReturn(false);
        when(tokenProvider.getIdByToken(testToken)).thenReturn(null);

        // When
        assertThrows(MemberException.class, () -> memberService.withdrawal(testToken));
    }

    @Test
    @DisplayName("Member 객체의 withdrawal 메소드가 실패하는 경우")
    void testWithdrawalFailure() {
        // Given
        String testToken = "testToken";
        Long userId = 1L;
        Member member = mock(Member.class);

        when(tokenProvider.isTokenValid(testToken)).thenReturn(false);
        when(tokenProvider.getIdByToken(testToken)).thenReturn(userId);
        when(memberRepository.findById(userId)).thenReturn(Optional.of(member));
        doThrow(new RuntimeException()).when(member).withdrawal();

        // When
        assertThrows(RuntimeException.class, () -> memberService.withdrawal(testToken));
    }

}
