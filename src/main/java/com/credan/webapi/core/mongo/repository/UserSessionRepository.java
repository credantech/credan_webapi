/**
 * @(#) UserSessionRepository.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.credan.webapi.core.mongo.entity.UserSessionEntity;

/**   
 * 
 *  
 * @author  Mond
 * @version 1.0.0, $Date: 2016年8月1日 下午2:35:43 $ 
 */
public interface UserSessionRepository extends MongoRepository<UserSessionEntity, String> {
	
	public UserSessionEntity findOneByAuthorization(String authorization);
	
	public UserSessionEntity findOneByUserId(String userId);
	
	

}
