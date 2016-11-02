/**
 * @(#) ZhaoLiJiResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;

/**   
 * 
 *  
 * @author  Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:10:22 $ 
 */
@Path("/v1/zlj")
@Produces(MediaType.APPLICATION_JSON)
public class ZhaoLiJiResource {
	
	/**
	 * 商户跳入请求
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/index")
	public String index(JSONObject params) {
		return "*****";
	}
	
	/**
	 * 查询订单信息
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/findOrders")
	public String findOrders(JSONObject params) {
		return "*****";
	}
	
	

}
