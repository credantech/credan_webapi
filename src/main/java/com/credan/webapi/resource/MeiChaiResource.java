package com.credan.webapi.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.resource.base.BasicResource;

@Path("/v1/meichai")
@Produces(MediaType.APPLICATION_JSON)
public class MeiChaiResource extends BasicResource{

	
	@POST
	@Path("/index")
	public Response index(String body){
		ResultVo resultVo = new ResultVo();
		JSONObject jsonObject = JSONObject.parseObject(body);
		
		
		return toResponse(resultVo);
	}
}
