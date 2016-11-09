package com.credan.webapi.core.dao.mapper.order;

import java.util.List;

import com.credan.webapi.config.mybatis.conf.MyBatisDao;
import com.credan.webapi.core.dao.entity.order.OrderDetail;
import com.credan.webapi.core.dao.entity.order.OrderDetailVo;

public interface OrderDetailDao extends MyBatisDao<OrderDetail> {

	OrderDetail findOneByOrderId(String orderId);

	OrderDetail findOneByProjectId(String projectId);

	/**
	 * 查询订单明细
	 * 
	 * @param ids
	 * @return
	 */
	List<OrderDetailVo> findDetails(List<String> ids);
	
	/**
	 * 查询需要回调通知状态变更的列表
	 * 
	 * @return
	 */
	List<OrderDetail> findList4Job();
}