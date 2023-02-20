package com.liujianan.cube.flowable.mapper;

import com.liujianan.cube.flowable.entity.CubeFlowForm;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowFormMapper
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/15 16:51
 */

@Repository
public interface CubeFlowFormMapper {

    /**
     * 查询流程表单
     *
     * @param id 流程表单ID
     * @return 流程表单
     */
    CubeFlowForm selectWfFormById(String id);

    /**
     * 查询流程表单列表
     *
     * @param wfForm 流程表单
     * @return 流程表单集合
     */
    List<CubeFlowForm> selectWfFormList(CubeFlowForm wfForm);
    List<CubeFlowForm> selectWfFormListFilter(CubeFlowForm wfForm);

    /**
     * 新增流程表单
     *
     * @param wfForm 流程表单
     * @return 结果
     */
    int insertWfForm(CubeFlowForm wfForm);

    /**
     * 修改流程表单
     *
     * @param wfForm 流程表单
     * @return 结果
     */
    int updateWfForm(CubeFlowForm wfForm);

    /**
     * 删除流程表单
     *
     * @param id 流程表单ID
     * @return 结果
     */
    int deleteWfFormById(Long id);

    /**
     * 批量删除流程表单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWfFormByIds(Long[] ids);

//    int insertInstanceBusiness(CubeFlowableInstanceBusiness cfInstanceBusiness);

    int rawUpdateSql(String sql);

    /**
     * 原生查询 SQL 语句
     * @param sql 建表 sql
     * @return 结果
     */
    Map<String, Object> rawSelectSql(String sql);

    /**
     * 删除表结构和数据
     * @param tableCode 表名
     * @return 结果
     */
    int dropTableIfExists(String tableCode);

    /**
     * 根据部署模型id获取表单对象的 TABLE_CODE, FORM_DATA
     * @param deploymentId
     * @return
     */
    CubeFlowForm selectFormDesignByDeploymentId(String deploymentId);

    /**
     * 获取业务表单数据
     * @param businessKey
     * @param tableCode
     * @return
     */
    Map<String, Object> getFormDataByBusinessKeyAndTableCode(String businessKey, String tableCode);
}
