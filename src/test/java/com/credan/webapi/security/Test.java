/**
 * @(#) Test.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.security;


import java.net.URLDecoder;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.util.security.DESHelper;
import com.credan.webapi.comm.util.security.DESUtil;

/**   
 * 
 *  
 * @author  Mond
 * @version 1.0.0, $Date: 2016年11月8日 下午3:52:40 $ 
 */
public class Test{

	@org.junit.Test
	public void test() throws Exception{
		String url = "%7B%22itemName%22%3A%2295%5Cu65b0+iPhone+6S+16G%5Cu9ed1%22%2C%22itemAmt%22%3A1%2C%22itemPrice%22%3A%223050.00%22%2C%22orderId%22%3A%22M161014341509734%22%2C%22merchantId%22%3A%2252a3298cdd7e4bfaa245a6b93b205d39%22%2C%22data%22%3A%227dEdzp6gh0OFI1XH5JVN4R88%2BSXRY0luTtvMh9gMwG%5C%2F44j7c1PJCTnw3JaMSGA9o2UVvIkV%5C%2FvZ1Mpff6UeKyoDeNWnd%2B%5C%2FIoGhkobOXctRRkIFzvtdbUgxlHBkDwk65QEWbMfRSXIIEaiWSoMTLAPSSqmuLxNSfY4Pc2EdNS32yIGFW9%2BZ7qhthzXRtzbsJVmwfsAti3CSPzZDgLAv5T3AA%3D%3D%22%2C%22sign%22%3A%22bIv%5C%2FbSZmElHFoP%2B4KEM7bZhzjfUFpPFmXGQHpTLx1inty3vELvIVmoNdCQFoTD6OKTShTaMdeNt2I0oUisk7eH%5C%2FMy7Mppn4DPT1FYo18Qt5nUYklQ9bwOilTjPNfZNsuXlnW1r8GhKJhk5kof8UQnoyvTPehP5rBCxMHmlfQsiE%3D%22%2C%22resultEncrypt%22%3A1%2C%22timestamp%22%3A%222016-11-08+15%3A25%3A00%22%7D";
		String decode = URLDecoder.decode(url, "utf-8");
		System.err.println(decode);
		JSONObject parseObject = JSONObject.parseObject(decode);
		String data = parseObject.getString("data");
		String decrypt = DESHelper.decrypt(data, "lJ2eSRDZ");
		System.err.println(decrypt);
		System.err.println(DESUtil.decrypt(data, "lJ2eSRDZlJ2eSRDZ"));
	}
}
