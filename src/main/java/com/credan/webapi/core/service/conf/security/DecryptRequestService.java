package com.credan.webapi.core.service.conf.security;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.security.AESHelper;
import com.credan.webapi.comm.util.security.RSAHelper;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;
import com.credan.webapi.config.jersey.api.exception.ParamException;
import com.credan.webapi.core.dao.entity.conf.MerchantRsaConfig;
import com.credan.webapi.core.service.conf.MerchantRsaConfigService;
import com.credan.webapi.core.service.merchant.MerchantInfoService;
import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DecryptRequestService extends AbstractRequestSecurity {

	@Value("${connect_time}")
	private int connetTime;

	@Autowired
	protected MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantRsaConfigService merchantRsaConfigService;

	@Override
	protected boolean checkReqTime(String timestamp) {
		Date timeDate = null;
		try {
			timeDate = DateHelper.addMinute(DateHelper.parseDate(timestamp, "yyyy-MM-dd HH:mm:ss"), connetTime);
		} catch (ParseException e) {
			log.error("时间格式异常======", e);
			throw new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR, "timestamp");
		}
		Date curDate = new Date();
		if (timeDate.compareTo(curDate) < 0) {
			log.warn("请求过期 ======timestamp:{}", timestamp);
			throw new ParamException(StatusEnum.INVALID);
		}
		return true;
	}

	@Override
	protected boolean checkMerchantId(String merchantId) {
		boolean checkStatus = merchantInfoService.checkStatus(merchantId);
		if (!checkStatus) {
			log.warn("商户ID无效=========MerchantId:{}", merchantId);
			ParamException paramException = new ParamException(StatusEnum.PROPERTY_LENGTH_ERROR);
			paramException.setMsg("商户ID无效");
			throw paramException;
		}
		return true;
	}

	@Override
	protected MerchantRsaConfig checkSign(String data, String sign, String merchantId) {
		MerchantRsaConfig merchantRsa = merchantRsaConfigService.findOneByMerchantId(merchantId);
		Preconditions.checkNotNull(merchantRsa, "商户公私钥配置为null，id=" + merchantId);
		boolean verify = false;
		try {
			verify = RSAHelper.verify(data, merchantRsa.getRsaPublicKey(), sign);
		} catch (Exception e) {
			log.error("RSA验签异常 ======", e);
			throw new ParamException(StatusEnum.INVALID_SIGN);
		}
		if (!verify) {
			log.debug("RSA验签失败=========sign:{}", sign);
			throw new ParamException(StatusEnum.INVALID_SIGN);
		}
		return merchantRsa;
	}

	@Override
	protected String decryptData(String data, String aesKey) {
		String decryptData;
		try {
			decryptData = AESHelper.decrypt(data, aesKey);
		} catch (Exception e) {
			log.error("DES解码异常======{}", e);
			throw new ParamException(StatusEnum.PARAM_FORMAT_ERROR);
		}

		return decryptData;
	}

}
