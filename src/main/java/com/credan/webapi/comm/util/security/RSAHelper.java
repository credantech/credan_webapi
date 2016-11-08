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

	public static final String ALGORITHM = "RSA";

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";// 摘要加密算饭

	public static final String CHAR_SET = "UTF-8";

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

}
