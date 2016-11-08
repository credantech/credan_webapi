package com.credan.webapi.comm.util.security;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.credan.webapi.comm.util.Base64Util;
import com.google.common.base.Charsets;

public final class AESHelper {

	private final static String SECRETKEYSPEC_ALGORITHM = "AES";

	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	private static final Charset CHAR_SET = Charsets.UTF_8;

	public static String encrypt(String input, String key) throws Exception {
		byte[] crypted = null;
		SecretKeySpec skey = new SecretKeySpec(key.getBytes(CHAR_SET), SECRETKEYSPEC_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, skey);
		crypted = cipher.doFinal(input.getBytes());
		return Base64Util.encodeBase64String(crypted);
	}

	public static String decrypt(String input, String key) throws Exception {
		byte[] output = null;
		SecretKeySpec skey = new SecretKeySpec(key.getBytes(CHAR_SET), SECRETKEYSPEC_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, skey);
		output = cipher.doFinal(Base64Util.decryptBASE64(input));
		return new String(output, CHAR_SET);
	}

}
