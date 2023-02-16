package com.liujianan.cube.flowable.service;

import com.liujianan.cube.flowable.entity.CubeNodeConfig;
import com.liujianan.cube.flowable.entity.vo.CubeNodeConfigVo;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeNodeConfigService
 * @Description: 节点配置service
 * @Author: sunyan
 * @Date: 2022/11/23 9:51
 */
public interface CubeNodeConfigService {

    /**
     * 通过流程定义id和点id查询节点配置
     * @param procDefId 流程定义id
     * @param nodeId 节点id
     * @return 节点配置vo对象
     */
    CubeNodeConfigVo getNodeConfigList(String procDefId, String nodeId);

    /**
     * 更新节点配置
     * @param cubeNodeConfigList 节点配置列表
     * @param createBy 用户名
     */
    void updateNodeConfig(List<CubeNodeConfig> cubeNodeConfigList,String createBy);
}
