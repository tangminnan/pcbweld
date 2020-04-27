package com.pcbWeld.users.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResultAcupunctureDo {
	
	//id
	private Integer tAcupunctureId;
	
	//用户id
	private Integer userId;
	
	//第一次检查时间
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date firstCheckDate;
	
	//第二次检查时间
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private  Date secondCheckDate;
	
	//第一次检查单位
	private String firstCheckCompany;
	
	private String secondCheckCompany;

	public String getSecondCheckCompany() {
		return secondCheckCompany;
	}

	public void setSecondCheckCompany(String secondCheckCompany) {
		this.secondCheckCompany = secondCheckCompany;
	}

	//第一次检查医生
	private String firstCheckDoctor;
	
	private String secondCheckDoctor;

	public Integer gettAcupunctureId() {
		return tAcupunctureId;
	}

	public void settAcupunctureId(Integer tAcupunctureId) {
		this.tAcupunctureId = tAcupunctureId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getFirstCheckDate() {
		return firstCheckDate;
	}

	public void setFirstCheckDate(Date firstCheckDate) {
		this.firstCheckDate = firstCheckDate;
	}

	public Date getSecondCheckDate() {
		return secondCheckDate;
	}

	public void setSecondCheckDate(Date secondCheckDate) {
		this.secondCheckDate = secondCheckDate;
	}

	public String getFirstCheckCompany() {
		return firstCheckCompany;
	}

	public void setFirstCheckCompany(String firstCheckCompany) {
		this.firstCheckCompany = firstCheckCompany;
	}

	public String getFirstCheckDoctor() {
		return firstCheckDoctor;
	}

	public void setFirstCheckDoctor(String firstCheckDoctor) {
		this.firstCheckDoctor = firstCheckDoctor;
	}

	public String getSecondCheckDoctor() {
		return secondCheckDoctor;
	}

	public void setSecondCheckDoctor(String secondCheckDoctor) {
		this.secondCheckDoctor = secondCheckDoctor;
	}
	
	
	
	
}
