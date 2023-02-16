package com.liujianan.cube.flowable.service;

import com.liujianan.cube.flowable.entity.ModelRequest;
import com.liujianan.cube.flowable.entity.ModelResponse;
import com.liujianan.cube.flowable.util.CubePage;
import org.flowable.engine.impl.persistence.entity.ModelEntityImpl;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowableModelerService
 * @Description: 流程模型管理接口
 * @Author: 风清扬 [刘佳男] 和 张馨月
 * @Date: 2022/11/15 15:01
 */

public interface CubeFlowableModelerService {

    /**
     * 查询流程模型列表
     * @param modelEntity 模型对象
     * @param pageNum 页数
     * @param pageSize 每页数量
     * @return
     */
    public CubePage modelerList(ModelEntityImpl modelEntity, Integer pageNum, Integer pageSize);

    /**
     * 根据流程模型id获取流程模型信息
     * @param modelId 流程模型id
     * @return
     */
    public ModelResponse queryModelerById(String modelId);

    /**
     * 创建流程模型
     * @param key 流程key
     * @param name 流程名称
     * @param formId 表单ID
     * @param category 流程分类
     * @param description 流程描述
     * @return
     */
    public String createModeler(String key, String name, String formId, String category, String description);

    /**
     * 保存模型编辑器
     * @param modelRequest 模型对象
     */
    public void saveModelEditor(ModelRequest modelRequest);

    /**
     * 修改流程模型
     * @param modelRequest 模型对象
     */
    public void updateModeler(ModelRequest modelRequest);

    /**
     * 根据id删除信息
     * @param modelId 流程模型id
     * @param cascade 是否级联删除
     */
    void deleteModeler(String modelId, Boolean cascade);

    /**
     * 部署流程模型
     * @param modelId 流程模型id
     */
    public void deployModeler(String modelId);

}
