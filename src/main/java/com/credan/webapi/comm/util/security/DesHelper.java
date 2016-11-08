package com.credan.webapi.comm.util.security;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.credan.webapi.comm.util.Base64Util;


public final class DESHelper {

	private final static String SECRETKEYSPEC_ALGORITHM = "DES";

	private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

	private static final String CHAR_SET = "UTF-8";

	private static byte[] desEncrypt(byte plainText[], String desKey) throws Exception {
		DESKeySpec dks = new DESKeySpec(desKey.getBytes());

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRETKEYSPEC_ALGORITHM);
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		IvParameterSpec iv = new IvParameterSpec(desKey.getBytes());
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
		return cipher.doFinal(plainText);
	}

	private static byte[] desDecrypt(byte encryptText[], String desKey) throws Exception {
		DESKeySpec dks = new DESKeySpec(desKey.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRETKEYSPEC_ALGORITHM);
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		IvParameterSpec iv = new IvParameterSpec(desKey.getBytes());
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
		return cipher.doFinal(encryptText);
	}

	/**
	 * DES加密
	 * 
	 * @param input
	 * @param desKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String input, String desKey) throws Exception {
		return new String(Base64Util.encodeBase64String(desEncrypt(input.getBytes(CHAR_SET), desKey)));
	}

	/**
	 * DES解密
	 * 
	 * @param input
	 * @param desKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String input, String desKey) throws Exception {
		byte result[] = Base64Util.decryptBASE64(input);
		return new String(desDecrypt(result, desKey), CHAR_SET);
	}
	
	/**
	 * 初始化一个DESKey
	 * 
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;

		if (seed != null) {
			secureRandom = new SecureRandom(Base64Util.decryptBASE64(seed));
		} else {
			secureRandom = new SecureRandom();
		}
		KeyGenerator kg = KeyGenerator.getInstance(SECRETKEYSPEC_ALGORITHM);
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return Base64Util.encodeBase64String(secretKey.getEncoded());
	}
}
