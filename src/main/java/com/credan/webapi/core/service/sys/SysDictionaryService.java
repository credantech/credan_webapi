/**
 * @(#) SysDictionaryService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credan.webapi.core.dao.entity.sys.SysDictionary;
import com.credan.webapi.core.dao.mapper.sys.SysDictionaryDao;
import com.credan.webapi.core.service.AbstractBasicService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 字典表
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月8日 下午9:49:33 $
 */
@Service
public class SysDictionaryService extends AbstractBasicService {

	@Autowired
	private SysDictionaryDao sysDictionaryDao;

	public List<SysDictionary> findListByDictType(String dictType) {
		if (Strings.isNullOrEmpty(dictType)) {
			return null;
		}
		List<SysDictionary> findListByDictType = sysDictionaryDao.findListByDictType(dictType);
		return findListByDictType;
	}

	public SysDictionary findOneByDictTypeAndDictCode(String dictType, String dictCode) {
		if (Strings.isNullOrEmpty(dictType) || Strings.isNullOrEmpty(dictCode)) {
			return null;
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("dictType", dictType);
		map.put("dictCode", dictCode);
		return sysDictionaryDao.findOneByDictTypeAndDictCode(map);
	}

}
