/**
 * @(#) FileResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource.file;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.config.jersey.api.entity.StatusCodeEnum;
import com.credan.webapi.core.service.file.FileUserService;
import com.credan.webapi.resource.base.BasicResource;

/**
 * 文件上传
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年9月22日 下午6:15:54 $
 */
@Path("/file")
@Component
public class FileResource extends BasicResource {

	@Autowired
	private FileUserService fileUserService;

	@POST
	@Path("/upload")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(String param) throws Exception {
		ResultVo resultVo = fileUserService.upload(param);
		if (resultVo.isSuccess()) {
			resultVo.setStatusCode(StatusCodeEnum.FILE_UPLODED.getCode());
		} else {
			resultVo.setStatusCode(StatusCodeEnum.FILE_UNUPLODED.getCode());
		}
		return toResponse(resultVo);
	}
}
