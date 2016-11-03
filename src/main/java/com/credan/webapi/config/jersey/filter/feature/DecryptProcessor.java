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
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.comm.util.Json;
import com.credan.webapi.config.Global;
import com.credan.webapi.config.jersey.exception.ParamException;
import com.credan.webapi.core.service.security.SignService;
import com.google.common.base.Charsets;
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
public class DecryptProcessor implements ContainerResponseFilter {
	@Override

	public void filter(ContainerRequestContext arg0, ContainerResponseContext arg1) throws IOException {
		ContainerRequest cr = (ContainerRequest) arg0;
		cr.bufferEntity();
		String param = cr.readEntity(String.class);
		JSONObject params = Json.ObjectMapper.fromJson(param, JSONObject.class);
		int resultEncrypt = params.getIntValue(Global.RESULT_ENCRYPT);
		if (Global.RESULT_ENCRYPT_NO == resultEncrypt) {
			return;
		}
		Object entity = arg1.getEntity();
		System.err.println(entity);
	}

}
