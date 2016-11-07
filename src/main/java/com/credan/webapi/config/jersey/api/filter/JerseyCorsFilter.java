/**
 * @(#) JerseyCorsFilter.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ContainerRequest;

import com.credan.webapi.comm.util.Json;

import lombok.extern.slf4j.Slf4j;

/**
 * Jersey跨域处理
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午2:08:15 $
 */
@Slf4j
@Provider
public class JerseyCorsFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext creq, ContainerResponseContext cres) {
		log.debug("URL --> {}, Response -->> {}", ((ContainerRequest) creq).getRequestUri().getPath(),
				Json.ObjectMapper.writeValue(cres.getEntity()));
		cres.getHeaders().add("Access-Control-Allow-Origin", "*");
		cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
		cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		cres.getHeaders().add("Access-Control-Max-Age", "1209600");

	}

}
