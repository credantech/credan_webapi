package com.credan.webapi.resource.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.core.service.app.CredanService;
import com.credan.webapi.resource.app.base.BasicResource;

@Path("/v1/zlj")
public class CredanResource extends BasicResource{

	@Autowired
	private CredanService credanService;
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(String body){
		JSONObject jsonObject = JSONObject.parseObject(body);
		credanService.register(jsonObject);
		return null;
	}
	
}
