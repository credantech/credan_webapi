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
	public Map<String, Object> calculate(JSONObject data){
		BigDecimal itemPrice = data.getBigDecimal("itemPrice");
		Integer itemAmt = data.getInteger("itemAmt");
		String merchantId = data.getString("merchantId");
		String orderId = data.getString("orderId");
		
		BigDecimal installment = Arith.mul(itemPrice, new BigDecimal(itemAmt));
		@SuppressWarnings("static-access")
		Map<String, Object> map = data.toJavaObject(data, Map.class);
		Map<String, Object> installments = CalculatorUtil.getACPI(installment);
		map.put("installments", installments);
		String token = UUIDUtils.getUUID();
		MerchantUserEntity.MerchantUserEntityBuilder builder = MerchantUserEntity.builder();
		builder.token(token).orderId(orderId).createTime(DateHelper.getCurrentTime()).installment(installment.doubleValue()).orderAmount(installment.doubleValue())
		.merchantId(merchantId).desc("inputInfo");
		MerchantUserEntity entity = builder.build();
		merchantUserReposityService.save(entity);
		map.put("token", token);
		return map;
	}

}
