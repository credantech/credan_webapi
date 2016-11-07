/**
 * @(#) DecryptProcessor.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.filter.feature;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.config.Global;
import com.credan.webapi.config.jersey.api.entity.RequestVo;
import com.credan.webapi.config.jersey.api.entity.ResponseVo;
import com.credan.webapi.core.service.security.EncryptResponseService;
import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

/**
 * 响应结果加密处理器
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午2:30:15 $
 */
@Slf4j
@Component
public class EncryptProcessor implements ContainerResponseFilter {
	@Autowired
	private EncryptResponseService encryptResponseService;

	@Override
	public void filter(ContainerRequestContext arg0, ContainerResponseContext arg1) throws IOException {
		try {
			ContainerRequest cr = (ContainerRequest) arg0;
			cr.bufferEntity();
			String param = cr.readEntity(String.class);
			RequestVo requestParam = JSONObject.parseObject(param, RequestVo.class);
			int resultEncrypt = Strings.isNullOrEmpty(requestParam.getResultEncrypt()) ? Global.RESULT_ENCRYPT_NO
					: Integer.valueOf(requestParam.getResultEncrypt());
			if (Global.RESULT_ENCRYPT_NO == resultEncrypt) {
				return;
			}
			Object entity = arg1.getEntity();
			ResponseVo encryResponse = encryptResponseService.encryResponse(entity.toString(), requestParam);
			arg1.setEntity(encryResponse);
		} catch (Exception e) {
			log.error("处理响应结果异常", e);
			arg1.setStatus(Status.OK.getStatusCode());
			ResponseVo build = ResponseVo.builder().errorCode(StatusEnum.SYSTEM_ERROR.getCode())
					.message(StatusEnum.SYSTEM_ERROR.getMsg()).build();
			arg1.setEntity(build);
		}
	}

}
