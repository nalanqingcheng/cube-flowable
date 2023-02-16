package com.liujianan.cube.flowable.entity.dto;

import com.liujianan.cube.flowable.common.core.web.domain.CurrentUser;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: BackNodeDto
 * @Description: 退回节点操作dto
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/29 14:20
 */

public class BackNodeDto {

    private String taskId;

    private String backNodeId;

    private String backNodeName;

    private String backReason;

    private String instanceId;

    private CurrentUser currentUser;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBackNodeId() {
        return backNodeId;
    }

    public void setBackNodeId(String backNodeId) {
        this.backNodeId = backNodeId;
    }

    public String getBackNodeName() {
        return backNodeName;
    }

    public void setBackNodeName(String backNodeName) {
        this.backNodeName = backNodeName;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }
}
