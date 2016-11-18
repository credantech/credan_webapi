/**
 * @(#) ServerConfig.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 忽略spring.boot.admin.client抛出的异常
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年10月14日 下午2:58:15 $
 */
@Configuration
public class ServerConfig {
	@Bean
	public ServerProperties serverProperties() {
		return new IgnoreUnknownFieldsServerProperties();
	}

	@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
	public static class IgnoreUnknownFieldsServerProperties extends ServerProperties {

	}
}
