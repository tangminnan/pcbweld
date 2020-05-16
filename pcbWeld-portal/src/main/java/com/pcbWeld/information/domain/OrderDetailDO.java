package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 物料表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-16 13:38:45
 */
public class OrderDetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//订单表id
	private String orderId;
	//用户ID
	private Long userId;
	//创建时间
	private Date createTime;
	//金额
	private BigDecimal amount;
	//数量
	private String buyNumber;
	//名称
	private String name;
	//
	private String address;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：订单表id
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单表id
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：数量
	 */
	public void setBuyNumber(String buyNumber) {
		this.buyNumber = buyNumber;
	}
	/**
	 * 获取：数量
	 */
	public String getBuyNumber() {
		return buyNumber;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：
	 */
	public String getAddress() {
		return address;
	}
}
