package com.credan.webapi.common.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class CredanSignUtil {
	public static String ALGORITHM = "RSA";
	
	public static String SIGN_ALGORITHMS = "SHA1WithRSA";
	
	public static String CHAR_SET = "UTF-8";
	
	/**
	 * 签名
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String sign(String content,String privateKey) throws Exception{
		PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey key = keyFactory.generatePrivate(encodedKeySpec);
		
		Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
		signature.initSign(key);
		signature.update(content.getBytes(CHAR_SET));
		
		byte[] signed = signature.sign();
		
		return Base64.getEncoder().encodeToString(signed);
	}
	
	

	
	
}
