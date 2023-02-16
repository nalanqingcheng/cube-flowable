package com.liujianan.cube.flowable.entity.vo;

import com.liujianan.cube.flowable.entity.CubeUser;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: ApprovalNodeVo
 * @Description: 审批节点VO类
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/12/1 18:58
 */

public class ApprovalNodeVo {

    /**节点id*/
    private String id;

    /**流程定义id*/
    private String procDefId;

    /**节点名*/
    private String title;

    /**节点类型 0开始 1用户任务 2结束 3排他网关 4并行网关*/
    private Integer type;

    /**关联角色*/
    private List<CubeUser> userList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<CubeUser> getUserList() {
        return userList;
    }

    public void setUserList(List<CubeUser> userList) {
        this.userList = userList;
    }
}
