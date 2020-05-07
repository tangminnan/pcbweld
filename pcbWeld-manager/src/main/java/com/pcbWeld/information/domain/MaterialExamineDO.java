package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 资料审核
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 11:28:36
 */
public class MaterialExamineDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Integer id;
    //用户id
    private Long userId;
    //订单id
    private Long orderId;
    //附件
    private String files;
    //订单金额
    private String orderMoney;
    //审核状态1：未审核2：已审核3：审核不通过
    private Integer examineFlag;
    //审核备注/意见
    private String examineIdea;
    //添加时间
    private Date addTime;
    //更新时间
    private Date updateTime;
    //类型1：附件2：物料
    private Integer type;

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
     * 设置：用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取：订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置：附件
     */
    public void setFiles(String files) {
        this.files = files;
    }

    /**
     * 获取：附件
     */
    public String getFiles() {
        return files;
    }

    /**
     * 设置：订单金额
     */
    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    /**
     * 获取：订单金额
     */
    public String getOrderMoney() {
        return orderMoney;
    }

    /**
     * 设置：审核状态1：未审核2：已审核3：审核不通过
     */
    public void setExamineFlag(Integer examineFlag) {
        this.examineFlag = examineFlag;
    }

    /**
     * 获取：审核状态1：未审核2：已审核3：审核不通过
     */
    public Integer getExamineFlag() {
        return examineFlag;
    }

    /**
     * 设置：审核备注/意见
     */
    public void setExamineIdea(String examineIdea) {
        this.examineIdea = examineIdea;
    }

    /**
     * 获取：审核备注/意见
     */
    public String getExamineIdea() {
        return examineIdea;
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
     * 设置：更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置：类型1：附件2：物料
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：类型1：附件2：物料
     */
    public Integer getType() {
        return type;
    }
}
