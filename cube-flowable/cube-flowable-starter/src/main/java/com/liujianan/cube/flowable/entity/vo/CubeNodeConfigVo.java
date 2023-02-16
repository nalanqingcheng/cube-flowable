package com.liujianan.cube.flowable.entity.vo;

import com.liujianan.cube.flowable.entity.CubeDepartment;
import com.liujianan.cube.flowable.entity.CubeRole;
import com.liujianan.cube.flowable.entity.CubeUser;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeNodeConfigVo
 * @Description: 节点配置vo
 * @Author: sunyan
 * @Date: 2022/11/24 10:56
 */
public class CubeNodeConfigVo {

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 流程定义id
     */
    private String procDefId;

    /**
     * 用户类型列表
     */
    private List<String> userTypeList;

    /**
     * 角色列表
     */
    private List<CubeRole> roleList;

    /**
     * 用户列表
     */
    private List<CubeUser> userList;

    /**
     * 部门列表
     */
    private List<CubeDepartment> deptList;

    /**
     * 部门负责人部门列表
     */
    private List<CubeDepartment> deptLeaderList;

    /**
     * 表单变量
     */
    private String formVariables;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public List<String> getUserTypeList() {
        return userTypeList;
    }

    public void setUserTypeList(List<String> userTypeList) {
        this.userTypeList = userTypeList;
    }

    public List<CubeRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<CubeRole> roleList) {
        this.roleList = roleList;
    }

    public List<CubeUser> getUserList() {
        return userList;
    }

    public void setUserList(List<CubeUser> userList) {
        this.userList = userList;
    }

    public List<CubeDepartment> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<CubeDepartment> deptList) {
        this.deptList = deptList;
    }

    public List<CubeDepartment> getDeptLeaderList() {
        return deptLeaderList;
    }

    public void setDeptLeaderList(List<CubeDepartment> deptLeaderList) {
        this.deptLeaderList = deptLeaderList;
    }

    public String getFormVariables() {
        return formVariables;
    }

    public void setFormVariables(String formVariables) {
        this.formVariables = formVariables;
    }
}
