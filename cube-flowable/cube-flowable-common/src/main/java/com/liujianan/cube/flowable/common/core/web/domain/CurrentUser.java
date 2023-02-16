package com.liujianan.cube.flowable.common.core.web.domain;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CurrentUser
 * @Description: 系统当前登陆人
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/20 16:45
 */

public class CurrentUser {

    /**
     * 登陆人id
     */
    private Long id;

    /**
     * 登陆人账号
     */
    private String userName;

    /**
     * 登陆人昵称
     */
    private String nickName;

    /** 部门ID */
    private Long deptId;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }
}
