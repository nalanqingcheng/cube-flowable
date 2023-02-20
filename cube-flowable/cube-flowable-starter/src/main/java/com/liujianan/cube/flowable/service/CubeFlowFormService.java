package com.liujianan.cube.flowable.service;

import com.liujianan.cube.flowable.entity.CubeFlowForm;
import com.liujianan.cube.flowable.entity.dto.ApplayFormDto;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowFormService
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/20 15:43
 */

public interface CubeFlowFormService {

    /**
     * 查询流程表单列表
     *
     * @param cubeFlowForm 流程表单
     * @return 流程表单集合
     */
    List<CubeFlowForm> selectList(CubeFlowForm cubeFlowForm);

    /**
     * 查询流程表单
     *
     * @param id 流程表单ID
     * @return 流程表单
     */
    CubeFlowForm selectFormById(String id);

    /**
     * 新增流程表单
     *
     * @param cubeFlowForm 流程表单
     * @return 结果
     */
    int insertWfForm(CubeFlowForm cubeFlowForm);

    /**
     * 修改流程表单
     *
     * @param cubeFlowForm 流程表单
     * @return 结果
     */
    int updateWfForm(CubeFlowForm cubeFlowForm);

    /**
     * 批量删除流程表单
     *
     * @param ids 需要删除的流程表单ID
     * @return 结果
     */
    int deleteWfFormByIds(Long[] ids);

    /**
     * 删除流程表单信息
     *
     * @param id 流程表单ID
     * @return 结果
     */
    int deleteWfFormById(Long id);

    /**
     * 保存表单设计
     *
     * @param cubeFlowForm 流程表单
     * @param tableSchema 数据库tableSchema
     * @return 结果
     */
    int saveFormDesign(CubeFlowForm cubeFlowForm, String tableSchema) throws Exception;

    /**
     * 保存表单数据
     * @param applayFormDto
     * @return
     */
    Long saveFormData(ApplayFormDto applayFormDto) throws Exception;

    /**
     * 根据已部署的模型id查找表单对象
     * @param deploymentId
     * @return
     */
    CubeFlowForm selectFormDesignByDeploymentId(String deploymentId);

    /**
     * 提交申请
     * @param cubeFlowForm
     * @return
     */
    // int submitApply(CubeFlowForm cubeFlowForm) throws Exception;


}
