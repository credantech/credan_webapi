/**
 * @(#) AppConfig.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * 初始化加载
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 下午6:05:21 $
 */
@Configuration
@Getter
public class AppConfig {

	/**项目配置*/
	@Value("${spring.profiles.active}")
	private String springProfilesActive;
	
	@Value("${connect_time}")
	private int connetTime;

}
