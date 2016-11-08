package com.credan.webapi.core.service.security;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.security.DesHelper;
import com.credan.webapi.comm.util.security.RSAHelper;
import com.credan.webapi.config.jersey.api.entity.RequestVo;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;
import com.credan.webapi.config.jersey.api.exception.ParamException;
import com.credan.webapi.core.service.AbstractBasicService;
import com.credan.webapi.core.service.merchant.MerchantInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignService extends AbstractBasicService {

	@Value("${connect_time}")
	private int connetTime;

	@Value("${zlj_deskey}")
	private String desKey;

	@Value("${zlj_public_key}")
	private String merchantPublicKey;

	@Autowired
	protected MerchantInfoService merchantInfoService;

	public RequestVo processInputParams(RequestVo requestVo) {
		JSONObject params = JSONObject.parseObject(requestVo.toString());
		checkNotNull(params, "sign", "data", "timestamp", "merchantId");
		String data = requestVo.getData().toString();
		String sign = requestVo.getSign();
		String timestamp = requestVo.getTimestamp();

		Date timeDate = null;
		try {
			timeDate = DateHelper.addMinute(DateHelper.parseDate(timestamp, "yyyy-MM-dd HH:mm:ss"), connetTime);
		} catch (ParseException e) {
			log.error("时间格式异常======", e);
			throw new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR, "timestamp");
		}
		Date curDate = new Date();
		if (timeDate.compareTo(curDate) < 0) {
			log.error("请求过期 ======timestamp:{}", timestamp);
			throw new ParamException(StatusEnum.INVALID);
		}

		boolean verify = false;
		try {
			verify = RSAHelper.verify(data, merchantPublicKey, sign);
		} catch (Exception e) {
			log.error("验签异常 ======", e);
			throw new ParamException(StatusEnum.INVALID_SIGN);
		}
		if (!verify) {
			log.debug("验签失败=========sign:{}", sign);
			throw new ParamException(StatusEnum.INVALID_SIGN);
		}

		String decryptData;
		try {
			decryptData = DesHelper.decrypt(data, desKey);
		} catch (Exception e) {
			log.error("解码异常======{}", e);
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}
		boolean checkStatus = merchantInfoService.checkStatus(requestVo.getMerchantId());
		if (!checkStatus) {
			log.error("商户ID无效=========MerchantId:{}", requestVo.getMerchantId());
			throw new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR);
		}
		requestVo.setData(JSONObject.parseObject(decryptData));
		return requestVo;
	}

}
