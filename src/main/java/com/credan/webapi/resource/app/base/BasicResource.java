/**
 * @(#) BasicResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource.app.base;

import javax.ws.rs.core.Response;

import com.credan.webapi.comm.JsonResult;
import com.credan.webapi.config.jersey.app.entity.ApiResultResponse;
import com.credan.webapi.resource.AbstractBasicResource;

/**
 * Webservice父类
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午1:25:45 $
 */
public abstract class BasicResource extends AbstractBasicResource{

	/**
	 * 处理响应结果（针对前端）
	 * 
	 * @param resultVo
	 * @return
	 */
	protected Response toResponse(JsonResult jsonResult) {
		ApiResultResponse apiResultResponse = new ApiResultResponse();
		apiResultResponse.setStatusCode(jsonResult.getStatusCode());
		apiResultResponse.setData(jsonResult.getData());
		apiResultResponse.setMessage(jsonResult.getMsg());
		return Response.status(200).entity(apiResultResponse.toString()).build();
	}


}
