/**
 * @(#) ParamVerifyExceptionMapper.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.app.exception.mapper;

import java.util.HashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.credan.webapi.comm.enums.StatusCodeEnum;
import com.credan.webapi.comm.util.LogUtil;
import com.credan.webapi.config.jersey.app.exception.ParamVerifyException;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import jersey.repackaged.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数校验错误处理
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年9月23日 上午11:36:01 $
 */
@Slf4j
@Provider
public class ParamVerifyExceptionMapper implements ExceptionMapper<ParamVerifyException> {
	
	@Override
	public Response toResponse(ParamVerifyException arg0) {
		StatusCodeEnum wrongParam = StatusCodeEnum.WRONG_PARAM;
		log.error(LogUtil.getTrace(arg0));
		HashMap<String, Object> map = Maps
				.newHashMap(ImmutableMap.<String, Object> of("message", "参数校验错误", "statusCode", wrongParam.getCode()));
		return Response.ok(map, MediaType.APPLICATION_JSON_TYPE).status(wrongParam.getCode()).build();
	}

}
