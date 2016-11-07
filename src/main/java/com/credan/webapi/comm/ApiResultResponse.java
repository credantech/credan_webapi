/**
 * 
 */
package com.credan.webapi.comm;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author george
 * @version 创建时间：2016年8月1日 下午1:18:38
 */
public class ApiResultResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4174089391636989571L;

	private int statusCode;

	private String message;

	private Object data;
	
	private Object otherMsg;
	
	private static ObjectMapper mapper = new ObjectMapper();

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static void setMapper(ObjectMapper mapper) {
		ApiResultResponse.mapper = mapper;
	}

	public Object getOtherMsg() {
		return otherMsg;
	}

	public void setOtherMsg(Object otherMsg) {
		this.otherMsg = otherMsg;
	}

	
	@Override
	public String toString() {
		
		try {
			mapper.setSerializationInclusion(Include.NON_NULL);
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
