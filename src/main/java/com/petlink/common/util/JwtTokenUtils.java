package com.petlink.common.util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenUtils {
	public static String createToken(String memberName, String secretKey, Long expireTime) {
		//토큰에 담을 정보
		Claims claims = Jwts.claims();
		claims.put("memberName", memberName);

		return Jwts.builder() //빌더를 활용 해 토큰 생성
			.setClaims(claims) //토큰에 담을 정보
			.setIssuedAt(new Date(System.currentTimeMillis()))//토큰 발행시간
			.setExpiration(new Date(System.currentTimeMillis() + expireTime))//토근 만료시간
			.signWith(getKey(secretKey), SignatureAlgorithm.HS256) //토큰 암호화
			.compact(); //토큰 생성
	}

	private static Key getKey(String secretKey) {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
}
