package com.liujianan.cube.flowable.entity;

import org.apache.ibatis.annotations.Param;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeDeptUserMapping
 * @Description: 部门和用户映射表
 * @Author: sunyan
 * @Date: 2022/11/23 13:41
 */
public class CubeDeptUserMapping {
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 是否是部门负责人 0不是 1是
     */
    private String isLeader;

    public CubeDeptUserMapping(){

    }

    public CubeDeptUserMapping( String userId ,String deptId) {
        this.deptId = deptId;
        this.userId = userId;
        this.isLeader = "0";
    }

    public CubeDeptUserMapping(String userId ,String deptId, Boolean isLeader) {
        this.deptId = deptId;
        this.userId = userId;
        this.isLeader = isLeader ? "1" : "0";
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(String isLeader) {
        this.isLeader = isLeader;
    }
}
