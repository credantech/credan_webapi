/**
 * @(#) BasicResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource;

import javax.ws.rs.core.Response;

import com.credan.webapi.comm.ResultVo;
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
		try {
			Preconditions.checkNotNull(vo);
		} catch (Exception e) {
			vo = new ResultVo(false);
		}
		return Response.status(200).entity(vo).build();
	}

}
