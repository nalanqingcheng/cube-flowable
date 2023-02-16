package com.liujianan.cube.flowable.service;

import com.liujianan.cube.flowable.entity.CubeProcessDefinition;
import com.liujianan.cube.flowable.util.CubePage;


import java.util.List;
import java.util.Map;


/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeProcessDefinitionService
 * @Description: 流程定义service
 * @Author: sunyan
 * @Date: 2022/11/21 9:35
 */
public interface CubeProcessDefinitionService {

    /**
     * 根据id删除流程定义  根据cascade判断是否是级联删除
     * @param processDefinitionId 流程定义id
     * @param cascade 是否级联删除
     */
    void delete(String processDefinitionId, Boolean cascade);

    /**
     * 根据id和 suspendState 激活或者挂起流程
     * @param id 流程定义id
     * @param suspendState 状态标志
     */
    void suspendOrActiveDefinition(String id, String suspendState);

    /**
     * 获取流程定义列表
     * @param processDefinition 流程定义对象
     * @param pageNum 页数
     * @param pageSize 每页数量
     * @return 流程定义
     */
    CubePage<CubeProcessDefinition> listProcessDefinition(CubeProcessDefinition processDefinition, Integer pageNum, Integer pageSize);

    /**
     * 获取流程xml字符串
     * @param deploymentId 部署id
     * @param resourceName 资源名称
     * @return xml字符串
     */
    Map<String,String> getProcessXmlStr(String deploymentId, String resourceName);

    /**
     * 获取节点列表
     * @param processId 流程id
     * @return 节点列表
     */
    List<Map<String,String>> getNodeList(String processId);


}
