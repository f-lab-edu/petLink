package com.petlink.common.encrypt;

public interface EncryptHelper {
	String encrypt(String password);

	boolean isMatch(String password, String encStr);
}
