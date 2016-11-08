/**
 * @(#) ZLJService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.api.zlj;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.enums.ConstantEnums;
import com.credan.webapi.comm.enums.ConstantEnums.CallBackResultEnum;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.config.AppConfig;
import com.credan.webapi.config.jersey.api.entity.RequestVo;
import com.credan.webapi.core.dao.entity.order.OrderDetail;
import com.credan.webapi.core.dao.entity.order.OrderDetailLog;
import com.credan.webapi.core.dao.entity.order.OrderDetailVo;
import com.credan.webapi.core.dao.mapper.order.OrderDetailDao;
import com.credan.webapi.core.service.AbstractBasicService;
import com.credan.webapi.core.service.app.CredanService;
import com.credan.webapi.core.service.order.OrderDetailLogService;
import com.credan.webapi.core.service.order.OrderDetailService;
import com.credan.webapi.core.service.security.SignService;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 找靓机Service
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午1:22:17 $
 */
@Slf4j
@Service
public class ZLJService extends AbstractBasicService {

	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private OrderDetailLogService orderDetailLogService;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Inject
	private AppConfig appConfig;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private SignService signService;
	
	@Autowired
	private CredanService CredanService;


	/**
	 * 商户跳入解析数据（该接口由前端转发进入）
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResultVo index(String params) {
		RequestVo requestVo = signService.processInputParams(JSONObject.parseObject(params, RequestVo.class));
		JSONObject param = JSONObject.parseObject(requestVo.toString());
		checkNotNull(param, "merchantId", "data");
		String merchantId = param.getString("merchantId");
		JSONObject data = param.getJSONObject("data");
		checkNotNull(data, "orderId", "itemPrice", "itemAmt", "itemName");
		
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
		record.setCallBackCount(Long.valueOf("0"));
		record.setOrderId(orderId);
		record.setCount(Long.valueOf(itemAmt));
		record.setMerchantId(merchantId);
		record.setName(itemName);
		record.setOrderAmount(itemPrice);
		record.setPrice(itemPrice);
		record.setTerm(tenorApplied == null ? null : Long.valueOf(tenorApplied));
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
		
		Map<String, Object> resultData = CredanService.calculate(data);
		
		ResultVo resultVo = new ResultVo(true);
		resultVo.putValue(resultData);
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
		JSONObject data = param.getJSONObject("data");
		checkNotNull(data, "orderIds");
		JSONArray orderIds = data.getJSONArray("orderIds");
		int total = 0;
		if (null == orderIds || orderIds.size() == 0) {
			ResultVo resultVo = new ResultVo(true);
			resultVo.putValue("total", total);
			resultVo.putValue("result", Lists.newArrayList());
			return resultVo;
		}
		List<String> ids = Lists.transform(Lists.newArrayList(orderIds.iterator()), new Function<Object, String>() {
			@Override
			public String apply(Object arg0) {
				return arg0.toString();
			}
		});
		List<OrderDetailVo> findDetails = orderDetailDao.findDetails(ids);
		findDetails = null == findDetails ? Lists.newArrayList() : findDetails;
		ResultVo resultVo = new ResultVo(true);
		resultVo.putValue("total", total);
		resultVo.putValue("result", findDetails);
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
		String projectId = param.getString("projectId");

		OrderDetail orderDetail = orderDetailDao.findOneByProjectId(projectId);
		if (null == orderDetail) {
			log.error("notify projectId find OrderDetail is null , projectId : {}", projectId);
			return new ResultVo(false);
		}
		JSONObject reqParam = new JSONObject();
		reqParam.put("orderId", orderDetail.getOrderId());
		reqParam.put("subType", ConstantEnums.NotifySubTypeEnum.PAID_SUCCESS.getCode());
		reqParam.put("ext", null);
		String zljNotifyUrl = appConfig.getZljNotifyUrl();
		String post = restTemplate.postForObject(zljNotifyUrl, reqParam, String.class);

		Date currentTime = DateHelper.getCurrentTime();
		orderDetail.setCallBackCount(orderDetail.getCallBackCount() + 1);
		orderDetail.setCallBackResult(Strings.isNullOrEmpty(post) ? CallBackResultEnum.FAIL.toString() : post);
		orderDetail.setCallBackTime(currentTime);

		switch (orderDetail.getCallBackCount().intValue()) {
		case 1:
			orderDetail.setNextCallBackTime(DateHelper.addMinute(currentTime, 5));
			break;
		case 2:
			orderDetail.setNextCallBackTime(DateHelper.addMinute(currentTime, 5));
			break;
		case 3:
			orderDetail.setNextCallBackTime(null);
			break;
		default:
			break;
		}
		orderDetailService.save(orderDetail);
		ResultVo resultVo = new ResultVo(true);
		resultVo.putValue("status", post);
		return resultVo;
	}

}
