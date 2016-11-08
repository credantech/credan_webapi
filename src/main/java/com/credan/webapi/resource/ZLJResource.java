/**
 * @(#) ZLJResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.config.jersey.api.annotation.EncryptAnnotation;
import com.credan.webapi.core.service.api.zlj.ZLJService;
import com.credan.webapi.resource.base.BasicResource;

/**
 * 找靓机对外WebService接口
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:10:22 $
 */
@Path("/v1/zlj")
@Produces(MediaType.APPLICATION_JSON)
public class ZLJResource extends BasicResource {

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
		ResultVo resultVo = zLJService.index(params);
		return toResponse(resultVo);
	}

	/**
	 * [商户查询接口]查询订单信息
	 * 
	 * @param params
	 * @return
	 */
	@EncryptAnnotation
	@POST
	@Path("/findOrders")
	public Response findOrders(String params) {
		ResultVo resultVo = zLJService.findOrders(toJson(params));
		return toResponse(resultVo);
	}

	/**
	 * 回调通知
	 * 
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@POST
	@Path("/notify")
	public Response notify(String params) throws Exception {
		ResultVo resultVo = zLJService.notify(toJson(params));
		return toResponse(resultVo);
	}

}
