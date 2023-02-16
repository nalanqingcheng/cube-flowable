package com.liujianan.cube.flowable.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowableNode
 * @Description: 节点设置实体类
 * @Author: sunyan
 * @Date: 2022/11/23 9:08
 */
public class CubeNodeConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private String id;
    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 节点类型0：单人活动1：多人单一2：多人顺序3：多人并行
     */
    private String nodeType;
    /**
     * 节点关联类型 0：角色 1：直接选人 2：部门，3：部门负责人。4：发起人部门负责人。5:动态变量
     */
    private String userType;
    /**
     * 关联其他表id
     */
    private String relateId;
    /**
     * 流程定义id
     */
    private String procDefId;
    /**
     * 模型id
     */
    private String modelId;
    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
