package com.pcbWeld.information.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * pcb类目表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 10:26:39
 */
public class MaterialItemDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //类目名称
    private String type;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //排序
    private Integer sort;
    //状态 1=关闭；2=启用
    private Integer status;
    //属性显示形式（1=输入框,2=加减框,3=下拉框,4=多选框）
    private Integer displayForm;


    private List MaterialParameterDO;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List getMaterialParameterDO() {
        return MaterialParameterDO;
    }

    public void setMaterialParameterDO(List materialParameterDO) {
        MaterialParameterDO = materialParameterDO;
    }

    public Integer getDisplayForm() {
        return displayForm;
    }

    public void setDisplayForm(Integer displayForm) {
        this.displayForm = displayForm;
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
     * 设置：类目名称
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取：类目名称
     */
    public String getType() {
        return type;
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
     * 设置：排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取：排序
     */
    public Integer getSort() {
        return sort;
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

    @Override
    public String toString() {
        return "MaterialItemDO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sort=" + sort +
                ", status=" + status +
                ", displayForm=" + displayForm +
                ", MaterialParameterDO=" + MaterialParameterDO +
                '}';
    }
}
