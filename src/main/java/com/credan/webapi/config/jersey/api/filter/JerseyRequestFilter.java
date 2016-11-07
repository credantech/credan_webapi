/**
 * @(#) JerseyRequestFilter.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ContainerRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Jersey请求拦截
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午2:11:05 $
 */
@Slf4j
@Provider
public class JerseyRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerrequestcontext) throws IOException {
		String param = null;
		ContainerRequest cr = null;
		cr = (ContainerRequest) containerrequestcontext;
		cr.bufferEntity();
		param = cr.readEntity(String.class);
		log.debug("URL --> {}, Paramter --> {}", cr.getRequestUri().getPath(), param);
	}

}
