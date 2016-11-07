/**
 * @(#) OrderDetailVo.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.dao.entity.order;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 查询订单明细响应结果
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月4日 下午1:25:42 $
 */
@Data
public class OrderDetailVo {

	private String orderId;

	private String applyDate;

	private String userId;

	private String status;

	private String statusDesc;

	private BigDecimal loanAmt;

	private Integer loanTnr;

	private BigDecimal loanMonthRate;
}
