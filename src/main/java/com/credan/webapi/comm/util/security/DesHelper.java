package com.credan.webapi.comm.util.security;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.credan.webapi.comm.util.Base64Util;
import com.google.common.base.Charsets;

public final class DesHelper {

	private final static String SECRETKEYSPEC_ALGORITHM = "DES";

	private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

	private static byte[] iv = { (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0xAB,
			(byte) 0xCD, (byte) 0xEF };

	/**
	 * 加密
	 * 
	 * @param encryptString
	 *            原始数据
	 * @param desKey
	 *            Base64转码后的密钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String encryptString, String desKey) throws Exception {
		Key key = toKey(desKey);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes(Charsets.UTF_8));
		return Base64Util.encodeBase64String(encryptedData);
	}

	/**
	 * 
	 * @param decryptString
	 *            Base64转码后的密文
	 * @param desKey
	 *            Base64转码后的密钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String decryptString, String desKey) throws Exception {
		Key key = toKey(desKey);
		byte[] byteMi = Base64Util.decryptBASE64(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);
		return new String(decryptedData, Charsets.UTF_8);
	}

	/**
	 * 还原密钥
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(String encryptKey) throws Exception {
		byte[] key = Base64Util.decryptBASE64(encryptKey);
		DESKeySpec des = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRETKEYSPEC_ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(des);
		return secretKey;
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
		return  Base64Util.encodeBase64String(secretKey.getEncoded());
	}
	
//	public static void main(String[] args) throws Exception {
//		String initKey = initKey("122222");
//		System.err.println(initKey);
//		String stre = "abc";
//		String encrypt = encrypt(stre, initKey);
//		System.err.println(encrypt);
//		String decrypt = decrypt(encrypt, initKey);
//		System.err.println(decrypt);
//	}

}
