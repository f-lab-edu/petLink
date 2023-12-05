package com.petlink.config.security.customUser;

import com.petlink.manager.repository.ManagerRepository;
import com.petlink.member.domain.Member;
import com.petlink.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomBaseUserDetailsServiceTest {

    private MemberRepository memberRepository;
    private ManagerRepository managerRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    public void setup() {
        memberRepository = mock(MemberRepository.class);
        managerRepository = mock(ManagerRepository.class);
        userDetailsService = new CustomUserDetailsService(memberRepository, managerRepository);
    }

    @Test
    @DisplayName("회원이 존재하는 경우 Member 객체를 반환한다.")
    public void testLoadUserByUsername_MemberExists() {
        String email = "member@example.com";
        Member member = Member.builder()
                .email(email)
                .name("물범")
                .build();
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());

        verify(managerRepository, never()).findByEmail(anyString());
    }


    @Test
    @DisplayName("이메일이 @petlink.co.kr로 끝나지 않는 경우 Member 객체를 반환한다.")
    public void testLoadUserByUsername_NotManagerExists() {
        String email = "member@notpetlink.co.kr";
        Member member = Member.builder()
                .email(email)
                .name("물범")
                .build();
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());

        verify(managerRepository, never()).findByEmail(anyString());
    }

    @Test
    @DisplayName("이메일이 null인 경우 UsernameNotFoundException을 던진다.")
    public void testLoadUserByUsername_NullEmail() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(null);
        });
    }

    @Test
    @DisplayName("이메일이 비어있는 문자열인 경우 UsernameNotFoundException을 던진다.")
    public void testLoadUserByUsername_EmptyEmail() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("");
        });
    }


}
