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
	public String credanPrivateKey;

	@Value("${credan_deskey}")
	public String desKey;

	@Test
	public void testSign() throws Exception {
		JSONObject param = JSONObject.parseObject(
				"{\"itemName\":\"***\",\"itemAmt\":1,\"itemPrice\":200.37,\"tenorApplied\":\"\",\"orderId\":\"\"}");

		String encrypt = DesHelper.encrypt(param.toJSONString(), desKey);
		System.err.println("encrypt : " + encrypt);
		String sign = RSAHelper.sign(encrypt, credanPrivateKey);
		System.err.println("sign :" + sign);
	}


}
