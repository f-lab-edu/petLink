package com.petlink.common.encrypt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class EncryptTest {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("문자열을 암호화한다.")
	void doEncrypt() {
		String password = "1234";
		String encryptPassword = passwordEncoder.encode(password);
		assertNotEquals(password, encryptPassword);
	}

	@Test
	@DisplayName("평문과 암호화된 문자열을 비교한다.")
	void isEquals() {
		String password = "1235";
		String encryptPassword = passwordEncoder.encode(password);
		assertNotEquals(password, encryptPassword);

		boolean match = passwordEncoder.matches(password, encryptPassword);
		assertTrue(match);
		boolean isNotMatch = passwordEncoder.matches("5678", encryptPassword);
		assertFalse(isNotMatch);
	}
}
