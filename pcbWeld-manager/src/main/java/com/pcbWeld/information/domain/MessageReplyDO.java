package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 留言回复表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-27 18:26:02
 */
public class MessageReplyDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//留言内容
	private String messageContent;
	//留言人id
	private Integer messageCreateId;
	//留言时间
	private Date messageTime;
	//状态1：正常2：禁止
	private Integer delFlag;
	//回复内容
	private String replyContent;
	//回复时间
	private Date replyTime;
	//回复id
	private Integer replyCreateId;
	//是否回复1：未回复2：已回复
	private Integer ifreply;
	//读取状态1：未读2：已读
	private Integer ifread;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：留言内容
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	/**
	 * 获取：留言内容
	 */
	public String getMessageContent() {
		return messageContent;
	}
	/**
	 * 设置：留言人id
	 */
	public void setMessageCreateId(Integer messageCreateId) {
		this.messageCreateId = messageCreateId;
	}
	/**
	 * 获取：留言人id
	 */
	public Integer getMessageCreateId() {
		return messageCreateId;
	}
	/**
	 * 设置：留言时间
	 */
	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	/**
	 * 获取：留言时间
	 */
	public Date getMessageTime() {
		return messageTime;
	}
	/**
	 * 设置：状态1：正常2：禁止
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：状态1：正常2：禁止
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
	/**
	 * 设置：回复内容
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	/**
	 * 获取：回复内容
	 */
	public String getReplyContent() {
		return replyContent;
	}
	/**
	 * 设置：回复时间
	 */
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	/**
	 * 获取：回复时间
	 */
	public Date getReplyTime() {
		return replyTime;
	}
	/**
	 * 设置：回复id
	 */
	public void setReplyCreateId(Integer replyCreateId) {
		this.replyCreateId = replyCreateId;
	}
	/**
	 * 获取：回复id
	 */
	public Integer getReplyCreateId() {
		return replyCreateId;
	}
	/**
	 * 设置：是否回复1：未回复2：已回复
	 */
	public void setIfreply(Integer ifreply) {
		this.ifreply = ifreply;
	}
	/**
	 * 获取：是否回复1：未回复2：已回复
	 */
	public Integer getIfreply() {
		return ifreply;
	}
	/**
	 * 设置：读取状态1：未读2：已读
	 */
	public void setIfread(Integer ifread) {
		this.ifread = ifread;
	}
	/**
	 * 获取：读取状态1：未读2：已读
	 */
	public Integer getIfread() {
		return ifread;
	}
}
