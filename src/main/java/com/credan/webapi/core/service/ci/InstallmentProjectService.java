/**
 * @(#) InstallmentProjectService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.ci;

import org.springframework.stereotype.Service;

import com.credan.webapi.core.dao.entity.ci.InstallmentProject;
import com.credan.webapi.core.dao.mapper.ci.InstallmentProjectDao;
import com.credan.webapi.core.service.BasicService;
import com.google.common.base.Strings;

/**
 * 分期项目表
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月8日 下午5:40:53 $
 */
@Service
public class InstallmentProjectService extends BasicService<InstallmentProjectDao, InstallmentProject> {

	public InstallmentProject findOne(String projectId) {
		if (Strings.isNullOrEmpty(projectId)) {
			return null;
		}
		return dao.selectByPrimaryKey(projectId);
	}

}
