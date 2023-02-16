package com.liujianan.cube.flowable.mapper;

import com.liujianan.cube.flowable.entity.CubeNodeConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeNodeMapper
 * @Description: 节点设置mapper
 * @Author: sunyan
 * @Date: 2022/11/23 9:29
 */
public interface CubeNodeConfigMapper {

    /**
     * 通过流程定义id节点id查询节点配置
     * @param procDefId 流程定义id
     * @param nodeId 节点id
     * @return 节点配置对象
     */
    List<CubeNodeConfig> selectNodeConfigList(@Param("procDefId") String procDefId,@Param("nodeId") String nodeId);

    /**
     * 新增节点配置
     * @param cubeNodeConfigList 节点配置对象列表
     * @return 数据库插入条数
     */
    int insertNodeConfig(List<CubeNodeConfig> cubeNodeConfigList);

    /**
     * 通过节点id删除
     * @param nodeId 节点id
     * @param modelId 模型id
     * @return 数据库插入条数
     */
    int deleteByNodeId(@Param("nodeId")String nodeId,@Param("modelId")String modelId);

    /**
     * 根据流程定义id获取模型id
     * @param procDefId 流程定义id
     * @return modelId
     */
    String selectModelIdByProcDefId(String procDefId);
}
