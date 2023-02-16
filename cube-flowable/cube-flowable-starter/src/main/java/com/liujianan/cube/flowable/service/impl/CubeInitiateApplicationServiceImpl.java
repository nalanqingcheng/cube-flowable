package com.liujianan.cube.flowable.service.impl;

import com.liujianan.cube.flowable.common.utils.StringUtils;
import com.liujianan.cube.flowable.entity.CubeFlowForm;
import com.liujianan.cube.flowable.entity.CubeProcessDefinition;
import com.liujianan.cube.flowable.entity.dto.ApplayFormDto;
import com.liujianan.cube.flowable.mapper.CubeFlowFormMapper;
import com.liujianan.cube.flowable.service.CubeFlowFormService;
import com.liujianan.cube.flowable.service.CubeInitiateApplicationService;
import com.liujianan.cube.flowable.util.CubePage;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeInitiateApplicationServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/23 11:21
 */

@Service
public class CubeInitiateApplicationServiceImpl implements CubeInitiateApplicationService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private CubeFlowFormService cubeFlowFormService;

    @Autowired
    private CubeFlowFormMapper cubeFlowFormMapper;

    @Override
    public CubePage<CubeProcessDefinition> initiateApplicationList(CubeProcessDefinition processDefinition) {
        CubePage<CubeProcessDefinition> list = new CubePage<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.orderByProcessDefinitionId().orderByProcessDefinitionId().desc();
        if (StringUtils.isNotBlank(processDefinition.getName())) {
            processDefinitionQuery.processDefinitionNameLike("%" + processDefinition.getName() + "%");
        }
        if (StringUtils.isNotBlank(processDefinition.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike("%" + processDefinition.getKey() + "%");
        }
        if (StringUtils.isNotBlank(processDefinition.getCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike("%" + processDefinition.getCategory() + "%");
        }
        if (!Objects.isNull(processDefinition.getOnlyLastVersion()) && processDefinition.getOnlyLastVersion()) {
            processDefinitionQuery.latestVersion(); // 仅查询最新版本
        }

        List<org.flowable.engine.repository.ProcessDefinition> processDefinitionList = processDefinitionQuery.list();

        for (org.flowable.engine.repository.ProcessDefinition definition: processDefinitionList) {
            ProcessDefinitionEntityImpl entityImpl = (ProcessDefinitionEntityImpl) definition;
            CubeProcessDefinition entity = new CubeProcessDefinition();
            entity.setId(definition.getId());
            entity.setKey(definition.getKey());
            entity.setName(definition.getName());
            entity.setCategory(definition.getCategory());
            entity.setVersion(definition.getVersion());
            entity.setDescription(definition.getDescription());
            entity.setDeploymentId(definition.getDeploymentId());
            Deployment deployment = repositoryService.createDeploymentQuery()
                    .deploymentId(definition.getDeploymentId())
                    .singleResult();
            entity.setDeploymentTime(deployment.getDeploymentTime());
            entity.setDiagramResourceName(definition.getDiagramResourceName());
            entity.setResourceName(definition.getResourceName());
            entity.setSuspendState(entityImpl.getSuspensionState() + "");
            if (entityImpl.getSuspensionState() == 1) {
                entity.setSuspendStateName("已激活");
            } else {
                entity.setSuspendStateName("已挂起");
            }
            list.add(entity);
        }
        return list;
    }

    @Override
    public CubeFlowForm queryFormByDeploymentId(String deploymentId) {
        // 根据部署模型id获取表单对象的 TABLE_CODE, FORM_DATA
        CubeFlowForm formMap = cubeFlowFormService.selectFormDesignByDeploymentId(deploymentId);
        return formMap;
    }

    @Override
    public Long tempSave(ApplayFormDto applayFormDto) throws Exception {
        return cubeFlowFormService.saveFormData(applayFormDto);
    }

    @Override
    public Map<String, Object> getBusinessFormData(String businessKey, String tableCode) {
        return cubeFlowFormMapper.getFormDataByBusinessKeyAndTableCode(businessKey, tableCode);
    }
}
