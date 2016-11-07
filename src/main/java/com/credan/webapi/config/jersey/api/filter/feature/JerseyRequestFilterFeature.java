/**
 * @(#) JerseyRequestFilterFeature.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.filter.feature;

import java.lang.annotation.Annotation;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.credan.webapi.config.jersey.api.annotation.EncryptAnnotation;
import com.google.common.base.Objects;

/**
 * 请求拦截器处理
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午2:17:46 $
 */
@Provider
public class JerseyRequestFilterFeature implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		Annotation annotation = resourceInfo.getResourceClass().getAnnotation(EncryptAnnotation.class);
		annotation = !Objects.equal(annotation, null) ? annotation
				: resourceInfo.getResourceMethod().getAnnotation(EncryptAnnotation.class);
		if (!Objects.equal(annotation, null)) {
			context.register(DecryptProcessor.class);
			context.register(EncryptProcessor.class);
		}
	}

}
