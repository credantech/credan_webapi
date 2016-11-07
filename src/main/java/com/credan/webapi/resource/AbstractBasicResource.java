/**
 * @(#) AbstractBasicResource.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.resource;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月4日 下午6:26:08 $
 */
public abstract class AbstractBasicResource {
	
	protected JSONObject toJson(String str) {
		return JSONObject.parseObject(str);
	}

}
