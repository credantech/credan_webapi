package com.credan.webapi.core.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.util.UUIDUtils;
import com.credan.webapi.config.jersey.api.entity.StatusCodeEnum;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;
import com.credan.webapi.config.jersey.api.exception.ParamException;
import com.credan.webapi.core.dao.entity.merchant.MerchantCashLoanApply;
import com.credan.webapi.core.service.AbstractBasicService;
import com.credan.webapi.core.service.merchant.MerchantCashLoanApplyService;
import com.credan.webapi.core.service.merchant.MerchantInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 美差Service
 * @author Mond
 *
 */
@Slf4j
@Service
public class MeichaiService extends AbstractBasicService {

	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantCashLoanApplyService merchantCashLoanApplyService;

	public ResultVo index(JSONObject param) {
		checkNotNull(param, "amount", "merchantId", "term");
		String merchantId = param.getString("merchantId");
		boolean checkStatus = merchantInfoService.checkStatus(merchantId);
		if (!checkStatus) {
			log.error("美差商户ID无效=========MerchantId:{}", merchantId);
			ParamException paramException = new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR);
			paramException.setMsg("商户ID无效");
			throw paramException;
		}
		String token = "token_"+UUIDUtils.getUUID();
		MerchantCashLoanApply apply = new MerchantCashLoanApply();
		apply.setApplyTerm(param.getLong("term"));
		apply.setApplyAmount(param.getBigDecimal("amount"));
		apply.setMerchanrtId(merchantId);
		apply.setSource("MEICHAI");
		apply.setTermUnit("D");
		apply.setToken(token);
		merchantCashLoanApplyService.saveSelective(apply);
		ResultVo resultVo = new ResultVo(true);
		resultVo.putValue("token", token);
		resultVo.setStatusCode(StatusCodeEnum.GENERATE_TOKEN_SUCCESS.getCode());
		return resultVo;
	}

}
