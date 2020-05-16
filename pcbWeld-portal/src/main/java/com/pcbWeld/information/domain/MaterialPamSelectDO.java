package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 多选框属性对应参数表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-13 15:18:09
 */
public class MaterialPamSelectDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private Integer paramsId;
    //
    private String paramsName;
    //
    private BigDecimal price;
    //
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
    public void setParamsId(Integer paramsId) {
        this.paramsId = paramsId;
    }

    /**
     * 获取：
     */
    public Integer getParamsId() {
        return paramsId;
    }

    /**
     * 设置：
     */
    public void setParamsName(String paramsName) {
        this.paramsName = paramsName;
    }

    /**
     * 获取：
     */
    public String getParamsName() {
        return paramsName;
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
     * 设置：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：
     */
    public Date getCreateTime() {
        return createTime;
    }
}
