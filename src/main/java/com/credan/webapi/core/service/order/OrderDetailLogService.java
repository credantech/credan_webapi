/**
 * @(#) OrderDetailLogService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.order;

import org.springframework.stereotype.Service;

import com.credan.webapi.core.dao.entity.order.OrderDetailLog;
import com.credan.webapi.core.dao.mapper.order.OrderDetailLogDao;
import com.credan.webapi.core.service.BasicService;

/**
 * 订单明细日志记录
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 上午11:36:33 $
 */
@Service
public class OrderDetailLogService extends BasicService<OrderDetailLogDao, OrderDetailLog> {

	public int save(OrderDetailLog log) {
		return super.save(log);
	}

}
