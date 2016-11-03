package com.credan.webapi.core.service.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.security.DesHelper;
import com.credan.webapi.comm.util.security.RSAHelper;
import com.credan.webapi.config.jersey.exception.ParamException;
import com.credan.webapi.core.service.AbstractBasicService;

@Service
public class SignService extends AbstractBasicService{
	
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
	
	public JSONObject processParams(JSONObject params) {
		try {
			String data = params.getString("data");
			String sign = params.getString("sign");
			if(!RSAHelper.verify(data.getBytes(),merchantPublicKey,sign)){
				throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
			}
			String timestamp = params.getString("timestamp");
			Date timeDate = DateHelper.addMinute(DateHelper.parseDate(timestamp,"yyyy-MM-dd HH:mm:ss"), 5);
			Date curDate = new Date();
			if(timeDate.compareTo(curDate) > 0){
				throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
			}

			String decryptData = DesHelper.decrypt(data,desKey);
			params.put(data, decryptData);
			
		} catch (Exception e) {
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}
		return params;
	}
	
	
	
}
