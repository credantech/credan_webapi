/**
 * @(#) BasicResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource;

import javax.ws.rs.core.Response;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.config.jersey.entity.ResponseVo;
import com.google.common.base.Preconditions;

/**
 * Webservice父类
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午1:25:45 $
 */
public abstract class BasicResource {

	/**
	 * 处理响应结果
	 * 
	 * 
	 * @param vo
	 * @return
	 */
	protected Response toResponse(ResultVo vo) {
		ResponseVo responseVo = null;
		try {
			Preconditions.checkNotNull(vo);
		} catch (Exception e) {
			vo = new ResultVo(false);
		}
		responseVo = ResponseVo.builder().data(vo.getData()).errorCode(vo.getErrorCode()).message(vo.getMessage())
				.timestamp(com.credan.webapi.comm.util.DateHelper.getDateTime()).build();
		return Response.status(200).entity(responseVo).build();
	}

	protected JSONObject toJson(String str) {
		return JSONObject.parseObject(str);
	}

}
