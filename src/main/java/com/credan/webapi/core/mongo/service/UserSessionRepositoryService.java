/**
 * @(#) UserSessionRepositoryService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.mongo.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.UUIDUtils;
import com.credan.webapi.core.mongo.entity.UserSessionEntity;
import com.credan.webapi.core.mongo.repository.UserSessionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年8月11日 下午3:07:07 $
 */
@Slf4j
@Component
public class UserSessionRepositoryService {

	@Autowired
	protected MongoTemplate mongoTemplate;
	@Autowired
	private UserSessionRepository userSessionRepository;

	public UserSessionEntity insert(UserSessionEntity entity) {
		UserSessionEntity findOneByuserId = null;
		if (null == entity) {
			log.warn(" insert UserSessionEntity is null ...");
			return entity;
		}
		if (StringUtils.isNotBlank(entity.getUserId())) {
			findOneByuserId = userSessionRepository.findOneByUserId(entity.getUserId());
			if (null == findOneByuserId) {
				findOneByuserId = new UserSessionEntity();
				findOneByuserId.setId(UUIDUtils.getUniqueUUID());
				findOneByuserId.setCreateTime(DateHelper.getCurrentTime());
				findOneByuserId.setVersion(0);
				findOneByuserId.setUserId(entity.getUserId());
			} else {
				findOneByuserId.setUpdateTime(DateHelper.getCurrentTime());
				findOneByuserId.setVersion(findOneByuserId.getVersion() + 1);
			}
			findOneByuserId.setAuthorization(UUIDUtils.getUniqueUUID());
			findOneByuserId = userSessionRepository.save(findOneByuserId);
		}
		return findOneByuserId;
	}

	public UserSessionEntity update(UserSessionEntity entity) {
		if (null == entity) {
			log.warn(" update UserSessionEntity is null ...");
			return null;
		}
		entity.setUpdateTime(DateHelper.getCurrentTime());
		entity.setVersion(entity.getVersion() + 1);
		return userSessionRepository.save(entity);
	}

	public UserSessionEntity findOneByAuthorization(String authorization) {
		if (StringUtils.isBlank(authorization)) {
			log.warn(" findOneByAuthorization UserSessionEntity authorization null ...");
			return null;
		}
		return userSessionRepository.findOneByAuthorization(authorization);
	}
}
