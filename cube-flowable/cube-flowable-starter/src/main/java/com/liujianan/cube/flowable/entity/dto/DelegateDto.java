package com.liujianan.cube.flowable.entity.dto;


import com.liujianan.cube.flowable.entity.BaseEntity;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: DelegateDto
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/12/5 10:53
 */

public class DelegateDto extends BaseEntity {

    /**
     * 流程实例id
     */
    private String instanceId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 委托人
     */
    private String fromUser;

    /**
     * 被委托人
     */
    private String delegateToUser;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getDelegateToUser() {
        return delegateToUser;
    }

    public void setDelegateToUser(String delegateToUser) {
        this.delegateToUser = delegateToUser;
    }
}
