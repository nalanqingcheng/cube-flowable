package com.liujianan.cube.flowable.service;

import com.liujianan.cube.flowable.entity.CubeRole;
import com.liujianan.cube.flowable.entity.CubeUser;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeUserService
 * @Description: 用户service
 * @Author: sunyan
 * @Date: 2022/11/28 15:48
 */
public interface CubeUserService {

    /**
     * 查询用户列表 userName和deptId 都可为空
     * @param userName 姓名
     * @param deptId 部门id
     * @return
     */
    List<CubeUser> selectUserList(String userName, String deptId);

    /**
     * 获取节点用户列表
     * @param procDefId 流程id
     * @param nodeId 节点id
     * @return 用户列表
     */
    List<CubeUser> getNodeUserList(String procDefId,String nodeId);

    /**
     * 获取角色列表
     * @param cubeRole
     * @return 角色列表
     */
    List<CubeRole> getRoleList(CubeRole cubeRole);

    /**
     * 新增用户
     * @param cubeUser 用户
     * @return 数量
     */
    int addCubeUser(CubeUser cubeUser);

    /**
     * 新增用户
     * @param cubeUserList 用户列表
     * @return 数量
     */
    int addCubeUser(List<CubeUser> cubeUserList);

    /**
     * 新增角色
     * @param cubeRole
     * @return
     */
    int addCubeRole(CubeRole cubeRole);

    /**
     * 设置用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return
     */
    void setUserRole(String userId,String roleId);

    /**
     * 删除用户
     * @param userId 用户id
     */
    void deleteUser(String userId);

    /**
     * 删除用户角色
     * @param userId 用户id
     * @param roleId 角色id
     */
    void deleteUserRole(String userId,String roleId);

    /**
     * 删除角色
     * @param roleId 角色id
     */
    void deleteRole(String roleId);
}
