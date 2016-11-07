/**
 * @(#) ResourceNotFoundExceptionMapper.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.exception.mapper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.alibaba.fastjson.JSONObject;
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
		Status statusCode = Status.NOT_FOUND;
		StatusEnum statusEnum = StatusEnum.UN_AUTHORIZED;
		ResultVo vo = new ResultVo(false);
		vo.setErrorCode(statusEnum.getCode());
		vo.setMessage(statusEnum.getMsg());
		JSONObject parseObject = JSONObject.parseObject(vo.toString());
		parseObject.put("statusCode", statusCode.getStatusCode());
		return Response.status(200).entity(parseObject).build();
	}

}
