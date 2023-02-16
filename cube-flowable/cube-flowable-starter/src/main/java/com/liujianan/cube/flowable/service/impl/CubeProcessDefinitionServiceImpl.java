package com.liujianan.cube.flowable.service.impl;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liujianan.cube.flowable.cmd.GetProcessDefinitionInfoCmd;
import com.liujianan.cube.flowable.entity.CubeProcessDefinition;
import com.liujianan.cube.flowable.mapper.CubeFlowableModelerMapper;
import com.liujianan.cube.flowable.service.CubeProcessDefinitionService;
import com.liujianan.cube.flowable.util.CubePage;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.job.api.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeProcessDefinitionServiceImpl
 * @Description: 流程定义service实现类
 * @Author: sunyan
 * @Date: 2022/11/21 9:38
 */
@Service
public class CubeProcessDefinitionServiceImpl implements CubeProcessDefinitionService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;

    @Autowired
    private CubeFlowableModelerMapper cubeFlowableModelerMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String processDefinitionId, Boolean cascade) {
        ProcessDefinition processDefinition = getProcessDefinitionById(processDefinitionId);
        if (processDefinition.getDeploymentId() == null) {
            throw new FlowableException("id为"+ processDefinitionId  +"的流程定义未部署 ");
        }
        if (cascade) {
            List<Job> jobs = managementService.createTimerJobQuery().processDefinitionId(processDefinitionId).list();
            for (Job job : jobs) {
                managementService.deleteTimerJob(job.getId());
            }
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
        } else {
            long processCount =
                    runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).count();
            if (processCount > 0) {
                throw new FlowableException("id为" + processDefinitionId + "的流程定义有正在运行的实例");
            }
            long jobCount = managementService.createTimerJobQuery().processDefinitionId(processDefinitionId).count();
            if (jobCount > 0) {
                throw new FlowableException("id为" + processDefinitionId + "的流程定义有正在运行的任务");
            }
            String modelId = repositoryService.createModelQuery().deploymentId(processDefinition.getDeploymentId()).singleResult().getId();
            repositoryService.deleteDeployment(processDefinition.getDeploymentId());


            //版本号大于1 删除时会退一个版本的部署id
            if(processDefinition.getVersion() > 1){
                cubeFlowableModelerMapper.updateModelDeploymentId(processDefinition.getKey(),processDefinition.getVersion() - 1,modelId);
            }
        }
    }

    @Override
    public void suspendOrActiveDefinition(String id, String suspendState) {
        if ("1".equals(suspendState)) {
            // 当流程定义被挂起时，已经发起的该流程定义的流程实例不受影响（如果选择级联挂起则流程实例也会被挂起）。
            // 当流程定义被挂起时，无法发起新的该流程定义的流程实例。
            // 直观变化：act_re_procdef 的 SUSPENSION_STATE_ 为 2
            repositoryService.suspendProcessDefinitionById(id);
        } else if ("2".equals(suspendState)) {
            repositoryService.activateProcessDefinitionById(id);
        }
    }

    @Override
    public CubePage<CubeProcessDefinition> listProcessDefinition(CubeProcessDefinition processDefinition, Integer pageNum, Integer pageSize) {
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

        List<ProcessDefinition> processDefinitionList;
        if (pageNum != null && pageSize != null) {
            processDefinitionList = processDefinitionQuery.listPage((pageNum - 1) * pageSize, pageSize);
            list.setTotal(processDefinitionQuery.count());
            list.setPageNum(pageNum);
            list.setPageSize(pageSize);
        } else {
            processDefinitionList = processDefinitionQuery.list();
        }
        for (ProcessDefinition definition : processDefinitionList) {
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
    public Map<String, String> getProcessXmlStr(String deploymentId, String resourceName) {
        InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);
        String xmlStr = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining(System.lineSeparator()));
        Map<String, String> map = Maps.newHashMap();
        map.put("xmlStr", xmlStr);
        return map;
    }

    @Override
    public List<Map<String, String>> getNodeList(String processId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processId);
        Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        //获取审批节点列表
        List<UserTask> list = flowElements.stream().filter(element -> element instanceof UserTask).map(e -> (UserTask) e).collect(Collectors.toList());
        List<Map<String, String>> reList = Lists.newArrayList();
        for (UserTask userTask : list) {
            Map<String, String> map = Maps.newHashMap();
            map.put("nodeId", userTask.getId());
            map.put("nodeName", userTask.getName());
            map.put("procDefId", processId);
            reList.add(map);
        }
        return reList;
    }




    private ProcessDefinition getProcessDefinitionById(String processDefinitionId) {
        return managementService.executeCommand(new GetProcessDefinitionInfoCmd(processDefinitionId, null, null));
    }
}
