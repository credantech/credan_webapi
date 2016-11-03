package com.credan.webapi.core.dao.mapper.order;

import com.credan.webapi.config.mybatis.conf.MyBatisDao;
import com.credan.webapi.core.dao.entity.order.OrderDetail;

public interface OrderDetailDao extends MyBatisDao<OrderDetail>{
    
    OrderDetail findOneByOrderId(String orderId);
    OrderDetail findOneByProjectId(String projectId);
}