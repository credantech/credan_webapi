/**
 * @(#) EncryptProcessor.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.filter.feature;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.comm.util.Json;
import com.credan.webapi.config.jersey.exception.ParamException;
import com.credan.webapi.service.sign.SignService;

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
			// TODO 处理数据 params,解析data数据，待处理
//			ResultVo result = signService.processParams(params);
			System.err.println(params);
		} catch (Exception e) {
			log.error(this.getClass().getSimpleName() + ", param : {}", param);
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}
	}

}
