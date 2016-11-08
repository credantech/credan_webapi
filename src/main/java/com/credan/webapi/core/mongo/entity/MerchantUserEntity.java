package com.credan.webapi.core.mongo.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "inputMsg")
public class MerchantUserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String merchantId;

	private double orderAmount;

	private double installment;

	private String userLatitude;

	private String userLongitude;

	@Indexed(unique=true)
	private String token;

	private int term;

	private String merchantPhone;

	private String projectId;
	
	//wx openId
	private String openId;
	
	//wx access_token
	private String accessToken;
	
	private String userId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;

	private Integer version = 0;

	private String desc;

	private double distance;
	
	private String wxId;
	
	private String wxData;
	
	private String orderId;

}
