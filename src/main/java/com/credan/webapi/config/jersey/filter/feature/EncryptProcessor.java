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
import com.credan.webapi.comm.util.Json;
import com.credan.webapi.core.service.security.SignService;
import com.google.common.base.Charsets;

/**
 * 入参解密处理器
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午2:30:15 $
 */
@Component
public class EncryptProcessor implements ContainerRequestFilter {

	@Autowired
	private SignService signService;

	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
		ContainerRequest cr = (ContainerRequest) arg0;
		cr.bufferEntity();
		String param = cr.readEntity(String.class);
		JSONObject params = Json.ObjectMapper.fromJson(param, JSONObject.class);
		params = signService.processInputParams(params);
		InputStream inputStream = new ByteArrayInputStream(params.toJSONString().getBytes(Charsets.UTF_8));
		arg0.setEntityStream(inputStream);
	}

}
