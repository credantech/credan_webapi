/**
 * @(#) EncryptResponseService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.security.AESHelper;
import com.credan.webapi.comm.util.security.RSAHelper;
import com.credan.webapi.config.jersey.api.entity.RequestVo;
import com.credan.webapi.config.jersey.api.entity.ResponseVo;
import com.credan.webapi.core.dao.entity.conf.MerchantRsaConfig;
import com.credan.webapi.core.service.AbstractBasicService;
import com.credan.webapi.core.service.conf.MerchantRsaConfigService;

/**
 * 响应结果加密处理
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 下午6:02:37 $
 */
@Service
public class EncryptResponseService extends AbstractBasicService {
	@Autowired
	private MerchantRsaConfigService merchantRsaConfigService;

	/**
	 * 响应结果加密
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ResponseVo encryResponse(String response, RequestVo requestParam) throws Exception {
		MerchantRsaConfig merchantRsaConfig = merchantRsaConfigService.findOneByMerchantId(requestParam.getMerchantId());
		ResponseVo responseParam = JSONObject.parseObject(response, ResponseVo.class);
		String encrypt = AESHelper.encrypt(responseParam.getData().toString(), merchantRsaConfig.getAesKey());
		String sign = RSAHelper.sign(encrypt, merchantRsaConfig.getCredanRsaPrivateKey());
		responseParam.setData(encrypt);
		responseParam.setSign(sign);
		responseParam.setResultEncrypt(requestParam.getResultEncrypt());
		responseParam.setTxnCode(requestParam.getTxnCode());
		responseParam.setTimestamp(DateHelper.getDateTime());
		return responseParam;
	}
	
	/**
	 * 手动组装返回加密参数
	 * 
	 * @param responseParam
	 * @return
	 * @throws Exception
	 */
	public JSONObject warpResponse(JSONObject responseParam) throws Exception{
		String merchantId = responseParam.getString("merchantId");
		MerchantRsaConfig merchantRsaConfig = merchantRsaConfigService.findOneByMerchantId(merchantId);
		JSONObject newHashMap = new JSONObject();
		String encrypt = AESHelper.encrypt(responseParam.toString(), merchantRsaConfig.getAesKey());
		newHashMap.put("data", encrypt);
		newHashMap.put("sign", RSAHelper.sign(encrypt, merchantRsaConfig.getCredanRsaPrivateKey()));
		newHashMap.put("timestamp", DateHelper.getDateTime());
		newHashMap.put("notifyUrl", merchantRsaConfig.getNotifyUrl());
		return newHashMap;
	}

}
