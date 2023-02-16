package com.liujianan.cube.flowable.entity;


/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowForm
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/15 15:18
 */

public class CubeFlowForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 物理表代码 */
    private String tableCode;

    /** 流程分类ID */
    private Long categoryId;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 流程分类名称 */
    private String categoryName;

    /** 表单数据 */
    private String formData;

    /**
     * 表单类型
     */
    private String formType;

    /**
     * 自定义表单组件路径
     */
    private String componentPath;

    private String deploymentId;

    /** 业务表id */
    private Long businessKey;

    /** 是否过滤掉已绑定流程的记录 1是 */
    private String filter;

    /** 根据祖级查询 */
    private String ancestorsQuery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getComponentPath() {
        return componentPath;
    }

    public void setComponentPath(String componentPath) {
        this.componentPath = componentPath;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Long getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(Long businessKey) {
        this.businessKey = businessKey;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getAncestorsQuery() {
        return ancestorsQuery;
    }

    public void setAncestorsQuery(String ancestorsQuery) {
        this.ancestorsQuery = ancestorsQuery;
    }
}
