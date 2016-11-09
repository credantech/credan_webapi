/**
 * @(#) OrderDetailService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credan.webapi.core.dao.entity.order.OrderDetail;
import com.credan.webapi.core.dao.mapper.order.OrderDetailDao;
import com.credan.webapi.core.service.BasicService;

/**
 * 订单Service
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 下午12:26:53 $
 */
@Service
public class OrderDetailService extends BasicService<OrderDetailDao, OrderDetail> {

	@Transactional(readOnly = false)
	public int saveSelective(OrderDetail record) {
		return super.saveSelective(record);
	}

	@Transactional(readOnly = false)
	public int save(OrderDetail record) {
		return super.save(record);
	}
}
