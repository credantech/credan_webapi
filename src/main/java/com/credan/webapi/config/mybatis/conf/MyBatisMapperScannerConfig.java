/**
 * @(#) MyBatisMapperScannerConfig.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.mybatis.conf;

import java.util.Properties;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.credan.webapi.config.Global;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * myBatis扫描接口
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月1日 下午6:52:10 $
 */
@Configuration
// TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(Global.BASIC_PACKAGE + ".core.dao.mapper");
		Properties properties = new Properties();
		properties.setProperty("mappers", Global.BASIC_PACKAGE + ".config.mybatis.conf.MyBatisDao");
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}
}
