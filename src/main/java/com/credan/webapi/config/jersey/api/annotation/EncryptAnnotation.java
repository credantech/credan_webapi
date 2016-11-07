/**
 * @(#) EncryptAnnotation.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加密注解类， 添加该注解的方法和类将进行特殊处理
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午2:21:08 $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.TYPE })
public @interface EncryptAnnotation {

	boolean value() default true;
	
}
