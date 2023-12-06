package com.petlink.common.cache;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 토큰 캐시 서비스
 * 일정의 블랙리스트
 * 목적은 별도의 캐시 테이블을 두는게 올바르지만
 * 해당 프로젝트의 종료를 앞두고 있어서
 * 기능의 구현 예를 보여주기 위해 생성
 */
@Component
@RequiredArgsConstructor
public class TokenCacheService {

    private static Map<String, LocalDate> tokenBlackList = new HashMap<>();

    public void addBlackList(String token) {
        tokenBlackList.put(token, LocalDate.now());
    }

    public boolean isBlackList(String token) {
        return tokenBlackList.containsKey(token);
    }

    public void removeBlackList(String token) {
        tokenBlackList.remove(token);
    }
}
