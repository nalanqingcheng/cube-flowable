package com.liujianan.cube.flowable.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeModelerMapper
 * @Description: 流程设计_数据层
 * @Author: 张馨月
 * @Date: 2022/11/24 14:40
 */

public interface CubeFlowableModelerMapper {

    /**
     * 删除模型和表单的绑定关系
     * @param modelId 流程模型id
     */
    public void deleteFormModelerRelation(String modelId);

    /**
     * 更新模型的部署id
     * @param key key
     * @param version 版本号
     * @return 流程定义id
     */
    void updateModelDeploymentId(@Param("key") String key,@Param("version")Integer version,@Param("modelId")String modelId);
}
