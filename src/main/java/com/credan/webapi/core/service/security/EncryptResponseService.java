/**
 * @(#) EncryptResponseService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.security;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.security.DESHelper;
import com.credan.webapi.comm.util.security.RSAHelper;
import com.credan.webapi.config.AppConfig;
import com.credan.webapi.config.jersey.api.entity.RequestVo;
import com.credan.webapi.config.jersey.api.entity.ResponseVo;
import com.credan.webapi.core.service.AbstractBasicService;

/**
 * 响应结果加密处理
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 下午6:02:37 $
 */
@Service
public class EncryptResponseService extends AbstractBasicService {
	@Inject
	AppConfig appConfig;

	/**
	 * 响应结果加密
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ResponseVo encryResponse(String response, RequestVo requestParam) throws Exception {
		ResponseVo responseParam = JSONObject.parseObject(response, ResponseVo.class);
		String encrypt = DESHelper.encrypt(responseParam.getData().toString(), appConfig.getDesKey());
		String sign = RSAHelper.sign(encrypt, appConfig.getPrivateKey());
		responseParam.setData(encrypt);
		responseParam.setSign(sign);
		responseParam.setResultEncrypt(requestParam.getResultEncrypt());
		responseParam.setTxnCode(requestParam.getTxnCode());
		responseParam.setTimestamp(DateHelper.getDateTime());
		return responseParam;
	}

}
