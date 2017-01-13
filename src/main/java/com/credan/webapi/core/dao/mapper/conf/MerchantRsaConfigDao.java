package com.credan.webapi.core.dao.mapper.conf;

import com.credan.webapi.config.mybatis.conf.MyBatisDao;
import com.credan.webapi.core.dao.entity.conf.MerchantRsaConfig;

public interface MerchantRsaConfigDao extends MyBatisDao<MerchantRsaConfig>{
	
	public MerchantRsaConfig findOneByMerchantId(String merchantId);
}