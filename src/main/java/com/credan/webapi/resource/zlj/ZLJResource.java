/**
 * @(#) ZLJResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource.zlj;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.config.jersey.annotation.EncryptAnnotation;
import com.credan.webapi.resource.BasicResource;

/**
 * 找靓机对外WebService接口
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:10:22 $
 */
@EncryptAnnotation
@Path("/v1/zlj")
@Produces(MediaType.APPLICATION_JSON)
public class ZLJResource extends BasicResource {

	/**
	 * 商户跳入请求
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/index")
	public Response index(String params) {
		ResultVo resultVo = new ResultVo(false);
		return toResponse(resultVo);
	}

	/**
	 * 查询订单信息
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/findOrders")
	public Response findOrders(String params) {
		ResultVo resultVo = new ResultVo(false);
		return toResponse(resultVo);
	}

}
