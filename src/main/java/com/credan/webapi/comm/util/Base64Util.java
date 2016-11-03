package com.credan.webapi.comm.util;

public class Base64Util {
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return java.util.Base64.getDecoder().decode(key);
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64Mime(String key) throws Exception {
		return java.util.Base64.getMimeDecoder().decode(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64String(byte[] key) throws Exception {
		return java.util.Base64.getEncoder().encodeToString(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64StringMime(byte[] key) throws Exception {
		return java.util.Base64.getMimeEncoder().encodeToString(key);
	}

}
