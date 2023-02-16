package com.liujianan.cube.flowable.service;

import com.liujianan.cube.flowable.entity.CubeFlowForm;
import com.liujianan.cube.flowable.entity.CubeProcessDefinition;
import com.liujianan.cube.flowable.entity.dto.ApplayFormDto;
import com.liujianan.cube.flowable.util.CubePage;

import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeInitiateApplicationService
 * @Description: 发起申请service
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/23 11:16
 */

public interface CubeInitiateApplicationService {

    /**
     * 获取发起申请列表
     * @param processDefinition
     * @return
     */
    public CubePage<CubeProcessDefinition> initiateApplicationList(CubeProcessDefinition processDefinition);

    /**
     * 根据部署模型id查找表单设计
     * @param deploymentId
     * @return
     */
    public CubeFlowForm queryFormByDeploymentId(String deploymentId);

    /**
     * 暂存表单数据
     * @param applayFormDto
     */
    public Long tempSave(ApplayFormDto applayFormDto) throws Exception;

    /**
     * 根据业务主键喝tableCode获取表单数据
     * @param businessKey
     * @param tableCode
     * @return
     */
    public Map<String, Object> getBusinessFormData(String businessKey, String tableCode);
}
