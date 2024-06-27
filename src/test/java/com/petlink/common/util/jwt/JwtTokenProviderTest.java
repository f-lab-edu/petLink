package com.petlink.common.util.jwt;

import com.petlink.common.domain.Address;
import com.petlink.member.domain.Member;
import com.petlink.member.domain.MemberStatus;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;

import static org.assertj.core.api.Assertions.assertThat;


class JwtTokenProviderTest {
    JwtTokenProvider provider = new JwtTokenProvider();
    Member member = Member.builder()
            .id(1L)
            .name("홍길동")
            .email("test@email.com")
            .password("1234")
            .tel("010-1234-5678")
            .status(MemberStatus.ACTIVE)
            .address(new Address("서울", "강남구", "12345"))
            .build();


    @BeforeEach
    void setUp() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generate a random key
        String secretString = Encoders.BASE64.encode(key.getEncoded());

        ReflectionTestUtils.setField(provider, "secretKey", "20231206:1252:1511:1113211:312321:11221");
        ReflectionTestUtils.setField(provider, "expireLength", 86400000L);

    }

    @Test
    @DisplayName("토큰을 생성할 수 있다.")
    void createToken() {
        String token = provider.createToken(member);
        System.out.println(token);
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("토큰에서 이메일을 추출할 수 있다.")
    void getEmailByTokenTest() {
        String token = provider.createToken(member);
        String email = provider.getEmailByToken(token);
        assertThat(email).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("토큰에서 권한을 추출할 수 있다.")
    void getRoleByTokenTest() {
        ReflectionTestUtils.setField(member, "role", UserRole.MEMBER);
        String token = provider.createToken(member);
        String role = provider.getRoleByToken(token);
        assertThat(role).isEqualTo(member.getRole().getRole());
    }

    @Test
    @DisplayName("토근의 유효성을 확인할 수 있다.")
    void isTokenExpiredTest() {
        String token = provider.createToken(member);
        boolean tokenValid = provider.isTokenValid(token);
        assertThat(tokenValid).isTrue();
    }
}