package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户地址
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-29 16:41:14
 */
public class UserAddressDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//用户ID
	private Long userId;
	//默认首选0:默认1：不是
	private String defaultFlag;
	//收货人
	private String consignee;
	//手机号码
	private String mobile;
	//收货地址
	private String dizhi;
	//详细地址
	private String address;
	//地址别名
	private String name;
	//创建时间
	private Date createTime;
	//省份
	private String provinceName;
	//城市
	private String cityName;
	//市区县
	private String areaName;


	public String getDizhi() {
		return dizhi;
	}

	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
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
	 * 设置：默认首选0:默认1：不是
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	/**
	 * 获取：默认首选0:默认1：不是
	 */
	public String getDefaultFlag() {
		return defaultFlag;
	}
	/**
	 * 设置：收货人
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	/**
	 * 获取：收货人
	 */
	public String getConsignee() {
		return consignee;
	}
	/**
	 * 设置：手机号码
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号码
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：详细地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：详细地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：地址别名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：地址别名
	 */
	public String getName() {
		return name;
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
	 * 设置：省份
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	/**
	 * 获取：省份
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * 设置：城市
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 获取：城市
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 设置：市区县
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取：市区县
	 */
	public String getAreaName() {
		return areaName;
	}
}
