/**
 * @Project: Demo @(#) JerseyConfig.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.credan.webapi.config.jersey.exception.mapper.ParamExceptionMapper;
import com.credan.webapi.config.jersey.exception.mapper.ResourceNotFoundExceptionMapper;
import com.credan.webapi.config.jersey.filter.JerseyCorsFilter;
import com.credan.webapi.config.jersey.filter.JerseyRequestFilter;
import com.credan.webapi.config.jersey.filter.feature.JerseyRequestFilterFeature;

/**
 * Jersey配置入口
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年7月28日 下午1:54:54 $
 */
@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		packages("com.credan.webapi.resource");

		/** 全局异常处理 */
		register(ResourceNotFoundExceptionMapper.class);
		register(ParamExceptionMapper.class);

		/** 过滤器注册 */
		register(JerseyCorsFilter.class);
		register(JerseyRequestFilter.class);

		/** 请求参数加解密处理 */
		register(JerseyRequestFilterFeature.class);

	}

}
