/**
 * @(#) BasicService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.credan.webapi.comm.enums.DelFlagEnum;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.UUIDUtils;
import com.credan.webapi.config.mybatis.conf.MyBatisDao;
import com.credan.webapi.core.dao.entity.BasicEntity;

/**
 * Service层基础
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午1:22:49 $
 */
public abstract class BasicService<D extends MyBatisDao<T>, T extends BasicEntity> extends AbstractBasicService {

	@Autowired
	private D dao;

	protected int save(T t) {
		if (t.isNewRecord()) {
			t.setId(UUIDUtils.getUniqueUUID());
			t.setDelFlag(DelFlagEnum.FALSE.getCode());
			t.setCreatedTime(DateHelper.getCurrentTime());
			t.setVersion(Long.valueOf(0));
			return dao.insert(t);
		}
		t.setLastUpdated(DateHelper.getCurrentTime());
		return dao.updateByPrimaryKeySelective(t);
	}

}
