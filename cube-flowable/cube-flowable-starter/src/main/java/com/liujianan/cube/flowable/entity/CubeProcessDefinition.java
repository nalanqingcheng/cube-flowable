package com.liujianan.cube.flowable.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: ProcessDefinition
 * @Description: 流程定义返回对象
 * @Author: sunyan
 * @Date: 2022/11/21 9:50
 */
public class CubeProcessDefinition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程id
     */
    private String id;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 流程KEY
     */
    private String key;
    /**
     * 流程版本
     */
    private int version;
    /**
     * 所属分类
     */
    private String category;
    /**
     * 流程描述
     */
    private String description;
    /**
     * 部署id
     */
    private String deploymentId;
    /**
     * 部署时间
     */
    private Date deploymentTime;
    /**
     * 流程图
     */
    private String diagramResourceName;
    /**
     * resourceName
     */
    private String resourceName;

    /** 流程实例状态 1 激活 2 挂起 */
    private String suspendState;

    /**
     * 状态名称
     */
    private String suspendStateName;

    private Boolean onlyLastVersion = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getSuspendState() {
        return suspendState;
    }

    public void setSuspendState(String suspendState) {
        this.suspendState = suspendState;
    }

    public String getSuspendStateName() {
        return suspendStateName;
    }

    public void setSuspendStateName(String suspendStateName) {
        this.suspendStateName = suspendStateName;
    }

    public Boolean getOnlyLastVersion() {
        return onlyLastVersion;
    }

    public void setOnlyLastVersion(Boolean onlyLastVersion) {
        this.onlyLastVersion = onlyLastVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("key", key)
                .append("version", version)
                .append("category", category)
                .append("description", description)
                .append("deploymentId", deploymentId)
                .append("deploymentTime", deploymentTime)
                .append("diagramResourceName", diagramResourceName)
                .append("resourceName", resourceName)
                .append("suspendState", suspendState)
                .append("suspendStateName", suspendStateName)
                .append("onlyLastVersion", onlyLastVersion)
                .toString();
    }
}
