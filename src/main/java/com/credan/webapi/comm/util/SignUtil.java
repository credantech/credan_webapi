package com.credan.webapi.comm.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class SignUtil {
	public static String ALGORITHM = "RSA";

	public static String SIGN_ALGORITHMS = "SHA1WithRSA";

	public static String CHAR_SET = "UTF-8";

	/**
	 * 签名
	 * 
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String sign(String content, String privateKey) throws Exception {
		PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey key = keyFactory.generatePrivate(encodedKeySpec);

		Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
		signature.initSign(key);
		signature.update(content.getBytes(CHAR_SET));

		byte[] signed = signature.sign();

		return Base64.getEncoder().encodeToString(signed);
	}

	/**
	 * 验签
	 * 
	 * @param content
	 * @param sign
	 * @param merchantPublicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean checkSign(String content, String sign, String merchantPublicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		byte[] encodedKey = Base64.getDecoder().decode(merchantPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

		signature.initVerify(publicKey);
		signature.update(content.getBytes(CHAR_SET));

		return signature.verify(Base64.getDecoder().decode(sign));
	}

	/**
	 * des解密
	 * 
	 * @param data
	 * @param desKey
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(String data, String desKey) throws Exception {
		byte result[] = decryptBASE64(data);
		return new String(desDecrypt(result, desKey), "UTF-8");
	}

	public static byte[] decryptBASE64(String data) {
		return Base64.getDecoder().decode(data);
	}

	/**
	 * base64编码解密参数
	 * 
	 * @param encryptText
	 * @param desKey
	 * @return
	 * @throws Exception
	 */
	private static byte[] desDecrypt(byte encryptText[], String desKey) throws Exception {
		DESKeySpec dks = new DESKeySpec(desKey.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(desKey.getBytes());
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
		return cipher.doFinal(encryptText);
	}

	private static byte[] desEncrypt(byte plainText[], String desKey) throws Exception {
		DESKeySpec dks = new DESKeySpec(desKey.getBytes());

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(desKey.getBytes());
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

		return cipher.doFinal(plainText);
	}

	public static String encrypt(String input, String desKey) throws Exception {
		return new String(Base64.getEncoder().encodeToString(desEncrypt(input.getBytes("UTF-8"), desKey)));
	}

	public static void main(String[] args) throws Exception {

		/**
		 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
		 * 
		 * @param arrBTmp
		 *            构成该字符串的字节数组
		 * @return 生成的密钥
		 * @throws java.lang.Exception
		 * credan:vGu6jJKSxG0=
		 * zhaoliangji:FY8qLClGpOM=

		 */
		String seed = "zhaoliangji";
		String desKey = initKey(seed);
		System.err.println(desKey);

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
			secureRandom = new SecureRandom(Base64.getDecoder().decode(seed));
		} else {
			secureRandom = new SecureRandom();
		}
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return Base64.getEncoder().encodeToString(secretKey.getEncoded());
	}
	
	 private static Key getKey(byte[] arrBTmp) throws Exception {
		  // 创建一个空的8位字节数组（默认值为0）
		  byte[] arrB = new byte[8];
		 
		  // 将原始字节数组转换为8位
		  for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
		   arrB[i] = arrBTmp[i];
		  }
		 
		  // 生成密钥
		  Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		 
		  return key;
		 }

}
