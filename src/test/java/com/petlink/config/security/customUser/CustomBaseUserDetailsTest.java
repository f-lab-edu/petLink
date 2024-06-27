package com.petlink.config.security.customUser;

import com.petlink.manager.domain.Manager;
import com.petlink.member.domain.Member;
import com.petlink.member.domain.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomBaseUserDetailsTest {

    @Test
    @DisplayName("매니저 이메일일 경우 ROLE_MANAGER 권한을 가진 CustomUserDetails 객체를 생성한다.")
    public void testCustomUserDetails_WithManager() {
        String email = "manager@petlink.co.kr";
        Manager manager = Manager.builder()
                .email(email)
                .build();

        CustomUserDetails userDetails = CustomUserDetails.builder()
                .user(manager)
                .build();

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ROLE_MANAGER", authorities.iterator().next().getAuthority());
    }

    @Test
    @DisplayName("회원 이메일일 경우 ROLE_MEMBER 권한을 가진 CustomUserDetails 객체를 생성한다.")
    public void testCustomUserDetails_WithMember() {
        String email = "member@example.com";
        Member member = Member.builder()
                .email(email)
                .status(MemberStatus.ACTIVE)
                .build();

        CustomUserDetails userDetails = CustomUserDetails.builder()
                .user(member)
                .build();

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ROLE_MEMBER", authorities.iterator().next().getAuthority());
    }
}