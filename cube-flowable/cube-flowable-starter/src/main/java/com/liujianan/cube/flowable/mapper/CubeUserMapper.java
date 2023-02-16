package com.liujianan.cube.flowable.mapper;

import com.liujianan.cube.flowable.entity.CubeRole;
import com.liujianan.cube.flowable.entity.CubeUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeUserMapper
 * @Description: user mapper
 * @Author: sunyan
 * @Date: 2022/11/28 9:49
 */
public interface CubeUserMapper {

    /**
     * 根据用户id查找用户
     * @param Id
     * @return
     */
    CubeUser selectUserById(String Id);

    /**
     * 根据角色id查userid
     * @param groupId
     * @return
     */
    List<String> selectUserIdByGroupId(String groupId);

    /**
     * 根据角色id查userid
     * @param groupId
     * @return
     */
    List<CubeUser> selectUserByGroupId(String groupId);

    /**
     * 根据角色id获取角色信息
     * @param id
     * @return
     */
    CubeRole selectRoleById(String id);

    /**
     * 根据部门id 查询用户id列表
     * @param deptId 部门id
     * @return 用户id列表
     */
    List<CubeUser> selectUserListByDeptId(String deptId);

    /**
     * 查询用户列表
     * @param userName 用户名称
     * @param deptId 部门id
     * @return 用户列表
     */
    List<CubeUser> selectUserList(@Param("userName") String userName,@Param("deptId") String deptId);

    /**
     * 查询用户部门负责人列表
     * @param deptId 部门id
     * @return 用户id列表
     */
    List<CubeUser> selectDeptLeaderByDeptId(String deptId);

    /**
     * 根据用户id查找部门负责人id
     * @param userId 用户id
     * @return 用户id列表
     */
    List<CubeUser> selectDeptLeaderByUserId(String userId);

    /**
     * 查询角色列表
     * @param cubeRole 角色
     * @return 角色列表
     */
    List<CubeRole> selectRoleList(CubeRole cubeRole);

    /**
     * 新增角色
     * @param cubeRole 角色
     * @return
     */
    int insertCubeRole(CubeRole cubeRole);

    /**
     * 新增用户
     * @param cubeUser 用户
     * @return
     */
    int insertCubeUser(CubeUser cubeUser);

    /**
     * 批量新增用户
     * @param userList 用户列表
     * @return
     */
    int insertCubeUserList(List<CubeUser> userList);
}
