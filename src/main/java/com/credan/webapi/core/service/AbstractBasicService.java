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
import com.google.common.base.Strings;

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
	public void checkNotNull(JSONObject param, String... keys) {
		Function<String[], String> function = new Function<String[], String>() {
			@Override
			public String apply(String[] arg0) {
				for (String key : arg0) {
					try {
						Preconditions.checkNotNull(param.get(key), key);
					} catch (Exception e) {
						return key;
					}
				}
				return null;
			}
		};
		String keyFlag = null;
		if (!(Strings.isNullOrEmpty(keyFlag = function.apply(keys)))) {
			throw new ParamException(StatusEnum.PROPERTY_REQUIRED, keyFlag);
		}
	}

}
