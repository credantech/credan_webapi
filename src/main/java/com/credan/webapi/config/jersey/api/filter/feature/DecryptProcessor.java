/**
 * @(#) DecryptProcessor.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.filter.feature;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.config.jersey.api.entity.RequestVo;
import com.credan.webapi.config.jersey.api.exception.ParamException;
import com.credan.webapi.core.service.security.SignService;
import com.google.common.base.Charsets;

import lombok.extern.slf4j.Slf4j;

/**
 * 入参解密处理器
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午2:30:15 $
 */
@Slf4j
@Component
public class DecryptProcessor implements ContainerRequestFilter {

	@Autowired
	private SignService signService;

	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
		ContainerRequest cr = (ContainerRequest) arg0;
		cr.bufferEntity();
		String param = cr.readEntity(String.class);
		RequestVo requestParam = null;
		try {
			requestParam = JSONObject.parseObject(param, RequestVo.class);
		} catch (Exception e) {
			log.error("请求参数转换JSON异常 ", e);
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}
		requestParam = signService.processInputParams(requestParam);
		InputStream inputStream = new ByteArrayInputStream(requestParam.toString().getBytes(Charsets.UTF_8));
		arg0.setEntityStream(inputStream);
	}

}
