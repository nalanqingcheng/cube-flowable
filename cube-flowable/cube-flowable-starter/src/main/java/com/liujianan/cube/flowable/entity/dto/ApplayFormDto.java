package com.liujianan.cube.flowable.entity.dto;


import com.liujianan.cube.flowable.entity.BaseEntity;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: ApplayFormDto
 * @Description: 流程表单申请DTO
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/12/1 13:12
 */

public class ApplayFormDto extends BaseEntity {

    /**
     * 提交类型 0：表单直接提交；1：列表直接提交
     */
    private String submitType;

    /**
     * 部署模型ID
     */
    private String deploymentId;

    /**
     * 流程定义ID
     */
    private String procDefId;

    /**
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 业务主键
     */
    private Long businessKey;

    /**
     * 业务表
     */
    private String tableCode;

    /**
     * 业务表单数据
     */
    private String formData;

    /**
     * 审批候选任务节点
     */
    private String approvedTask;

    /**
     * 审批候选人
     */
    private String assignees;

    /**
     * 优先级
     */
    private Integer priority = 50;

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
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

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Long getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(Long businessKey) {
        this.businessKey = businessKey;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getApprovedTask() {
        return approvedTask;
    }

    public void setApprovedTask(String approvedTask) {
        this.approvedTask = approvedTask;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getAssignees() {
        return assignees;
    }

    public void setAssignees(String assignees) {
        this.assignees = assignees;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
