package com.pcbWeld.owneruser.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-27 09:55:49
 */
public class OwnerUserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
		//头像附件
		private MultipartFile fileImg; 
	 	//id
		private Long id;
		//账号
		private Long accountNumber;
		//微信id
		private String openId;
		//昵称
		private String nickname;
		//密码
		private String password;
		//手机号
		private String phone;
		//头像
		private String heardUrl;
		//真实姓名
		private String name;
		//身份证号
		private String identityCard;
		//qq标识
		private String unionid;
		//注册时间
		private Date registerTime;
		//消费金额
		private Double payNum;
		//服务次数
		private Integer serveNum;
		//最后登录时间
		private Date loginTime;
		//添加时间
		private Date addTime;
		//修改时间
		private Date updateTime;
		//0：正常；1：禁止
		private Integer deleteFlag;
		//
		private String username;
		//性别：值为1时是男性，值为2时是女性，值为0时是未知
		private Integer sex;
		//出生年月
		private Date birthday;
		//地址
		private String address;
		//公司名称
		private String company;
		
		private String qqNumber;

		//发票 开票公司名称
		private String receiptCompany;
		//发票  开票税号
		private String receiptNumber;
		//发票  开户行
		private String receiptBank;
		//发票  开户账号
		private String receiptAccount;
		//发票  开票联系电话
		private String receiptPhone;
		//发票  开票公司联系地址
		private String receiptAddress;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getReceiptCompany() {
		return receiptCompany;
	}

	public void setReceiptCompany(String receiptCompany) {
		this.receiptCompany = receiptCompany;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getReceiptBank() {
		return receiptBank;
	}

	public void setReceiptBank(String receiptBank) {
		this.receiptBank = receiptBank;
	}

	public String getReceiptAccount() {
		return receiptAccount;
	}

	public void setReceiptAccount(String receiptAccount) {
		this.receiptAccount = receiptAccount;
	}

	public String getReceiptPhone() {
		return receiptPhone;
	}

	public void setReceiptPhone(String receiptPhone) {
		this.receiptPhone = receiptPhone;
	}

	public String getReceiptAddress() {
		return receiptAddress;
	}

	public void setReceiptAddress(String receiptAddress) {
		this.receiptAddress = receiptAddress;
	}

	public String getQqNumber() {
			return qqNumber;
		}
		public void setQqNumber(String qqNumber) {
			this.qqNumber = qqNumber;
		}
		/**
		 * 设置：id
		 */
		public void setId(Long id) {
			this.id = id;
		}
		/**
		 * 获取：id
		 */
		public Long getId() {
			return id;
		}
		/**
		 * 设置：账号
		 */
		public void setAccountNumber(Long accountNumber) {
			this.accountNumber = accountNumber;
		}
		/**
		 * 获取：账号
		 */
		public Long getAccountNumber() {
			return accountNumber;
		}
		/**
		 * 设置：微信id
		 */
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		/**
		 * 获取：微信id
		 */
		public String getOpenId() {
			return openId;
		}
		/**
		 * 设置：昵称
		 */
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		/**
		 * 获取：昵称
		 */
		public String getNickname() {
			return nickname;
		}
		/**
		 * 设置：密码
		 */
		public void setPassword(String password) {
			this.password = password;
		}
		/**
		 * 获取：密码
		 */
		public String getPassword() {
			return password;
		}
		/**
		 * 设置：手机号
		 */
		public void setPhone(String phone) {
			this.phone = phone;
		}
		/**
		 * 获取：手机号
		 */
		public String getPhone() {
			return phone;
		}
		/**
		 * 设置：头像
		 */
		public void setHeardUrl(String heardUrl) {
			this.heardUrl = heardUrl;
		}
		/**
		 * 获取：头像
		 */
		public String getHeardUrl() {
			return heardUrl;
		}
		/**
		 * 设置：真实姓名
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * 获取：真实姓名
		 */
		public String getName() {
			return name;
		}
		/**
		 * 设置：身份证号
		 */
		public void setIdentityCard(String identityCard) {
			this.identityCard = identityCard;
		}
		/**
		 * 获取：身份证号
		 */
		public String getIdentityCard() {
			return identityCard;
		}
		/**
		 * 设置：qq标识
		 */
		public void setUnionid(String unionid) {
			this.unionid = unionid;
		}
		/**
		 * 获取：qq标识
		 */
		public String getUnionid() {
			return unionid;
		}
		/**
		 * 设置：注册时间
		 */
		public void setRegisterTime(Date registerTime) {
			this.registerTime = registerTime;
		}
		/**
		 * 获取：注册时间
		 */
		public Date getRegisterTime() {
			return registerTime;
		}
		/**
		 * 设置：消费金额
		 */
		public void setPayNum(Double payNum) {
			this.payNum = payNum;
		}
		/**
		 * 获取：消费金额
		 */
		public Double getPayNum() {
			return payNum;
		}
		/**
		 * 设置：服务次数
		 */
		public void setServeNum(Integer serveNum) {
			this.serveNum = serveNum;
		}
		/**
		 * 获取：服务次数
		 */
		public Integer getServeNum() {
			return serveNum;
		}
		/**
		 * 设置：最后登录时间
		 */
		public void setLoginTime(Date loginTime) {
			this.loginTime = loginTime;
		}
		/**
		 * 获取：最后登录时间
		 */
		public Date getLoginTime() {
			return loginTime;
		}
		/**
		 * 设置：添加时间
		 */
		public void setAddTime(Date addTime) {
			this.addTime = addTime;
		}
		/**
		 * 获取：添加时间
		 */
		public Date getAddTime() {
			return addTime;
		}
		/**
		 * 设置：修改时间
		 */
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		/**
		 * 获取：修改时间
		 */
		public Date getUpdateTime() {
			return updateTime;
		}
		/**
		 * 设置：0：正常；1：禁止
		 */
		public void setDeleteFlag(Integer deleteFlag) {
			this.deleteFlag = deleteFlag;
		}
		/**
		 * 获取：0：正常；1：禁止
		 */
		public Integer getDeleteFlag() {
			return deleteFlag;
		}
		/**
		 * 设置：
		 */
		public void setUsername(String username) {
			this.username = username;
		}
		/**
		 * 获取：
		 */
		public String getUsername() {
			return username;
		}
		/**
		 * 设置：性别：值为1时是男性，值为2时是女性，值为0时是未知
		 */
		public void setSex(Integer sex) {
			this.sex = sex;
		}
		/**
		 * 获取：性别：值为1时是男性，值为2时是女性，值为0时是未知
		 */
		public Integer getSex() {
			return sex;
		}
		/**
		 * 设置：出生年月
		 */
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		/**
		 * 获取：出生年月
		 */
		public Date getBirthday() {
			return birthday;
		}
		/**
		 * 设置：地址
		 */
		public void setAddress(String address) {
			this.address = address;
		}
		/**
		 * 获取：地址
		 */
		public String getAddress() {
			return address;
		}
		/**
		 * 设置：公司名称
		 */
		public void setCompany(String company) {
			this.company = company;
		}
		/**
		 * 获取：公司名称
		 */
		public String getCompany() {
			return company;
		}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public MultipartFile getFileImg() {
		return fileImg;
	}
	public void setFileImg(MultipartFile fileImg) {
		this.fileImg = fileImg;
	}
	

}
