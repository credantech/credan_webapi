/**
 * @Project: credan_common @(#) Json.java
 * 
 * Copyright (c) 2016, Credan-版权所有
 * 
 */
package com.credan.webapi.comm.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 单利模式实现Json工具类
 * 
 * Class <code>Json</code>
 * 
 * @Time 2016年5月13日 下午5:19:19
 * @author: Mond
 * @version 1.0.0 ,2016年5月13日
 */
public enum Json implements Function {

	ObjectMapper {
		private ObjectMapper mapper = new ObjectMapper();

		@Override
		public <T> String writeValue(T t) {
			if (null == t)
				return null;
			try {
				return mapper.writeValueAsString(t);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public <T> T fromJson(String jsonString, Class<T> clazz) {
			if (StringUtils.isBlank(jsonString))
				return null;
			try {
				return mapper.readValue(jsonString, clazz);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public <T> T fromJson(String jsonString, JavaType javaType) {
			if (StringUtils.isBlank(jsonString))
				return null;
			try {
				return mapper.readValue(jsonString, javaType);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
			return mapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass, elementClasses);
		}

		@Override
		public ObjectMapper getMapper() {
			return mapper;
		}

	}

}

interface Function {
	
	ObjectMapper getMapper();
	
	/**
	 * javaBean 转换为Json字符串
	 */
	<T> String writeValue(T t);

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
	 * 
	 * @see #fromJson(String, JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz);

	/**
	 * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
	 * 
	 * @see #createCollectionType(Class, Class...)
	 */
	public <T> T fromJson(String jsonString, JavaType javaType);

	/**
	 * 构造泛型的Collection Type如: ArrayList<MyBean>,
	 * 则调用constructCollectionType(ArrayList.class,MyBean.class)
	 * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
	 */
	public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses);
}