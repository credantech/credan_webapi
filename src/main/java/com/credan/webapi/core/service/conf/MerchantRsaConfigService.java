/**
 * @(#) MerchantRsaConfigService.java
 * 
 * Copyright (c) 2017, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.conf;

import org.springframework.stereotype.Service;

import com.credan.webapi.core.dao.entity.conf.MerchantRsaConfig;
import com.credan.webapi.core.dao.mapper.conf.MerchantRsaConfigDao;
import com.credan.webapi.core.service.BasicService;
import com.google.common.base.Strings;

/**   
 * 商户公私钥配置
 *  
 * @author  Mond
 * @version 1.0.0, $Date: 2017年1月13日 上午11:04:48 $ 
 */
@Service
public class MerchantRsaConfigService extends BasicService<MerchantRsaConfigDao, MerchantRsaConfig> {
	
	public MerchantRsaConfig findOneByMerchantId(String merchantId){
		if (Strings.isNullOrEmpty(merchantId)) {
			return null;
		}
		return dao.findOneByMerchantId(merchantId);
	}
	
}
