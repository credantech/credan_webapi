/**
 * @(#) MyBatisDao.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.mybatis.conf;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyBatisDao
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月1日 下午6:56:39 $
 */
public interface MyBatisDao<T> extends Mapper<T>, MySqlMapper<T> {

}
