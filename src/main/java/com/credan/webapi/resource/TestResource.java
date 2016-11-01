/**
 * @(#) TestResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.credan.webapi.core.dao.entity.share.MobileLabel;
import com.credan.webapi.core.dao.mapper.share.MobileLabelMapper;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月1日 下午7:26:51 $
 */
@Path("/test")
public class TestResource {

	@Autowired
	private MobileLabelMapper mobileLabelMapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public MobileLabel test() {
		MobileLabel selectByPrimaryKey = mobileLabelMapper
				.selectByPrimaryKey("201610261908383444c67cd3d64476a11142387e26c473");
		return selectByPrimaryKey;
	}

}
