/**
 * @(#) ResourceExceptionMapper.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.exception.mapper;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 下午2:36:59 $
 */
@Slf4j
@Provider
public class ResourceExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception arg0) {
		Status statusCode = Status.INTERNAL_SERVER_ERROR;
		StatusEnum statusEnum = StatusEnum.SYSTEM_ERROR;
		if (arg0 instanceof NotAllowedException) {
			statusCode = Status.NOT_FOUND;
			statusEnum = StatusEnum.UN_AUTHORIZED;
		}
		log.error(arg0.getMessage(), arg0);
		ResultVo vo = new ResultVo(false);
		vo.setErrorCode(statusEnum.getCode());
		vo.setMessage(statusEnum.getMsg());
		vo.setStatusCode(statusCode.getStatusCode() + "");
		return Response.status(200).entity(vo).build();
	}
}
