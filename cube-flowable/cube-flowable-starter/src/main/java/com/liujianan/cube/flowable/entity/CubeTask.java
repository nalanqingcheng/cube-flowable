package com.liujianan.cube.flowable.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeTask
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/23 18:56
 */

public class CubeTask extends BaseEntity {

    private String id;

    /**
     * 部署模型ID
     */
    private String deploymentId;

    /**
     * 流程定义id
     */
    private String procDefId;


    /**
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 业务主键
     */
    private String businessKey;

    /**
     * 业务表
     */
    private String tableCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 结果
     */
    private String result;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String applayTime;

    /**
     * 表单类型
     */
    private String formType;

    /**
     * 表单模板数据
     */
    private String formData;

    /**
     * 自定义组件名称
     */
    private String componentName;

    /**
     * 标题
     */
    private String title;

    /**
     * 流程定义名称
     */
    private String procdesName;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 审批意见
     */
    private String comment;

    /**
     * 参数字符串
     */
    private String variablesStr;

    /**
     * 通过标识
     */
    private Boolean pass;

    /**
     * 下个节点审批人
     */
    private String assignees;

    /**
     * 下一个审批节点
     */
    private String approvedTask;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 委托人
     */
    private String entrustUser;

    /**
     * 驳回至
     */
    private String rejectTo;

    /**
     * 耗时
     */
    private String duration;

    private Integer pageNum;

    private Integer pageSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getApplayTime() {
        return applayTime;
    }

    public void setApplayTime(String applayTime) {
        this.applayTime = applayTime;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVariablesStr() {
        return variablesStr;
    }

    public void setVariablesStr(String variablesStr) {
        this.variablesStr = variablesStr;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public String getApprovedTask() {
        return approvedTask;
    }

    public void setApprovedTask(String approvedTask) {
        this.approvedTask = approvedTask;
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

    public String getEntrustUser() {
        return entrustUser;
    }

    public void setEntrustUser(String entrustUser) {
        this.entrustUser = entrustUser;
    }

    public String getRejectTo() {
        return rejectTo;
    }

    public void setRejectTo(String rejectTo) {
        this.rejectTo = rejectTo;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
