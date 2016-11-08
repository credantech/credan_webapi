/**
 * @(#) ParamExceptionMapper.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.config.jersey.api.entity.StatusCodeEnum;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;
import com.credan.webapi.config.jersey.api.exception.ParamException;

import lombok.extern.slf4j.Slf4j;

/**
 * 参数异常
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:48:01 $
 */
@Slf4j
@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {

	@Override
	public Response toResponse(ParamException arg0) {
		StatusEnum statusEnum = arg0.getStatusEnum();
		log.error(arg0.getMsg(), arg0);
		ResultVo vo = new ResultVo(false);
		vo.setErrorCode(statusEnum.getCode());
		vo.setMessage(arg0.getMsg());
		vo.setStatusCode(StatusCodeEnum.WRONG_PARAM.getCode());
		return Response.status(200).entity(vo).build();
	}

}
