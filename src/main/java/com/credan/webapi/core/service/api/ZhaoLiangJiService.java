/**
 * @(#) ZLJService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.api;

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
import com.credan.webapi.comm.util.Arith;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.UUIDUtils;
import com.credan.webapi.comm.util.security.AESHelper;
import com.credan.webapi.comm.util.security.RSAHelper;
import com.credan.webapi.config.AppConfig;
import com.credan.webapi.config.jersey.api.entity.RequestVo;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;
import com.credan.webapi.config.jersey.api.exception.ParamException;
import com.credan.webapi.core.dao.entity.ci.InstallmentProject;
import com.credan.webapi.core.dao.entity.order.OrderDetail;
import com.credan.webapi.core.dao.entity.order.OrderDetailLog;
import com.credan.webapi.core.dao.entity.order.OrderDetailVo;
import com.credan.webapi.core.dao.entity.sys.SysDictionary;
import com.credan.webapi.core.dao.mapper.order.OrderDetailDao;
import com.credan.webapi.core.service.AbstractBasicService;
import com.credan.webapi.core.service.app.CredanService;
import com.credan.webapi.core.service.ci.InstallmentProjectService;
import com.credan.webapi.core.service.order.OrderDetailLogService;
import com.credan.webapi.core.service.order.OrderDetailService;
import com.credan.webapi.core.service.security.SignService;
import com.credan.webapi.core.service.sys.SysDictionaryService;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 找靓机Service
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午1:22:17 $
 */
@Slf4j
@Service
public class ZhaoLiangJiService extends AbstractBasicService {

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
	private InstallmentProjectService installmentProjectService;
	@Autowired
	private CredanService CredanService;
	@Autowired
	private SysDictionaryService sysDictionaryService;

	/** 字典表项目审批状态type */
	private static final String CI_PRODUCT_AUDIT_STATUS = "ci_product_audit_status";

	/** 项目表审批拒绝状态 */
	private static final String AUDIT_STATUS_REJECT = "REJECT";
	/** 项目表已支付状态 */
	private static final String AUDIT_STATUS_PAID = "PAID";

	/**
	 * 商户跳入解析数据（该接口由前端转发进入）
	 * 
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResultVo index(String params) {
		RequestVo requestVo = JSONObject.parseObject(params, RequestVo.class);
		JSONObject param = null;
		if ("uat".equalsIgnoreCase(appConfig.getSpringProfilesActive()) && "0".equals(requestVo.getTxnCode())) {
			param = JSONObject.parseObject(params);
		} else {
			requestVo = signService.processInputParams(requestVo);
			param = JSONObject.parseObject(requestVo.toString());
		}
		checkNotNull(param, "merchantId", "data");
		String merchantId = param.getString("merchantId");
		JSONObject data = param.getJSONObject("data");
		checkNotNull(data, "orderId", "itemPrice", "itemAmt", "itemName");

		String orderId = data.getString("orderId");
		Integer tenorApplied = data.getInteger("tenorApplied");
		tenorApplied = null == tenorApplied ? 1 : (tenorApplied.compareTo(0) < 0 ? 1 : tenorApplied);
		BigDecimal itemPrice = data.getBigDecimal("itemPrice");
		try {
			Preconditions.checkArgument(itemPrice.compareTo(BigDecimal.ZERO) > 0, "单价错误");
		} catch (Exception e) {
			throw new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR, "itemPrice");
		}
		Integer itemAmt = data.getInteger("itemAmt");
		itemAmt = itemAmt < 1 ? 1 : itemAmt;
		String itemName = data.getString("itemName");

		String unit = ConstantEnums.TermUnitEnum.M.toString();
		OrderDetail record = orderDetailDao.findOneByOrderId(orderId);
		if (null == record) {
			record = new OrderDetail();
			record.setProjectId(UUIDUtils.getUUID());
		} else {
			InstallmentProject findOne = installmentProjectService.findOne(record.getProjectId());
			if (null != findOne && (AUDIT_STATUS_PAID.equals(findOne.getAuditStatus())
					|| AUDIT_STATUS_REJECT.equals(findOne.getAuditStatus()))) {
				ParamException paramException = new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR, "orderId");
				paramException.setMsg("订单已被处理，请勿重新操作");
				throw paramException;
			}
		}
		JSONObject extJson = new JSONObject();
		extJson.put("recipientProvince", data.getString("recipientProvince"));
		extJson.put("recipientCity", data.getString("recipientCity"));
		extJson.put("recipientDistrict", data.getString("recipientDistrict"));
		extJson.put("recipientName", data.getString("recipientName"));
		extJson.put("recipientPhone", data.getString("recipientPhone"));
		extJson.put("recipientAddr", data.getString("recipientAddr"));
		String token = record.getToken();
		record.setCallBackCount(Long.valueOf("0"));
		record.setOrderId(orderId);
		record.setCount(Long.valueOf(itemAmt));
		record.setMerchantId(merchantId);
		record.setName(itemName);
		record.setOrderAmount(Arith.mul(itemPrice, new BigDecimal(itemAmt)));
		record.setPrice(itemPrice);
		record.setTerm(Long.valueOf(tenorApplied));
		record.setUnit(unit);
		record.setExt(extJson.toString());
		record.setSource("ZHAOLIANGJI");
		if (Strings.isNullOrEmpty(token)) {
			token = "token_" + org.apache.commons.codec.digest.DigestUtils.md5Hex(orderId);
			record.setToken(token);
		}
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
		log.setSource("ZHAOLIANGJI");
		log.setToken(record.getToken());
		log.setUserId(record.getUserId());
		log.setExt(extJson.toString());
		orderDetailLogService.saveSelective(log);
		data.put("projectId", record.getProjectId());
		data.putAll(extJson);
		;
		Map<String, Object> resultData = CredanService.calculate(data, token);

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
		Lists.transform(findDetails, new Function<OrderDetailVo, OrderDetailVo>() {
			@Override
			public OrderDetailVo apply(OrderDetailVo arg0) {
				if (Strings.isNullOrEmpty(arg0.getStatus())) {
					arg0.setStatus("PENDING");
					arg0.setStatusDesc("待审核");
				}
				return null;
			}
		});
		ResultVo resultVo = new ResultVo(true);
		resultVo.putValue("total", total);
		resultVo.putValue("result", findDetails);
		return resultVo;
	}

	/**
	 * 跑批回调
	 * 
	 * @param param
	 * @return
	 */
	public ResultVo jobNotify(String param) {
		List<OrderDetail> findList4Job = orderDetailDao.findList4Job();
		if (null != findList4Job) {
			for (OrderDetail orderDetail : findList4Job) {
				Date nextCallBackTime = orderDetail.getNextCallBackTime();
				if (null != nextCallBackTime) {
					int compare = DateHelper.compare(DateHelper.getCurrentTime(), nextCallBackTime);
					if (compare < 0) {
						break;
					}
				}
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("projectId", orderDetail.getProjectId());
				try {
					notify(jsonObject);
				} catch (Exception e) {
					log.error("找靓机项目状态变更回调通知异常 ： ", e);
				}
			}
		}
		return new ResultVo(true);
	}

