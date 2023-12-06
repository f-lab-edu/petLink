package com.petlink.common.util.jwt;

import com.petlink.common.domain.user.BaseUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expire-length}")
    private Long expireLength;


    /**
     * 토큰 생성
     *
     * @param user
     * @return
     */
    public String createToken(BaseUser user) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("role", user.getRole().getRole());
        long systemTime = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(systemTime))
                .setExpiration(new Date(systemTime + expireLength))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 키 생성
     */
    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * 토큰 만료 여부 확인
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰에서 특정 정보 추출
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 토큰에서 사용자 이메일 추출
     */
    public String getEmailByToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public String getRoleByToken(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean isTokenValid(String token) {
        return (getEmailByToken(token) != null && !isTokenExpired(token));
    }

    /**
     * 토큰에서 사용자 정보 추출
     */
    public Long getIdByToken(String token) {
        return extractClaim(token, claims -> claims.get("id", Long.class));
    }

    /**
     * 토큰 만료일자 검사
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 토큰 만료일자 추출
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
