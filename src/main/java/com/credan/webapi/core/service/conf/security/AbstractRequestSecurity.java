/**
 * @(#) AbstractRequestSecurity.java
 * 
 * Copyright (c) 2017, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.conf.security;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.config.jersey.api.entity.RequestVo;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;
import com.credan.webapi.config.jersey.api.exception.ParamException;
import com.credan.webapi.core.dao.entity.conf.MerchantRsaConfig;
import com.credan.webapi.core.service.AbstractBasicService;

import lombok.extern.slf4j.Slf4j;

/**
 * 加密请求参数解密流程抽象类
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2017年1月13日 上午11:38:46 $
 */
@Slf4j
public abstract class AbstractRequestSecurity extends AbstractBasicService {

	/**
	 * 解析加密的入参
	 * 
	 * @param requestVo
	 * @return
	 */
	public final RequestVo processInputParams(RequestVo requestVo) {
		JSONObject params = null;
		try {
			params = JSONObject.parseObject(requestVo.toString());
		} catch (Exception e) {
			log.error("时间格式异常======{}", e);
			throw new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR, "timestamp");
		}
		checkNotNull(params, "sign", "data", "timestamp", "merchantId");
		/** 校验请求时间 */
		checkReqTime(requestVo.getTimestamp());
		/** 校验商户ID */
		checkMerchantId(requestVo.getMerchantId());
		String data = requestVo.getData().toString();
		/** 校验签名 */
		MerchantRsaConfig merchantRsaConfig = checkSign(data, requestVo.getSign(), requestVo.getMerchantId());
		/** 解密参数 */
		String decryptData = decryptData(data, merchantRsaConfig.getAesKey());
		requestVo.setData(JSONObject.parseObject(decryptData));
		return requestVo;
	}

	protected abstract boolean checkReqTime(String timestamp);

	protected abstract boolean checkMerchantId(String merchantId);

	protected abstract MerchantRsaConfig checkSign(String data, String sign, String merchantId);

	protected abstract String decryptData(String data, String aesKey);

}
