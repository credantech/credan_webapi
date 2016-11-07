/**
 * @(#) AbstractBasicService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;
import com.credan.webapi.config.jersey.api.exception.ParamException;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;

/**
 * 抽象父类
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 上午11:47:54 $
 */
public class AbstractBasicService {

	/**
	 * 判断参数不能为空
	 * 
	 * @param objects
	 */
	public static void checkNotNull(JSONObject param, String... keys) {
		Function<String[], Boolean> function = new Function<String[], Boolean>() {
			@Override
			public Boolean apply(String[] arg0) {
				for (String key : arg0) {
					Preconditions.checkNotNull(param.get(key), key);
				}
				return true;
			}
		};
		try {
			function.apply(keys);
		} catch (Exception e) {
			throw new ParamException(StatusEnum.PROPERTY_REQUIRED, e.getMessage());
		}
	}

}
