/**
 * @(#) ZLJService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.merchant.zlj;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.enums.ConstantEnums;
import com.credan.webapi.core.dao.entity.order.OrderDetail;
import com.credan.webapi.core.dao.entity.order.OrderDetailLog;
import com.credan.webapi.core.dao.mapper.order.OrderDetailDao;
import com.credan.webapi.core.service.AbstractBasicService;
import com.credan.webapi.core.service.order.OrderDetailLogService;
import com.credan.webapi.core.service.order.OrderDetailService;

/**
 * 找靓机Service
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午1:22:17 $
 */
@Service
public class ZLJService extends AbstractBasicService {

	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private OrderDetailLogService orderDetailLogService;
	@Autowired
	private OrderDetailDao orderDetailDao;

	/**
	 * 商户跳入解析数据
	 * 
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResultVo index(JSONObject param) {
		checkNotNull(param, "merchantId", "data");
		String merchantId = param.getString("merchantId");
		JSONObject data = param.getJSONObject("data");
		checkNotNull(data, "orderId", "tenorApplied", "itemPrice", "itemAmt", "itemName");
		String orderId = data.getString("orderId");
		Integer tenorApplied = data.getInteger("tenorApplied");
		BigDecimal itemPrice = data.getBigDecimal("itemPrice");
		Integer itemAmt = data.getInteger("itemAmt");
		String itemName = data.getString("itemName");

		String unit = ConstantEnums.TermUnitEnum.M.toString();
		OrderDetail record = orderDetailDao.findOneByOrderId(orderId);
		if (null == record) {
			record = new OrderDetail();
		}
		record.setOrderId(orderId);
		record.setCount(Long.valueOf(itemAmt));
		record.setMerchantId(merchantId);
		record.setName(itemName);
		record.setOrderAmount(itemPrice);
		record.setPrice(itemPrice);
		record.setTerm(Long.valueOf(tenorApplied));
		record.setUnit(unit);
		orderDetailService.save(record);

		OrderDetailLog log = new OrderDetailLog();
		log.setCount(Long.valueOf(itemAmt));
		log.setMerchantId(merchantId);
		log.setName(itemName);
		log.setOrderAmount(itemPrice);
		log.setOrderId(orderId);
		log.setPrice(itemPrice);
		log.setTerm(Long.valueOf(tenorApplied));
		log.setUnit(unit);
		orderDetailLogService.save(log);
		ResultVo resultVo = new ResultVo(true);
		@SuppressWarnings("unchecked")
		Map<String, Object> javaObject = data.toJavaObject(Map.class);
		resultVo.putValue(javaObject);
		return resultVo;
	}

	/**
	 * 查询订单信息
	 * 
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true)
	public ResultVo findOrders(JSONObject param) {
		checkNotNull(param, "orderIds");
		ResultVo resultVo = new ResultVo(true);
		return resultVo;
	}

	/**
	 * 回调通知合作方
	 * 
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true)
	public ResultVo notify(JSONObject param) {
		checkNotNull(param, "projectId");

		ResultVo resultVo = new ResultVo(true);
		return resultVo;
	}

}
