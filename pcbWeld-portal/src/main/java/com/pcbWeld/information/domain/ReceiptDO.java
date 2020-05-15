package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-13 10:02:23
 */
public class ReceiptDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //订单编号
    private String orderNos;
    //用户ID
    private Long userId;
    //联系人姓名
    private String consignee;
    //创建时间
    private Date createTime;
    //接收发票地址
    private String address;
    //个人联系电话
    private String mobile;
    //开票公司名称
    private String receiptCompany;
    //开票税号
    private String receiptNumber;
    //开户行
    private String receiptBank;
    //开户账户
    private String receiptAccount;
    //开户联系电话
    private String receiptPhone;
    //开票公司联系地址
    private String receiptAddress;
    //发票金额
    private BigDecimal payAmount;
    //状态  0=待开发票   1=已开发票
    private Integer receiptStatus;

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Integer getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(Integer receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

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
     * 设置：订单编号
     */
    public void setOrderNos(String orderNos) {
        this.orderNos = orderNos;
    }
    /**
     * 获取：订单编号
     */
    public String getOrderNos() {
        return orderNos;
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
     * 设置：接收发票地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * 获取：接收发票地址
     */
    public String getAddress() {
        return address;
    }
    /**
     * 设置：个人联系电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**
     * 获取：个人联系电话
     */
    public String getMobile() {
        return mobile;
    }
    /**
     * 设置：开票公司名称
     */
    public void setReceiptCompany(String receiptCompany) {
        this.receiptCompany = receiptCompany;
    }
    /**
     * 获取：开票公司名称
     */
    public String getReceiptCompany() {
        return receiptCompany;
    }
    /**
     * 设置：开票税号
     */
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    /**
     * 获取：开票税号
     */
    public String getReceiptNumber() {
        return receiptNumber;
    }
    /**
     * 设置：开户行
     */
    public void setReceiptBank(String receiptBank) {
        this.receiptBank = receiptBank;
    }
    /**
     * 获取：开户行
     */
    public String getReceiptBank() {
        return receiptBank;
    }
    /**
     * 设置：开户账户
     */
    public void setReceiptAccount(String receiptAccount) {
        this.receiptAccount = receiptAccount;
    }
    /**
     * 获取：开户账户
     */
    public String getReceiptAccount() {
        return receiptAccount;
    }
    /**
     * 设置：开户联系电话
     */
    public void setReceiptPhone(String receiptPhone) {
        this.receiptPhone = receiptPhone;
    }
    /**
     * 获取：开户联系电话
     */
    public String getReceiptPhone() {
        return receiptPhone;
    }
    /**
     * 设置：开票公司联系地址
     */
    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }
    /**
     * 获取：开票公司联系地址
     */
    public String getReceiptAddress() {
        return receiptAddress;
    }
    /**
     * 设置：发票金额
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    /**
     * 获取：发票金额
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }
}
