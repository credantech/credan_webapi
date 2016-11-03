package com.credan.webapi.service.sign;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.SignUtil;
import com.credan.webapi.config.jersey.exception.ParamException;
import com.credan.webapi.service.CredanBaseService;

public class SignService extends CredanBaseService{
	
	@Value("${zlj_deskey}")
	private String desKey;
	
	@Value("${zlj_private_key}")
	private String merchantPrivateKey;
	
	@Value("${zlj_public_key}")
	private String merchantPublicKey;
	
	@Value("${credan_deskey}")
	private String credanDeskey;
	
	@Value("${credan_private_key}")
	private String credanPrivateKey;
	
	@Value("${credan_public_key}")
	private String credanPublicKey;
	
	public ResultVo processParams(JSONObject params) {
		ResultVo resultVo = new ResultVo(false);
		try {
			String data = params.getString("data");
			String sign = params.getString("sign");
			if(!SignUtil.checkSign(data, sign, "")){
				return resultVo;
			}
			String timestamp = params.getString("timestamp");
			Date timeDate = DateHelper.addMinute(DateHelper.parseDate(timestamp,"yyyy-MM-dd HH:mm:ss"), 5);
			Date curDate = new Date();
			if(timeDate.compareTo(curDate) > 0){
				throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
			}

			String decryptData = SignUtil.decrypt(sign, desKey);
			
		} catch (Exception e) {
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}
		return resultVo;
	}
	
	
	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String decryptSign(String data) throws Exception{
		String signData = SignUtil.sign(data, credanPrivateKey);
		String decrypt = SignUtil.decrypt(data, credanDeskey);
		System.err.println(decrypt);
		return decrypt; 
	}

	public String encryptSign(String data) throws Exception{
		String encrypt = SignUtil.encrypt(data, desKey);
		System.err.println(encrypt);
		return encrypt;
	}
	
	
	
}
