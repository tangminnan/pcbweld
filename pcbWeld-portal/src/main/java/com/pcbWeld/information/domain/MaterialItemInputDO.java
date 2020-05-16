package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 输入框属性对应价格表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-13 15:18:09
 */
public class MaterialItemInputDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private Long itemId;
    //价格
    private BigDecimal price;
    //是否有范围，可选填
    private String scope;
    //计算方法（* 或者 +）
    private String countWay;
    //创建时间
    private Date createTime;

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
     * 设置：
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取：
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 设置：
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取：
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置：是否有范围，可选填
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * 获取：是否有范围，可选填
     */
    public String getScope() {
        return scope;
    }

    /**
     * 设置：计算方法（* 或者 +）
     */
    public void setCountWay(String countWay) {
        this.countWay = countWay;
    }

    /**
     * 获取：计算方法（* 或者 +）
     */
    public String getCountWay() {
        return countWay;
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
}
