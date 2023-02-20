package com.liujianan.cube.flowable.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liujianan.cube.flowable.cmd.DeployModelCmd;
import com.liujianan.cube.flowable.cmd.SaveModelEditorCmd;
import com.liujianan.cube.flowable.common.core.exception.CubeFlowableBaseException;
import com.liujianan.cube.flowable.common.utils.StringUtils;
import com.liujianan.cube.flowable.entity.ModelRequest;
import com.liujianan.cube.flowable.entity.ModelResponse;
import com.liujianan.cube.flowable.mapper.CubeFlowFormMapper;
import com.liujianan.cube.flowable.mapper.CubeFlowableModelerMapper;
import com.liujianan.cube.flowable.service.CubeFlowableModelerService;
import com.liujianan.cube.flowable.util.CubePage;
import com.liujianan.cube.flowable.util.UUIDUtil;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.ModelEntityImpl;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowableModelerServiceImpl
 * @Description: 流程模型管理service实现类
 * @Author: 风清扬 [刘佳男] 和 张馨月
 * @Date: 2022/11/15 15:01
 */

@Service
public class CubeFlowableModelerServiceImpl implements CubeFlowableModelerService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CubeFlowFormMapper formMapper;
    @Autowired
    private CubeFlowableModelerMapper cubeFlowableModelerMapper;

    /**
     * 查询流程模型列表
     *
     * @param modelEntity 模型对象
     * @param pageNum 页数
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public CubePage modelerList(ModelEntityImpl modelEntity, Integer pageNum, Integer pageSize) {

        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.orderByLastUpdateTime().desc();

        if (org.apache.commons.lang3.StringUtils.isNotBlank(modelEntity.getKey())) {
            modelQuery.modelKey(modelEntity.getKey());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(modelEntity.getName())) {
            modelQuery.modelNameLike("%" + modelEntity.getName() + "%");
        }
        if (org.apache.commons.lang3.StringUtils.isNoneBlank(modelEntity.getCategory())) {
            modelQuery.modelCategory(modelEntity.getCategory());
        }

        List<Model> resultList = modelQuery.listPage((pageNum - 1) * pageSize, pageSize);
//        Collections.sort(resultList, new Comparator<Model>() {
//            @Override
//            public int compare(Model o1, Model o2) {
//                if (o1.getCreateTime().getTime() < o2.getCreateTime().getTime()) {
//                    return 1;
//                } else if (o1.getCreateTime().getTime() > o2.getCreateTime().getTime()) {
//                    return -1;
//                } else {
//                    return 0;
//                }
//            }
//        });
        CubePage<ModelEntityImpl> list = new CubePage<>();
        resultList.parallelStream().forEach(model -> {
            ModelEntityImpl modelEntity1 = (ModelEntityImpl) model;
            list.add(modelEntity1);
        });

        list.setTotal(modelQuery.count());
        list.setPageNum(pageNum);
        list.setPageSize(pageSize);
        return list;
    }

    /**
     * 根据流程模型id获取流程模型信息
     *
     * @param modelId 流程模型id
     * @return
     */
    @Override
    public ModelResponse queryModelerById(String modelId) {

        Model model = repositoryService.getModel(modelId);
        if (model == null) {
            throw new CubeFlowableBaseException("流程模型不存在：id " + modelId);
        }

        ModelResponse response = new ModelResponse();
        response.setCategory(model.getCategory());
        response.setCreateTime(model.getCreateTime());
        response.setId(model.getId());
        response.setKey(model.getKey());
        response.setLastUpdateTime(model.getLastUpdateTime());
        try {
            JsonNode modelNode = objectMapper.readTree(model.getMetaInfo());
            response.setDescription(modelNode.get("description").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setName(model.getName());
        response.setVersion(model.getVersion());
        if (model.getDeploymentId() != null) {
            response.setDeployed(true);
        } else {
            response.setDeployed(false);
        }
        response.setTenantId(model.getTenantId());

        if (model.hasEditorSource()) {
            byte[] editorBytes = repositoryService.getModelEditorSource(model.getId());
            String editor = new String(editorBytes, StandardCharsets.UTF_8);
            response.setEditor(editor);
        }

        return response;
    }

    /**
     * 创建流程模型
     *
     *
     * @param key         流程key
     * @param name        流程名称
     * @param formId      表单ID
     * @param category    流程分类
     * @param description 流程描述
     */
    @Override
    public String createModeler(String key, String name, String formId, String category, String description) {

        // modelKey 唯一性校验
        if (repositoryService.createModelQuery().modelKey(key).count() > 0) {
            throw new CubeFlowableBaseException("创建流程模型失败：key【" + key + "】已存在");
        }

        Model model = repositoryService.newModel();
        model.setKey(key);
        model.setName(name);
        model.setCategory(category);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode metaInfo = objectMapper.createObjectNode();
        metaInfo.put("name", name);
        metaInfo.put("description", StringUtils.isEmpty(description) ? "" : description);
        model.setMetaInfo(metaInfo.toString());

        repositoryService.saveModel(model);

        // 记录表单模型关系表
        StringBuilder builder = new StringBuilder(" INSERT INTO CUBE_FLOWABLE_FORM_MODEL (ID, FORM_ID, MODEL_ID) VALUES ( '");
        builder.append(UUIDUtil.uuid());
        builder.append("' , '");
        builder.append(formId);
        builder.append("' , '");
        builder.append(model.getId());
        builder.append("' ) ");
        formMapper.rawUpdateSql(builder.toString());

        return model.getId();

    }

    /**
     * 保存模型编辑器
     *
     * @param modelRequest 模型对象
     */
    @Override
    public void saveModelEditor(ModelRequest modelRequest) {
        managementService.executeCommand(new SaveModelEditorCmd(modelRequest.getId(), modelRequest.getEditor()));
    }

    /**
     * 修改流程模型
     *
     * @param modelRequest 模型对象
     */
    @Override
    public void updateModeler(ModelRequest modelRequest) {
        managementService.executeCommand(new SaveModelEditorCmd(modelRequest.getId(), modelRequest.getKey(),
                modelRequest.getName(), modelRequest.getCategory(), modelRequest.getDescription()));
    }

    /**
     * 根据id删除信息
     *
     * @param modelId 流程模型id
     * @param cascade 是否级联删除
     */
    @Override
    public void deleteModeler(String modelId, Boolean cascade) {
        try {
            Model deleteModel = repositoryService.createModelQuery().modelId(modelId).singleResult();
            if (cascade && deleteModel.getDeploymentId() != null) {
                repositoryService.deleteDeployment(deleteModel.getDeploymentId(), cascade);
            }
            repositoryService.deleteModel(modelId);
            // 删除模型同时删除该模型和表单的绑定关系（From cube_flowable_form_model）
            cubeFlowableModelerMapper.deleteFormModelerRelation(modelId);
            // formMapper.rawUpdateSql(" DELETE FROM WF_FORM_MODEL WHERE 1=1 AND MODEL_ID ='" + modelId + "' ");
        }
        catch (Exception e) {

        }
    }

    /**
     * 部署流程模型
     *
     * @param modelId 流程模型id
     */
    @Override
    public void deployModeler(String modelId) {
        managementService.executeCommand(new DeployModelCmd(modelId));
    }

}
