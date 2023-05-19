package com.petlink.common.encrypt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EncryptTest {

	@Autowired
	private EncryptHelper encryptHelper;

	@Test
	@DisplayName("암호화 테스트")
	void doEncrypt() {
		String password = "1234";
		String encryptPassword = encryptHelper.encrypt(password);
		assertNotEquals(password, encryptPassword);
	}

	@Test
	@DisplayName("암호화 비교 검증 테스트")
	void isEquals() {
		String password = "1235";
		String encryptPassword = encryptHelper.encrypt(password);
		assertNotEquals(password, encryptPassword);

		boolean match = encryptHelper.isMatch(password, encryptPassword);
		assertTrue(match);
		boolean isNotMatch = encryptHelper.isMatch("5678", encryptPassword);
		assertFalse(isNotMatch);
	}
}
