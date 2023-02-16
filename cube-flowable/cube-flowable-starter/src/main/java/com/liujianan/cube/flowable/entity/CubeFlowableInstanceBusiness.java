package com.liujianan.cube.flowable.entity;

import java.util.Date;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowableInstanceBusiness
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/20 16:38
 */

public class CubeFlowableInstanceBusiness {

    private Long id;

    private String instanceId;

    private String businessKey;

    private String tableCode;

    private String status;

    private String result;

    private Date applayTime;

    private String createUserId;

    private Date createTime;

    /**
     * 部署模型ID
     */
    private String deploymentId;

    /**
     * 流程定义ID
     */
    private String procDefId;

    private String title;

    /**
     * 流程定义名称
     */
    private String procdesName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getApplayTime() {
        return applayTime;
    }

    public void setApplayTime(Date applayTime) {
        this.applayTime = applayTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcdesName() {
        return procdesName;
    }

    public void setProcdesName(String procdesName) {
        this.procdesName = procdesName;
    }
}
