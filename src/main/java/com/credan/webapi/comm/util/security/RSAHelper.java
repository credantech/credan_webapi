package com.credan.webapi.comm.util.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.asn1.ASN1Sequence;

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
		String credan_private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOBT8S8KOq6aft36Ly4EiuQH9moml+F88DQd08quJ93zA5jpW4jllBWo024q/g5Hlng7vO0BSz7l+ru8vzHaVYxX+J7hQ9VJkAuZF32eyIdD2j6ipQTunsZRJAq9Y3CUQnrrx5xgD1IwFAVU0gxn4KN86HpEvnPowD5R++3RKUSfAgMBAAECgYAQk8Nv8XaSrpIPf0x7PxSwczIvd/ijTiNGBqo/AQ2rJiku5JfCDw0ZaKdvLbgmf/3LlsaIUR8pX79enj0WxwapO9/DvSLPDO8wSKKgaPHWYMEpRS5o1fGCW7oFw16+XPakT36pda7BcsTlpHkLoyy2J4u5MNDIh7EWdgdRIl8ruQJBAP5k3rVL2sqUh1JtzhxD6uyAxtzWH4uoOJBtjz8txGM6pWVpypQfyu5hcXxAKtpv3AU3olRGvVaP+SXQPm9REVUCQQDhvntaglsIFpHDu93xgrDRzBEMSYelwniLJU2yF1hfEjukjQaV2UR3ix9SzFaSjKv3gyI7UYe2T99OOV3+nE4jAkEAq5gCmSuXYzoYpUMn2ez+E5UxjlhoYt2PeexMyYLniz9NvUBEwvFHAtB7qiHmRf1Z3bMPLeWhZTgsCJOU6aU3GQJBAI2keD+tJTMo/iMDYNolt46b7WJ5TIycPgiIynxhG4jjptqzxhsF7WjYuTm5juR7MXenJzZzDKU+7o1lf22/Y2MCQA9gIVEYLWnKxITusa939X88ThoG7//wC4MIXJyTNaJ4mxvnUDwN9emwE62lsdVretuACd2AV+T6FIC+DTqS3og=";
		String credan_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgU/EvCjqumn7d+i8uBIrkB/ZqJpfhfPA0HdPKrifd8wOY6VuI5ZQVqNNuKv4OR5Z4O7ztAUs+5fq7vL8x2lWMV/ie4UPVSZALmRd9nsiHQ9o+oqUE7p7GUSQKvWNwlEJ668ecYA9SMBQFVNIMZ+CjfOh6RL5z6MA+Ufvt0SlEnwIDAQAB";
		
		String raw ="123456";
		System.err.println(sign(raw, credan_private_key));
		System.err.println(verify(raw, credan_public_key, sign(raw, credan_private_key)));
	}

}
