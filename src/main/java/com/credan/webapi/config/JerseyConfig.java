/**
 * @Project: Demo @(#) JerseyConfig.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年7月28日 下午1:54:54 $
 */
@Component
@ApplicationPath("/v1")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		packages("com.credan.webapi.resource");

		/** 全局异常处理 */

		/** 过滤器注册 */

	}

}
