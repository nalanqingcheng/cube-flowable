package com.liujianan.cube.flowable.mapper;


import com.liujianan.cube.flowable.entity.CubeFlowableInstanceBusiness;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CubeFlowableInstanceBusinessMapper {
    /**
     * 删除流程实例业务关系表
     * @return result
     */
    int deleteInstanceBusiness(Long id);

    int insertInstanceBusiness(CubeFlowableInstanceBusiness cfInstanceBusiness);

    int updateInstanceBusiness(CubeFlowableInstanceBusiness cfInstanceBusiness);

    List<String> selectIRunIdentity(@Param("taskId")String taskId, @Param("type") String type);
}
