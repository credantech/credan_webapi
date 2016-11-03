/**
 * @(#) ResourceNotFoundExceptionMapper.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.exception.mapper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.enums.StatusEnum;

/**
 * 接口404
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午12:43:04 $
 */
@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<javax.ws.rs.NotFoundException> {

	@Override
	public Response toResponse(NotFoundException arg0) {
		StatusEnum statusEnum = StatusEnum.UN_AUTHORIZED;
		ResultVo vo = new ResultVo(false);
		vo.setErrorCode(statusEnum.getCode());
		vo.setMessage(statusEnum.getMsg());
		return Response.status(200).entity(vo).build();
	}

}
