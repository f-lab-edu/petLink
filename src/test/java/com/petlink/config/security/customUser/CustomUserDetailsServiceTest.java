package com.petlink.config.security.customUser;

import com.petlink.manager.domain.Manager;
import com.petlink.manager.repository.ManagerRepository;
import com.petlink.member.domain.Member;
import com.petlink.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

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
    @DisplayName("이메일이 @petlink.co.kr로 끝나는 경우 Manager 객체를 반환한다.")
    public void testLoadUserByUsername_ManagerExists() {
        String email = "manager@petlink.co.kr";

        Manager manager = Manager.builder()
                .email(email)
                .name("매니저")
                .build();
        when(managerRepository.findByEmail(email)).thenReturn(Optional.of(manager));
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        verify(memberRepository, never()).findByEmail(anyString());
    }

    @Test
    @DisplayName("멤버와 매니저가 존재하지 않는 경우 UsernameNotFoundException을 던진다.")
    public void testLoadUserByUsername_UserNotFound() {
        String email = "nonexistent@example.com";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(managerRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(email);
        });

        verify(memberRepository).findByEmail(email);
        verify(managerRepository, never()).findByEmail(email);
    }

}
