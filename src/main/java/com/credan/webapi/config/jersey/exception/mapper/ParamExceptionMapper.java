/**
 * @(#) ParamExceptionMapper.java
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
import com.credan.webapi.config.jersey.exception.ParamException;

/**
 * 参数异常
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:48:01 $
 */
@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {

	@Override
	public Response toResponse(ParamException arg0) {
		StatusEnum statusEnum = arg0.getStatusEnum();
		ResultVo vo = new ResultVo(false);
		vo.setErrorCode(statusEnum.getCode());
		vo.setMessage(statusEnum.getMsg());
		return Response.status(200).entity(vo).build();
	}

}
