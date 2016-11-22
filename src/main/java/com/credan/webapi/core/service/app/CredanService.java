package com.credan.webapi.core.service.app;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.util.Arith;
import com.credan.webapi.comm.util.CalculatorUtil;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.UUIDUtils;
import com.credan.webapi.core.mongo.entity.MerchantUserEntity;
import com.credan.webapi.core.mongo.repository.MerchantUserReposityService;
import com.credan.webapi.core.service.AbstractBasicService;

@Service
public class CredanService extends AbstractBasicService {

	@Autowired
	private MerchantUserReposityService merchantUserReposityService;

	/**
	 * 计算分期
	 * @param installment
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> calculate(JSONObject data, String token) {
		BigDecimal itemPrice = data.getBigDecimal("itemPrice");
		Integer itemAmt = data.getInteger("itemAmt");
		String merchantId = data.getString("merchantId");
		String orderId = data.getString("orderId");
		String projectId = data.getString("projectId");

		BigDecimal orderAmount = Arith.mul(itemPrice, new BigDecimal(itemAmt));
		@SuppressWarnings("static-access")
		Map<String, Object> map = data.toJavaObject(data, Map.class);
		Map<String, Object> installments = CalculatorUtil.getACPI(orderAmount);
		map.put("installments", installments);
		map.put("orderAmount", orderAmount);
		merchantUserReposityService.delete(MerchantUserEntity.builder().token(token).build());
		MerchantUserEntity.MerchantUserEntityBuilder builder = MerchantUserEntity.builder();
		builder.token(token).orderId(orderId).createTime(DateHelper.getCurrentTime())
				.installment(orderAmount.doubleValue()).orderAmount(orderAmount.doubleValue()).merchantId(merchantId)
				.desc("inputInfo").projectId(projectId);
		MerchantUserEntity entity = builder.build();
		merchantUserReposityService.save(entity);
		map.put("token", token);
		return map;
	}

	/**
	 * 生成token并保存mongo
	 * 
	 * @param orderAmount
	 * @param merchantId
	 * @param term
	 * @return
	 */
	public String generateToken(BigDecimal orderAmount, String merchantId, Integer term) {
		String token = "token_" + UUIDUtils.getUUID();
		orderAmount = null == orderAmount ? BigDecimal.ZERO : orderAmount;
		MerchantUserEntity.MerchantUserEntityBuilder builder = MerchantUserEntity.builder();
		builder.token(token).createTime(DateHelper.getCurrentTime()).installment(orderAmount.doubleValue())
				.orderAmount(orderAmount.doubleValue()).merchantId(merchantId).desc("inputInfo")
				.term(null == term ? 0 : term);
		MerchantUserEntity entity = builder.build();
		MerchantUserEntity save = merchantUserReposityService.save(entity);
		return save.getToken();
	}

}
