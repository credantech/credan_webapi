/**
 * @(#) EncryptProcessor.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.filter.feature;

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
import com.credan.webapi.comm.util.Json;
import com.credan.webapi.config.jersey.exception.ParamException;
import com.credan.webapi.core.service.security.SignService;
import com.google.common.base.Charsets;

import lombok.extern.slf4j.Slf4j;

/**
 * 加密处理器
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午2:30:15 $
 */
@Slf4j
@Component
public class EncryptProcessor implements ContainerRequestFilter {

	@Autowired
	private SignService signService;

	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
		String param = null;
		ContainerRequest cr = null;
		try {
			cr = (ContainerRequest) arg0;
			cr.bufferEntity();
			param = cr.readEntity(String.class);
			JSONObject params = Json.ObjectMapper.fromJson(param, JSONObject.class);
			params = signService.processInputParams(params);
			InputStream inputStream = new ByteArrayInputStream(params.toJSONString().getBytes(Charsets.UTF_8));
			arg0.setEntityStream(inputStream);
		} catch (Exception e) {
			log.error(this.getClass().getSimpleName() + ", param : {}", param);
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}
	}

}