	/**
	 * 回调通知合作方
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ResultVo notify(JSONObject param) throws Exception {
		checkNotNull(param, "projectId");
		String projectId = param.getString("projectId");

		OrderDetail orderDetail = orderDetailDao.findOneByProjectId(projectId);
		if (null == orderDetail) {
			log.error("notify projectId find OrderDetail is null , projectId : {}", projectId);
			return new ResultVo(false);
		}
		InstallmentProject findOne = installmentProjectService.findOne(projectId);
		String auditStatus = findOne.getAuditStatus();
		// 审批拒绝和审批通过已付款的进行回调
		if (!AUDIT_STATUS_REJECT.equals(auditStatus) && !AUDIT_STATUS_PAID.equals(auditStatus)) {
			log.debug("notify project auditStatus is {}", auditStatus);
			orderDetail.setNextCallBackTime(null);
			orderDetail.setCount(Long.valueOf(0));
			orderDetailService.save(orderDetail);
			return new ResultVo(false);
		}
		JSONObject reqParam = new JSONObject();
		reqParam.put("orderId", orderDetail.getOrderId());
		if (auditStatus.equals(AUDIT_STATUS_REJECT)) {
			reqParam.put("subType", ConstantEnums.NotifySubTypeEnum.AUDIT_REJECT.getCode());
		} else if (auditStatus.equals(AUDIT_STATUS_PAID)) {
			reqParam.put("subType", ConstantEnums.NotifySubTypeEnum.PAID_SUCCESS.getCode());
		}
		JSONObject ext = new JSONObject();
		ext.put("status", auditStatus);
		SysDictionary sysDictionary = sysDictionaryService.findOneByDictTypeAndDictCode(CI_PRODUCT_AUDIT_STATUS,
				auditStatus);
		ext.put("statusDesc", null == sysDictionary ? "" : sysDictionary.getDictName());
		ext.put("loanAmout", findOne.getInstallmentAmount());

		reqParam.put("ext", ext);

		Map<String, Object> newHashMap = Maps.newHashMap();
		String encrypt = AESHelper.encrypt(reqParam.toString(), appConfig.getDesKey());
		newHashMap.put("data", encrypt);
		newHashMap.put("sign", RSAHelper.sign(encrypt, appConfig.getPrivateKey()));
		newHashMap.put("timestamp", DateHelper.getDateTime());
		String zljNotifyUrl = appConfig.getZljNotifyUrl();
		String jsonString = JSONObject.toJSONString(newHashMap);
		String post = restTemplate.postForObject(zljNotifyUrl, jsonString, String.class);
		log.debug("找靓机 回调   post: {}", post);
		Date currentTime = DateHelper.getCurrentTime();
		orderDetail.setCallBackCount(orderDetail.getCallBackCount() + 1);
		orderDetail.setCallBackResult(Strings.isNullOrEmpty(post) ? CallBackResultEnum.FAIL.toString() : post);
		orderDetail.setCallBackTime(currentTime);
		if (orderDetail.getCallBackResult().equalsIgnoreCase(CallBackResultEnum.SUCCESS.toString())) {
			orderDetail.setNextCallBackTime(null);
		} else {
			switch (orderDetail.getCallBackCount().intValue()) {
			case 1:
				orderDetail.setNextCallBackTime(DateHelper.addMinute(currentTime, 5));
				break;
			case 2:
				orderDetail.setNextCallBackTime(DateHelper.addMinute(currentTime, 15));
				break;
			case 3:
				orderDetail.setNextCallBackTime(null);
				break;
			default:
				orderDetail.setNextCallBackTime(null);
				break;
			}
		}

		orderDetailService.save(orderDetail);
		ResultVo resultVo = new ResultVo(true);
		resultVo.putValue("status", post);
		return resultVo;
	}

}
