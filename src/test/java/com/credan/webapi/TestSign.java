package com.credan.webapi;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.security.DesHelper;
import com.credan.webapi.comm.util.security.RSAHelper;

public class TestSign extends ApplicationTests {

	@Value("${zlj_public_key}")
	public String zljPublicKey;

	@Value("${zlj_private_key}")
	public String zljPrivateKey;

	@Value("${zlj_deskey}")
	public String desKey;

	@Test
	public void testSign() throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONObject param = JSONObject.parseObject(
				"{\"orderIds\":[\"123\"]}");
		jsonObject.put("merchantId", "1111");
		jsonObject.put("orderId", "123");
		String jsonString = param.toJSONString();
		String encrypt = DesHelper.encrypt(jsonString, desKey);
		System.err.println("encrypt : " + encrypt);
		String sign = RSAHelper.sign(encrypt, zljPrivateKey);
		System.err.println("sign :" + sign);
		jsonObject.put("data", encrypt);
		jsonObject.put("sign", sign);
		jsonObject.put("resultEncrypt", 1);
		jsonObject.put("timestamp", DateHelper.getDate("yyyy-MM-dd HH:mm:ss"));
		System.err.println("desKey : " + desKey);
		System.err.println("jsonString : " + jsonString);
		System.err.println("zljPrivateKey : " + zljPrivateKey);
		System.err.println(" zljPublicKey : " + zljPublicKey);
		System.err.println(RSAHelper.verify(encrypt, zljPublicKey, sign));
		
		System.err.println(jsonObject);
	}


}
