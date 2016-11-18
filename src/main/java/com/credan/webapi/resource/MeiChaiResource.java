package com.credan.webapi.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.core.service.api.MeichaiService;
import com.credan.webapi.resource.base.BasicResource;

/**
 * 美差Webservice
 * 
 * @author Mond
 *
 */
@Path("/v1/meichai")
@Produces(MediaType.APPLICATION_JSON)
public class MeiChaiResource extends BasicResource{

	@Autowired
	private MeichaiService meichaiService;
	
	/**
	 * 获取token
	 * 
	 * @param body
	 * @return
	 */
	@POST
	@Path("/index")
	public Response index(String body){
		ResultVo resultVo =meichaiService.index(toJson(body));
		return toResponse(resultVo);
	}
}
