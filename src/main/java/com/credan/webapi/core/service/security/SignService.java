package com.credan.webapi.core.service.security;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.security.DesHelper;
import com.credan.webapi.comm.util.security.RSAHelper;
import com.credan.webapi.config.jersey.exception.ParamException;
import com.credan.webapi.core.service.AbstractBasicService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignService extends AbstractBasicService {

	@Value("${connet_time}")
	private int connetTime;

	@Value("${zlj_deskey}")
	private String desKey;

	@Value("${zlj_public_key}")
	private String merchantPublicKey;

	public JSONObject processInputParams(JSONObject params) {
		checkNotNull(params, "sign", "data", "timestamp");
		String data = params.getString("data");
		String sign = params.getString("sign");
		String timestamp = params.getString("timestamp");
		
		boolean verify = false;
		try {
			verify = RSAHelper.verify(data.getBytes(), merchantPublicKey, sign);
		} catch (Exception e) {
			log.error("验签异常 ======",e);
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}
		if (!verify) {
			log.debug("验签失败=========sign:{}",sign);
			throw new ParamException(StatusEnum.INVALID_SIGN);
		}
		Date timeDate = null;
		try {
			timeDate = DateHelper.addMinute(DateHelper.parseDate(timestamp, "yyyy-MM-dd HH:mm:ss"), connetTime);
		} catch (ParseException e) {
			log.error("时间格式异常======",e);
			throw new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR, "timestamp");
		}
		Date curDate = new Date();
		if (timeDate.compareTo(curDate) > 0) {
			log.error("请求过期 ======timestamp:{}",timestamp);
			throw new ParamException(StatusEnum.INVALID);
		}

		String decryptData;
		try {
			decryptData = DesHelper.decrypt(data, desKey);
		} catch (Exception e) {
			log.error("解码异常======{}",e);
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}
		params.put("data", JSONObject.parseObject(decryptData));
		return params;
	}
	
	

}
