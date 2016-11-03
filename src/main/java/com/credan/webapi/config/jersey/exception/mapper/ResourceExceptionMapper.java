/**
 * @(#) ResourceExceptionMapper.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.enums.StatusEnum;

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
		StatusEnum statusEnum = StatusEnum.SYSTEM_ERROR;
		log.error(arg0.getMessage(), arg0);
		ResultVo vo = new ResultVo(false);
		vo.setErrorCode(statusEnum.getCode());
		vo.setMessage(arg0.getMessage());
		return Response.status(200).entity(vo).build();
	}
}
