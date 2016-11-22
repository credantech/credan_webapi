package com.credan.webapi.core.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.credan.webapi.core.mongo.entity.MerchantUserEntity;

public interface MerchantUserReposityService extends MongoRepository<MerchantUserEntity, String>{
	
	MerchantUserEntity findOneByToken(String token);

	
}
