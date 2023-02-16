package com.liujianan.cube.flowable.service.impl;

import com.google.common.collect.Sets;
import com.liujianan.cube.flowable.entity.CubeNodeConfig;
import com.liujianan.cube.flowable.entity.CubeRole;
import com.liujianan.cube.flowable.entity.CubeUser;
import com.liujianan.cube.flowable.mapper.CubeNodeConfigMapper;
import com.liujianan.cube.flowable.mapper.CubeUserMapper;
import com.liujianan.cube.flowable.service.CubeUserService;
import org.flowable.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeUserServiceImpl
 * @Description: 用户service实现类
 * @Author: sunyan
 * @Date: 2022/11/28 15:55
 */
@Service
public class CubeUserServiceImpl implements CubeUserService {

    @Autowired
    private CubeUserMapper cubeUserMapper;

    @Autowired
    private CubeNodeConfigMapper cubeNodeConfigMapper;

    @Autowired
    private IdentityService identityService;

    @Override
    public List<CubeUser> selectUserList(String userName, String deptId) {
        return cubeUserMapper.selectUserList(userName,deptId);
    }

    @Override
    public List<CubeUser> getNodeUserList(String procDefId, String nodeId) {
        //获取节点配置列表
        List<CubeNodeConfig> list = cubeNodeConfigMapper.selectNodeConfigList(procDefId, nodeId);
        //用户列表
        Set<CubeUser> userList = Sets.newHashSet();
        for (CubeNodeConfig cubeNodeConfig : list) {
            switch (cubeNodeConfig.getUserType()) {
                case "0":
                    //userType=0 用户角色 这时relateId存储的是groupId用，分隔的字符串
                    //根据组id查用户列表
                    List<String> groupIdList = Arrays.stream(cubeNodeConfig.getRelateId().split(",")).collect(Collectors.toList());
                    for (String s : groupIdList) {
                        List<CubeUser> list0 = cubeUserMapper.selectUserByGroupId(s);
                        userList.addAll(list0);
                    }
                    break;
                case "1":
                    //userType=1 直接选择用户 这时relateId存储的是用户id用，分隔的字符串
                    List<String> userIdList = Arrays.stream(cubeNodeConfig.getRelateId().split(",")).collect(Collectors.toList());
                    for (String s : userIdList) {
                        userList.add(cubeUserMapper.selectUserById(s));
                    }
                    break;
                case "2":
                    //userType=2 部门 这时relateId存储的是部门id用，分隔的字符串
                    //根据部门id查用户列表
                    List<String> deptIdList = Arrays.stream(cubeNodeConfig.getRelateId().split(",")).collect(Collectors.toList());
                    for (String s : deptIdList) {
                        List<CubeUser> list2 = cubeUserMapper.selectUserListByDeptId(s);
                        userList.addAll(list2);
                    }
                    break;
                case "3":
                    //userType=3 部门负责人 这时relateId存储的是部门id用，分隔的字符串
                    //根据部门id查负责人列表
                    List<String> deptLeaderIdList = Arrays.stream(cubeNodeConfig.getRelateId().split(",")).collect(Collectors.toList());
                    for (String s : deptLeaderIdList) {
                        List<CubeUser> list3 = cubeUserMapper.selectDeptLeaderByDeptId(s);
                        userList.addAll(list3);
                    }
                    break;
                case "4":
                    //userType=4 发起人部门负责人 这时relateId存储的是用户id
                    //根据用户id查负责人列表
                    List<CubeUser> list4 = cubeUserMapper.selectDeptLeaderByUserId(cubeNodeConfig.getRelateId());
                    userList.addAll(list4);
                    break;
                case "5":
                    //userType=5
                    //动态字段
                    break;
                default:
                    break;
            }
        }
        return userList.stream().collect(Collectors.toList());
    }

    @Override
    public List<CubeRole> getRoleList(CubeRole cubeRole) {
        return cubeUserMapper.selectRoleList(cubeRole);
    }

    @Override
    public int addCubeUser(CubeUser cubeUser) {
        return cubeUserMapper.insertCubeUser(cubeUser);
    }

    @Override
    public int addCubeUser(List<CubeUser> cubeUserList) {
        return cubeUserMapper.insertCubeUserList(cubeUserList);
    }

    @Override
    public int addCubeRole(CubeRole cubeRole) {
        return cubeUserMapper.insertCubeRole(cubeRole);
    }

    @Override
    public void setUserRole(String userId, String roleId) {
        identityService.createMembership(userId,roleId);
    }

    @Override
    public void deleteUser(String userId) {
        identityService.deleteUser(userId);
    }

    @Override
    public void deleteUserRole(String userId, String roleId) {
        identityService.deleteMembership(userId,roleId);
    }

    @Override
    public void deleteRole(String roleId) {
        identityService.deleteGroup(roleId);
    }


}
