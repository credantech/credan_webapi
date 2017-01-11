package com.credan.webapi;

import java.net.URLEncoder;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.security.AESHelper;
import com.credan.webapi.comm.util.security.RSAHelper;

public class TestSign extends ApplicationTests {

	@Value("${credan_public_key}")
	public String zljPublicKey;

	@Value("${credan_private_key}")
	public String zljPrivateKey;

	@Value("${zlj_deskey}")
	public String desKey;

	@Test
	public void testIndex() throws Exception {
		JSONObject param = new JSONObject();
		param.put("orderId", "111111");
		param.put("itemPrice", "3000");
		param.put("itemAmt", 1);
		param.put("itemName", "test");
		String jsonString = param.toJSONString();
		String encrypt = AESHelper.encrypt(jsonString, desKey);
		System.err.println("encrypt : " + encrypt);
		String sign = RSAHelper.sign(encrypt, zljPrivateKey);
		System.err.println("sign :" + sign);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", encrypt);
		jsonObject.put("sign", sign);
		jsonObject.put("resultEncrypt", 1);
		jsonObject.put("timestamp", DateHelper.getDate("yyyy-MM-dd HH:mm:ss"));
		jsonObject.put("merchantId", "18fd13cc9aa611e6afb66c92bf314c17");
		System.err.println("desKey : " + desKey);
		System.err.println("jsonString : " + jsonString);
		System.err.println("zljPrivateKey : " + zljPrivateKey);
		System.err.println(" zljPublicKey : " + zljPublicKey);
		System.err.println(RSAHelper.verify(encrypt, zljPublicKey, sign));
		
		System.err.println(jsonObject);
		System.err.println(URLEncoder.encode(jsonObject.toJSONString(), "utf-8"));
	}


}
