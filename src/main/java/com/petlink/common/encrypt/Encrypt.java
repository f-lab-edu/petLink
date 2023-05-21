package com.petlink.common.encrypt;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class Encrypt implements EncryptHelper {
	private static final String PEPPER = "glenfiddich"; //추가적인 암호화
	private static final int WORK_FACTOR = 12;      //암호화 강도

	@Override
	public String encrypt(String password) {
		return BCrypt.hashpw(password + PEPPER, BCrypt.gensalt(WORK_FACTOR));
	}

	@Override
	public boolean isMatch(String password, String encPassword) {
		return BCrypt.checkpw(password + PEPPER, encPassword);
	}
}