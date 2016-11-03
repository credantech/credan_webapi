package com.credan.webapi;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.util.security.DesHelper;
import com.credan.webapi.comm.util.security.RSAHelper;

public class TestSign extends ApplicationTests {

	@Value("${credan_public_key}")
	public String credanPublicKey;

	@Value("${credan_private_key}")
	public static String credanPrivateKey;

	@Value("${credan_deskey}")
	public static String desKey;

	public void testSign() throws Exception {
		JSONObject param = JSONObject.parseObject(
				"{\"itemName\":\"***\",\"itemAmt\":1,\"itemPrice\":200.37,\"tenorApplied\":\"\",\"orderId\":\"\"}");

		String encrypt = DesHelper.encrypt(param.toJSONString(), desKey);
		String sign = RSAHelper.sign(encrypt.getBytes(), credanPrivateKey);
		System.err.println(sign);
	}

	public static void main(String[] args) throws Exception {
		JSONObject param = JSONObject.parseObject(
				"{\"itemName\":\"手机\",\"itemAmt\":1,\"itemPrice\":200.37,\"tenorApplied\":\"9\",\"orderId\":\"12521215\"}");

		String encrypt = DesHelper.encrypt(param.toJSONString(), desKey);
		String sign = RSAHelper.sign(encrypt.getBytes(), credanPrivateKey);
		System.err.println(sign);
	}

}
