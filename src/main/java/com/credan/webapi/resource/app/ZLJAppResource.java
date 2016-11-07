/**
 * @(#) ZLJAppResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.credan.webapi.comm.JsonResult;
import com.credan.webapi.core.service.api.zlj.ZLJService;
import com.credan.webapi.resource.app.base.BasicResource;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月4日 下午6:22:21 $
 */
@Path("/v1/zlj")
@Produces(MediaType.APPLICATION_JSON)
public class ZLJAppResource extends BasicResource {

	@Autowired
	private ZLJService zLJService;

	/**
	 * 商户跳入请求(该接口由前端转发进入)
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/index")
	public Response index(String params) {
		JsonResult jsonResult = zLJService.index(toJson(params));
		return toResponse(jsonResult);
	}

}
