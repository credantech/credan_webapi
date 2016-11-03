package com.credan.webapi.comm.util.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.credan.webapi.comm.util.Base64Util;
import com.google.common.base.Charsets;

public class RSAHelper {

	public static String ALGORITHM = "RSA";

	public static String SIGN_ALGORITHMS = "SHA1WithRSA";// 摘要加密算饭

	public static String CHAR_SET = "UTF-8";

	/**
	 * 数据签名
	 * 
	 * @param content 签名内容
	 * @param privateKey 私钥
	 * @return 返回签名数据
	 */
	public static String sign(String content, String privateKey) throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.decryptBASE64(privateKey));
		KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);

		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

		signature.initSign(priKey);
		signature.update(content.getBytes(CHAR_SET));

		byte[] signed = signature.sign();

		return Base64Util.encodeBase64String(signed);
	}

	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 * 
	 * @param data 已加密数据
	 * @param publicKey 公钥(BASE64编码)
	 * @param sign 数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean verify(String data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = Base64Util.decryptBASE64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
		signature.initVerify(publicK);
		signature.update(data.getBytes(Charsets.UTF_8));
		return signature.verify(Base64Util.decryptBASE64(sign));
	}

	public static void main(String[] args) throws Exception {
		String pri = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKXbCq1bvzKSlAMeDMTdK8bSC/5dgEqomYnrJ0r3eSQe1jh/FV+Y8OlrJkBlRIOu+nNuDkTYMN74v9ndqdX5kegEnJusdPdDvOlZlhtbNt6mKF2te38VK0+WSGdBDgRGdqrds7ZGmBBdoxEwNrgPawAYFWWiwhjhY7QR636V7hQ9AgMBAAECgYA8YWEiubsCu1A9TkxRwz8zs7aAFSQmifjTuBNm/G9S0hMlIEb0taZHJCYZ3YQy+JnfOhrhv18TXbXiqAszhhcyraqPGpLq/ZaXt/GzXX2A1axcJ7Cf3rXgWnOzB1t0wwyLRH8c216Aio13fD+/OJOJe+b/KYPfTIis8w9D4ZnEYQJBAM51JLplXDqr345CJgpyRsm76HdwPY3GtDvnkuUAqJrGHyREGsDn6PKyUSGjkwgMIn1HuA9tZ9g/VJY+0SfMrSkCQQDNp6/i/Ek8ycds3rCksnTenEVxIoMP8+FA58WYS1TiUm6ivRJ6LpgBAYf0UROqJ0u9ioJniCR45Gh0uufa2Pz1AkEAuOdTcJjtBCMAgkEeHscAPRbM+YONrVdj/i7FcdGoL433QBy6tUA+cnZfkTgVq+RqUzeTUkXS9mqp0DIMUo/OGQJBAJfFv0p3Ad241FKGbjq98P4wuhbqb2sggwaRhgQE5Fo6l6fGfZJqPIfRh3JJO4gBhCYXyHF7yd7mQiiTn3SlWtkCQBumUcrlHFP6buDazWP/PNioWA4cNmCgM3yTSiwgxQ4aXWdMQQ/AXFTULuNus8UQSvfCpxpGfGrq6AFehRX1oz4=";
		String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCl2wqtW78ykpQDHgzE3SvG0gv+XYBKqJmJ6ydK93kkHtY4fxVfmPDpayZAZUSDrvpzbg5E2DDe+L/Z3anV+ZHoBJybrHT3Q7zpWZYbWzbepihdrXt/FStPlkhnQQ4ERnaq3bO2RpgQXaMRMDa4D2sAGBVlosIY4WO0Eet+le4UPQIDAQAB";
		String raw = "{\"itemName\":\"***\",\"orderId\":\"\",\"itemAmt\":1,\"tenorApplied\":\"\",\"itemPrice\":200.37}";
		String initKey = "12345678";// DESHelper.initKey("123456");
		System.err.println("initKey ： " + initKey);
		String encodeBase64String = DESHelper.encrypt(raw, initKey);
		System.err.println("encodeBase64String : " + encodeBase64String);
		String sign = sign(encodeBase64String, pri);
		System.err.println("sign : " + sign);
		boolean verify = verify(encodeBase64String, sign, pub);
		System.err.println("verify : " + verify);
		String desDecrypt = DESHelper.decrypt(encodeBase64String, initKey);
		System.err.println("desDecrypt : " + desDecrypt);
	}

	//
	//
	// /** 指定key的大小 */
	// private static int KEYSIZE = 1024;
	//
	// /**
	// * 加密算法RSA
	// */
	// public static final String KEY_ALGORITHM = "RSA";
	//
	// /**
	// * 签名算法
	// */
	// public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
	//
	// /**
	// * 获取公钥的key
	// */
	// private static final String PUBLIC_KEY = "RSAPublicKey";
	//
	// /**
	// * 获取私钥的key
	// */
	// private static final String PRIVATE_KEY = "RSAPrivateKey";
	//
	// /**
	// * RSA最大加密明文大小
	// */
	// private static final int MAX_ENCRYPT_BLOCK = 117;
	//
	// /**
	// * RSA最大解密密文大小
	// */
	// private static final int MAX_DECRYPT_BLOCK = 128;
	//
	// /**
	// * <p>
	// * 生成密钥对(公钥和私钥)
	// * </p>
	// *
	// * @return
	// * @throws Exception
	// */
	// public static Map<String, Object> genKeyPair(String seed) throws Exception {
	// /** RSA算法要求有一个可信任的随机数源 */
	// SecureRandom sr = new SecureRandom();
	// sr.setSeed(seed.getBytes(Charsets.UTF_8));
	// KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
	// /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
	// keyPairGen.initialize(KEYSIZE, sr);
	//
	// KeyPair keyPair = keyPairGen.generateKeyPair();
	// RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
	// RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
	// Map<String, Object> keyMap = new HashMap<String, Object>(2);
	// keyMap.put(PUBLIC_KEY, publicKey);
	// keyMap.put(PRIVATE_KEY, privateKey);
	// return keyMap;
	// }
	//
	// /**
	// * <p>
	// * 用私钥对信息生成数字签名
	// * </p>
	// *
	// * @param data
	// * 已加密数据
	// * @param privateKey
	// * 私钥(BASE64编码)
	// *
	// * @return
	// * @throws Exception
	// */
	// public static String sign(byte[] data, String privateKey) throws Exception {
	// byte[] keyBytes = Base64Util.decryptBASE64(privateKey);
	// PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	// PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
	// Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	// signature.initSign(privateK);
	// signature.update(data);
	// return Base64Util.encodeBase64String(signature.sign());
	// }
	//
	// public static String sign(String data, String privateKey) throws Exception {
	//
	// // 解密由base64编码的私钥
	// byte[] keyBytes = Base64Util.decryptBASE64(privateKey);
	//
	// // 构造PKCS8EncodedKeySpec对象
	// PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	//
	// // KEY_ALGORITHM 指定的加密算法
	// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	//
	// // 取私钥匙对象
	// PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
	//
	// // 用私钥对信息生成数字签名
	// Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	// signature.initSign(priKey);
	// signature.update(data.getBytes(Charsets.UTF_8));
	// return Base64Util.encodeBase64String(signature.sign());
	// }
	//
	// /**
	// * <p>
	// * 校验数字签名
	// * </p>
	// *
	// * @param data
	// * 已加密数据
	// * @param publicKey
	// * 公钥(BASE64编码)
	// * @param sign
	// * 数字签名
	// *
	// * @return
	// * @throws Exception
	// *
	// */
	// public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
	// byte[] keyBytes = Base64Util.decryptBASE64(publicKey);
	// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
	// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	// PublicKey publicK = keyFactory.generatePublic(keySpec);
	// Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	// signature.initVerify(publicK);
	// signature.update(data);
	// return signature.verify(Base64Util.decryptBASE64(sign));
	// }
	//
	// /**
	// * <P>
	// * 私钥解密
	// * </p>
	// *
	// * @param encryptedData
	// * 已加密数据
	// * @param privateKey
	// * 私钥(BASE64编码)
	// * @return
	// * @throws Exception
	// */
	// public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
	// byte[] keyBytes = Base64Util.decryptBASE64(privateKey);
	// PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	// Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
	// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	// cipher.init(Cipher.DECRYPT_MODE, privateK);
	// int inputLen = encryptedData.length;
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// int offSet = 0;
	// byte[] cache;
	// int i = 0;
	// // 对数据分段解密
	// while (inputLen - offSet > 0) {
	// if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
	// cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
	// } else {
	// cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
	// }
	// out.write(cache, 0, cache.length);
	// i++;
	// offSet = i * MAX_DECRYPT_BLOCK;
	// }
	// byte[] decryptedData = out.toByteArray();
	// out.close();
	// return decryptedData;
	// }
	//
	// /**
	// * <p>
	// * 公钥解密
	// * </p>
	// *
	// * @param encryptedData
	// * 已加密数据
	// * @param publicKey
	// * 公钥(BASE64编码)
	// * @return
	// * @throws Exception
	// */
	// public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
	// byte[] keyBytes = Base64Util.decryptBASE64(publicKey);
	// X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	// Key publicK = keyFactory.generatePublic(x509KeySpec);
	// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	// cipher.init(Cipher.DECRYPT_MODE, publicK);
	// int inputLen = encryptedData.length;
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// int offSet = 0;
	// byte[] cache;
	// int i = 0;
	// // 对数据分段解密
	// while (inputLen - offSet > 0) {
	// if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
	// cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
	// } else {
	// cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
	// }
	// out.write(cache, 0, cache.length);
	// i++;
	// offSet = i * MAX_DECRYPT_BLOCK;
	// }
	// byte[] decryptedData = out.toByteArray();
	// out.close();
	// return decryptedData;
	// }
	//
	// /**
	// * <p>
	// * 公钥加密
	// * </p>
	// *
	// * @param data
	// * 源数据
	// * @param publicKey
	// * 公钥(BASE64编码)
	// * @return
	// * @throws Exception
	// */
	// public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
	// byte[] keyBytes = Base64Util.decryptBASE64(publicKey);
	// X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	// Key publicK = keyFactory.generatePublic(x509KeySpec);
	// // 对数据加密
	// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	// cipher.init(Cipher.ENCRYPT_MODE, publicK);
	// int inputLen = data.length;
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// int offSet = 0;
	// byte[] cache;
	// int i = 0;
	// // 对数据分段加密
	// while (inputLen - offSet > 0) {
	// if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	// cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
	// } else {
	// cache = cipher.doFinal(data, offSet, inputLen - offSet);
	// }
	// out.write(cache, 0, cache.length);
	// i++;
	// offSet = i * MAX_ENCRYPT_BLOCK;
	// }
	// byte[] encryptedData = out.toByteArray();
	// out.close();
	// return encryptedData;
	// }
	//
	// /**
	// * <p>
	// * 私钥加密
	// * </p>
	// *
	// * @param data
	// * 源数据
	// * @param privateKey
	// * 私钥(BASE64编码)
	// * @return
	// * @throws Exception
	// */
	// public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
	// byte[] keyBytes = Base64Util.decryptBASE64(privateKey);
	// PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	// Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
	// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	// cipher.init(Cipher.ENCRYPT_MODE, privateK);
	// int inputLen = data.length;
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// int offSet = 0;
	// byte[] cache;
	// int i = 0;
	// // 对数据分段加密
	// while (inputLen - offSet > 0) {
	// if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	// cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
	// } else {
	// cache = cipher.doFinal(data, offSet, inputLen - offSet);
	// }
	// out.write(cache, 0, cache.length);
	// i++;
	// offSet = i * MAX_ENCRYPT_BLOCK;
	// }
	// byte[] encryptedData = out.toByteArray();
	// out.close();
	// return encryptedData;
	// }
	//
	// /**
	// * <p>
	// * 获取私钥
	// * </p>
	// *
	// * @param keyMap
	// * 密钥对
	// * @return
	// * @throws Exception
	// */
	// public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
	// Key key = (Key) keyMap.get(PRIVATE_KEY);
	// return Base64Util.encodeBase64String(key.getEncoded());
	// }
	//
	// /**
	// * <p>
	// * 获取公钥
	// * </p>
	// *
	// * @param keyMap
	// * 密钥对
	// * @return
	// * @throws Exception
	// */
	// public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
	// Key key = (Key) keyMap.get(PUBLIC_KEY);
	// return Base64Util.encodeBase64String(key.getEncoded());
	// }
	//
	// public static void main(String[] args) throws Exception {
	// Map<String, Object> genKeyPair = genKeyPair("123456");
	// String privateKey = getPrivateKey(genKeyPair);
	// System.err.println(privateKey);
	// String publicKey = getPublicKey(genKeyPair);
	// System.err.println("publicKey : " +publicKey);
	// String a = "abcdfrr";
	// String initKey = DESHelper.initKey("123456");
	// System.err.println("initKey : " + initKey);
	//// String encrypt = DESHelper.encrypt(a, initKey);
	//// System.err.println("encrypt : " + encrypt);
	//// String sign = sign(encrypt, privateKey);
	//// System.err.println("sign : " + sign);
	//// boolean verify = verify(encrypt.getBytes(), publicKey, sign);
	//// System.err.println("verify : " + verify);
	//// String decrypt = DESHelper.decrypt(encrypt, initKey);
	//// System.err.println("decrypt : " + decrypt);
	// }

}
