package com.petlink.common.util.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.petlink.member.domain.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.expire-length}")
	private Long expireLength;

	/** 토큰 생성
	 */
	public String createToken(Member member) {
		Claims claims = Jwts.claims();
		claims.put("id", member.getId());
		claims.put("role", JwtRole.MEMBER);
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(member.getEmail())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expireLength))
			.signWith(getKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	/** 키 생성
	 */
	private Key getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	/** 토큰 만료 여부 확인
	 */
	private Claims extractAllClaims(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	/** 토큰에서 특정 정보 추출
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/** 토큰에서 사용자 이메일 추출
	 */
	public String getEmailByToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/** 토큰 유효성 검사
	 */
	public boolean isTokenValid(String token) {
		return (getEmailByToken(token) != null && !isTokenExpired(token));
	}

	/** 토큰 만료일자 검사
	 */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/** 토큰 만료일자 추출
	 */
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
