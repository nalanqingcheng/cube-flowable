package com.liujianan.cube.flowable.service.impl;


import com.google.common.collect.Lists;
import com.liujianan.cube.flowable.entity.CubeDepartment;
import com.liujianan.cube.flowable.entity.CubeNodeConfig;
import com.liujianan.cube.flowable.entity.CubeRole;
import com.liujianan.cube.flowable.entity.CubeUser;
import com.liujianan.cube.flowable.entity.vo.CubeNodeConfigVo;
import com.liujianan.cube.flowable.mapper.CubeDeptMapper;
import com.liujianan.cube.flowable.mapper.CubeNodeConfigMapper;
import com.liujianan.cube.flowable.mapper.CubeUserMapper;
import com.liujianan.cube.flowable.service.CubeNodeConfigService;
import com.liujianan.cube.flowable.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeNodeConfigServiceImpl
 * @Description: 节点配置serviceImpl
 * @Author: sunyan
 * @Date: 2022/11/23 9:52
 */
@Service
public class CubeNodeConfigServiceImpl implements CubeNodeConfigService {

    @Autowired
    private CubeNodeConfigMapper cubeNodeConfigMapper;

    @Autowired
    private CubeUserMapper cubeUserMapper;

    @Autowired
    private CubeDeptMapper cubeDeptMapper;

    @Override
    public CubeNodeConfigVo getNodeConfigList(String procDefId, String nodeId ) {
        List<CubeNodeConfig> list = cubeNodeConfigMapper.selectNodeConfigList(procDefId,nodeId);
        CubeNodeConfigVo cubeNodeConfigVo = new CubeNodeConfigVo();
        cubeNodeConfigVo.setNodeId(nodeId);
        cubeNodeConfigVo.setProcDefId(procDefId);
        List<String> typeList = Lists.newArrayList();
        //构建前端需要的格式
        for(CubeNodeConfig cubeNodeConfig : list){
            typeList.add(cubeNodeConfig.getUserType());
            switch (cubeNodeConfig.getUserType()) {
                case "0":
                    //userType=0 用户角色 这时relateId存储的是groupId用，分隔的字符串
                    List<String> roleIdList = Arrays.stream(cubeNodeConfig.getRelateId().split(",")).collect(Collectors.toList());
                    List<CubeRole> roleList = Lists.newArrayList();
                    for (String id : roleIdList){
                        roleList.add(cubeUserMapper.selectRoleById(id));
                    }
                    cubeNodeConfigVo.setRoleList(roleList);
                    break;
                case "1":
                    //userType=1 直接选择用户 这时relateId存储的是用户id用，分隔的字符串
                    List<String> userIdList = Arrays.stream(cubeNodeConfig.getRelateId().split(",")).collect(Collectors.toList());
                    List<CubeUser> userList = Lists.newArrayList();
                    for (String id : userIdList){
                        userList.add(cubeUserMapper.selectUserById(id));
                    }
                    cubeNodeConfigVo.setUserList(userList);
                    break;
                case "2":
                    //userType=2 部门 这时relateId存储的是部门id
                    List<String> deptIdList = Arrays.stream(cubeNodeConfig.getRelateId().split(",")).collect(Collectors.toList());
                    List<CubeDepartment> deptList = Lists.newArrayList();
                    for (String id : deptIdList){
                        deptList.add(cubeDeptMapper.selectDeptListById(id));
                    }
                    cubeNodeConfigVo.setDeptList(deptList);
                    break;
                case "3":
                    //userType=3 部门负责人 这时relateId存储的是部门id
                    List<String> deptLeaderIdList = Arrays.stream(cubeNodeConfig.getRelateId().split(",")).collect(Collectors.toList());
                    List<CubeDepartment> deptLeaderList = Lists.newArrayList();
                    for (String id : deptLeaderIdList){
                        deptLeaderList.add(cubeDeptMapper.selectDeptListById(id));
                    }
                    cubeNodeConfigVo.setDeptLeaderList(deptLeaderList);
                    break;
                case "5":
                    //userType=5 表单变量 这时relateId存储的是变量
                    cubeNodeConfigVo.setFormVariables(cubeNodeConfig.getRelateId());
                    break;
                default:
                    break;
            }
        }
        cubeNodeConfigVo.setUserTypeList(typeList);
        return cubeNodeConfigVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNodeConfig(List<CubeNodeConfig> cubeNodeConfigList,String createBy) {
        //获取节点id
        String nodeId = cubeNodeConfigList.get(0).getNodeId();
        //获取流程定义id
        String procDefId = cubeNodeConfigList.get(0).getProcDefId();
        //获取模型id
        String modelId = cubeNodeConfigMapper.selectModelIdByProcDefId(procDefId);
        //删除该节点的配置
        cubeNodeConfigMapper.deleteByNodeId(nodeId,modelId);
        for(CubeNodeConfig cubeNodeConfig: cubeNodeConfigList){
            cubeNodeConfig.setId(UUIDUtil.uuid());
            cubeNodeConfig.setModelId(modelId);
            cubeNodeConfig.setCreateBy(createBy);
            //user_type = 4 是发起人所在部门
            if(cubeNodeConfig.getUserType().equals("4")){
                CubeDepartment department = cubeDeptMapper.selectDeptByUserId(createBy);
                if(department != null){
                    cubeNodeConfig.setRelateId(department.getDeptId());
                }
            }
        }
        //插入新配置
        cubeNodeConfigMapper.insertNodeConfig(cubeNodeConfigList);
    }
}
