/**
 * @(#) MerchantInfoService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.merchant;

import org.springframework.stereotype.Service;

import com.credan.webapi.comm.enums.ConstantEnums.SwitchEnum;
import com.credan.webapi.core.dao.entity.merchant.MerchantInfo;
import com.credan.webapi.core.dao.mapper.merchant.MerchantInfoDao;
import com.credan.webapi.core.service.BasicService;
import com.google.common.base.Strings;

/**
 * 商户Service
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月7日 下午7:14:09 $
 */
@Service
public class MerchantInfoService extends BasicService<MerchantInfoDao, MerchantInfo> {

	/**
	 * 校验商户是否可用
	 * 
	 * @param merchantId
	 * @return
	 */
	public boolean checkStatus(String merchantId) {
		if (Strings.isNullOrEmpty(merchantId)) {
			return false;
		}
		MerchantInfo merchantInfo = dao.selectByPrimaryKey(merchantId);
		return merchantInfo != null && SwitchEnum.ENABLED.toString().equals(merchantInfo.getStatus());
	}

}
