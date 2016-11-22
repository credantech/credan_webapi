/**
 * @(#) ZhaoLiangJiServiceTests.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.service.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.credan.webapi.ApplicationTests;
import com.credan.webapi.core.service.api.ZhaoLiangJiService;

/**   
 * 
 *  
 * @author  Mond
 * @version 1.0.0, $Date: 2016年11月22日 上午11:05:01 $ 
 */
public class ZhaoLiangJiServiceTests extends ApplicationTests {
	
	@Autowired
	private ZhaoLiangJiService zhaoLiangJiService;

	@Test
	public void index(){
		String params = "{\"merchantId\":\"111\",\"data\":{\"orderId\":\"123456\",\"itemPrice\":100,\"itemAmt\":1,\"itemName\":\"Test\"}}";
		zhaoLiangJiService.index(params);
	}
	
}
