package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * pcb参数表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 10:26:39
 */
public class MaterialParameterDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private Long itemId;
    //参数名称
    private String parameterName;
    //规格名称
    private String specificationName;
    //价格
    private BigDecimal price;
    //创建时间
    private Date createTime;
    //创建人
    private String createBy;
    //更新时间
    private Date updateTime;
    //状态 1=关闭；2=启用
    private Integer status;


    //类目名称
    private String itemName;

    //有无子分类 0=有 1=没有
    private Integer isJunior;


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
     * 设置：参数名称
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * 获取：参数名称
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * 设置：规格名称
     */
    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    /**
     * 获取：规格名称
     */
    public String getSpecificationName() {
        return specificationName;
    }

    /**
     * 设置：价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取：价格
     */
    public BigDecimal getPrice() {
        return price;
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
     * 设置：创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取：创建人
     */
    public String getCreateBy() {
        return createBy;
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
     * 设置：状态 1=关闭；2=启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态 1=关闭；2=启用
     */
    public Integer getStatus() {
        return status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "MaterialParameterDO{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", parameterName='" + parameterName + '\'' +
                ", specificationName='" + specificationName + '\'' +
                ", price=" + price +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", status=" + status +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
