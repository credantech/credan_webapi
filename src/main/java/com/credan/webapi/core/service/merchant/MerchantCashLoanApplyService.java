/**
 * @(#) MerchantCashLoanApplyService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.merchant;

import org.springframework.stereotype.Service;

import com.credan.webapi.core.dao.entity.merchant.MerchantCashLoanApply;
import com.credan.webapi.core.dao.mapper.merchant.MerchantCashLoanApplyDao;
import com.credan.webapi.core.service.BasicService;

/**   
 * 
 *  
 * @author  Mond
 * @version 1.0.0, $Date: 2016年11月21日 上午11:52:37 $ 
 */
@Service
public class MerchantCashLoanApplyService extends BasicService<MerchantCashLoanApplyDao, MerchantCashLoanApply>{
	
	@Override
	public int saveSelective(MerchantCashLoanApply t) {
		return super.saveSelective(t);
	}

}
