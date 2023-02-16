package com.liujianan.cube.flowable.entity.dto;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: NodeTaskDto
 * @Description: 获取节点信息入参
 * @Author: 韩超
 * @Date: 2022/12/14
 */
public class NodeTaskDto {
    private String deploymentId;
    private String variablesStr;
    private String submitType;
    private Long businessKey ;
    private String tableCode;

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getVariablesStr() {
        return variablesStr;
    }

    public void setVariablesStr(String variablesStr) {
        this.variablesStr = variablesStr;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public Long getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(Long businessKey) {
        this.businessKey = businessKey;
    }
}
