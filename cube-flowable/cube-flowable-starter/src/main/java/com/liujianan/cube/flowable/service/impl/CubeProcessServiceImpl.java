package com.liujianan.cube.flowable.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liujianan.cube.flowable.cmd.BackUserTaskCmd;
import com.liujianan.cube.flowable.common.core.web.domain.CurrentUser;
import com.liujianan.cube.flowable.common.utils.DateUtils;
import com.liujianan.cube.flowable.common.utils.StringUtils;
import com.liujianan.cube.flowable.constant.FlowableConstant;
import com.liujianan.cube.flowable.entity.CubeFlowableInstanceBusiness;
import com.liujianan.cube.flowable.entity.CubeTask;
import com.liujianan.cube.flowable.entity.CubeUser;
import com.liujianan.cube.flowable.entity.dto.ApplayFormDto;
import com.liujianan.cube.flowable.entity.dto.BackNodeDto;
import com.liujianan.cube.flowable.entity.dto.DelegateDto;
import com.liujianan.cube.flowable.entity.dto.NodeTaskDto;
import com.liujianan.cube.flowable.entity.vo.ApprovalNodeVo;
import com.liujianan.cube.flowable.entity.vo.BackNodeVo;
import com.liujianan.cube.flowable.entity.vo.HistoricActivity;
import com.liujianan.cube.flowable.mapper.CubeFlowFormMapper;
import com.liujianan.cube.flowable.mapper.CubeFlowableInstanceBusinessMapper;
import com.liujianan.cube.flowable.mapper.CubeTaskMapper;
import com.liujianan.cube.flowable.mapper.CubeUserMapper;
import com.liujianan.cube.flowable.service.CubeFlowFormService;
import com.liujianan.cube.flowable.service.CubeProcessService;
import com.liujianan.cube.flowable.service.CubeUserService;
import com.liujianan.cube.flowable.util.FlowableUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Gateway;
import org.flowable.bpmn.model.ParallelGateway;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.SubProcess;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.de.odysseus.el.ExpressionFactoryImpl;
import org.flowable.common.engine.impl.de.odysseus.el.util.SimpleContext;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.javax.el.ExpressionFactory;
import org.flowable.common.engine.impl.javax.el.ValueExpression;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeProcessServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/24 8:54
 */

@Service
public class CubeProcessServiceImpl implements CubeProcessService {

    private final Logger logger = LoggerFactory.getLogger(CubeProcessServiceImpl.class);

    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private CubeTaskMapper cubeTaskMapper;
    @Autowired
    private CubeFlowFormService cubeFlowFormService;
    @Autowired
    private CubeFlowFormMapper cubeFlowFormMapper;
    @Autowired
    private CubeFlowableInstanceBusinessMapper cubeFlowableInstanceBusinessMapper;
    @Autowired
    private CubeUserService cubeUserService;
    @Autowired
    private CubeProcessService cubeProcessService;
    @Autowired
    private CubeUserMapper cubeUserMapper;

//    @Override
//    public int submitApply(CubeFlowForm cubeFlowForm) throws Exception {
//        return cubeFlowFormService.submitApply(cubeFlowForm);
//    }

    @Override
    public List<CubeTask> myApplicationList(CubeTask task) {
        return cubeTaskMapper.findApplicationList(task);
    }

    @Override
    public List<CubeTask> myTodoList(CubeTask task) {
        return cubeTaskMapper.findTodoList(task);
    }

    @Override
    public List<CubeTask> myDoneList(CubeTask task) {
        return cubeTaskMapper.findDoneList(task);
    }

    @Override
    public int deleteMyApplication(Long id) {
        return cubeFlowableInstanceBusinessMapper.deleteInstanceBusiness(id);
    }

    @Override
    public List<HistoricActivity> selectHistoryList(HistoricActivity historicActivity) {
        List<HistoricActivity> rActivityList = new ArrayList<>();
        HistoricActivityInstanceQuery activityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        if (StringUtils.isNotBlank(historicActivity.getAssignee())) {
            activityInstanceQuery.taskAssignee(historicActivity.getAssignee());
        }
        if (StringUtils.isNotBlank(historicActivity.getActivityName())) {
            activityInstanceQuery.activityName(historicActivity.getActivityName());
        }
        List<HistoricActivityInstance> activityInstanceList = activityInstanceQuery.processInstanceId(historicActivity.getProcessInstanceId())
                .activityType("userTask")
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        activityInstanceList.forEach(instance -> {
            HistoricActivity activity = new HistoricActivity();
            BeanUtils.copyProperties(instance, activity);
            String taskId = instance.getTaskId();
            List<Comment> comment = taskService.getTaskComments(taskId, "comment");
            if (!CollectionUtils.isEmpty(comment)) {
                activity.setComment(comment.get(0).getFullMessage());
            }
            // 如果是撤销（deleteReason 不为 null），写入审批意见栏
            if (StringUtils.isNotBlank(activity.getDeleteReason())) {
                activity.setComment(activity.getDeleteReason());
                // 如果是退回，DELETE_REASON_ 字段 Change activity to
                if (activity.getDeleteReason().startsWith("Change activity to")) {
                    Map<String, Object> objectMap = cubeFlowFormMapper.rawSelectSql(" SELECT MESSAGE_ FROM ACT_HI_COMMENT WHERE 1=1 AND TYPE_='退回' AND TASK_ID_='" + taskId + "' AND PROC_INST_ID_='" + instance.getProcessInstanceId() + "' LIMIT 1 ");
                    activity.setComment(objectMap.get("MESSAGE_").toString());
                }
            }
            if (!Objects.isNull(instance.getAssignee())) {
//                String sysUser = instance.getAssignee();
//                if (sysUser != null) {
//                    activity.setAssigneeName(sysUser);
//                }
                if (instance.getAssignee().equals("system")) {
                    return;
                }
            } else {
                activity.setAssigneeName("/");
            }
            rActivityList.add(activity);
        });

        // 以下手动封装发起人节点的数据
        HistoricActivity startActivity = new HistoricActivity();
        activityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        HistoricActivityInstance startActivityInstance = activityInstanceQuery.processInstanceId(historicActivity.getProcessInstanceId())
                .activityType("startEvent")
                .singleResult();
        BeanUtils.copyProperties(startActivityInstance, startActivity);
        startActivity.setActivityName("开始");
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(historicActivity.getProcessInstanceId())
                .singleResult();
        startActivity.setAssignee(historicProcessInstance.getStartUserId());
//        SysUser sysUser = remoteUserService.getUserInfo(historicProcessInstance.getStartUserId()).getData().getSysUser();
//        if (sysUser != null) {
//            startActivity.setAssigneeName(sysUser);
//        }
        startActivity.setComment("提交申请");
        // 手动过滤该条发起人数据
        boolean necessaryAdd = true;
//        if ((StringUtils.isNotBlank(historicActivity.getActivityName()) && !startActivity.getActivityName().equals(historicActivity.getActivityName()))
//                || (StringUtils.isNotBlank(historicActivity.getAssignee()) && !startActivity.getAssignee().equals(historicActivity.getAssignee()))) {
//            necessaryAdd = false;
//        }
        if (necessaryAdd) {
            rActivityList.add(0, startActivity);
        }
        // 以下手动封装结束节点的数据
        HistoricActivity endActivity = new HistoricActivity();
        activityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
//        HistoricActivityInstance endActivityInstance = activityInstanceQuery.processInstanceId(historicActivity.getProcessInstanceId())
//                .activityType("endEvent")
//                .singleResult();
        List<HistoricActivityInstance> endActivityInstances = activityInstanceQuery.processInstanceId(historicActivity.getProcessInstanceId())
                .activityType("endEvent")
                .list();
        /**
         * 查询是否还有未执行完的任务
         */
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(historicActivity.getProcessInstanceId()).list();
        if (taskList.isEmpty()) {
            BeanUtils.copyProperties(endActivityInstances.get(0), endActivity);
            endActivity.setActivityName("结束");
            endActivity.setAssigneeName("/");
            endActivity.setComment("已结束");

            // 手动过滤该条发起人数据
            necessaryAdd = true;
            if ((StringUtils.isNotBlank(historicActivity.getActivityName()) && !endActivity.getActivityName().equals(historicActivity.getActivityName()))
                    || (StringUtils.isNotBlank(historicActivity.getAssignee()) && !endActivity.getAssignee().equals(historicActivity.getAssignee()))) {
                necessaryAdd = false;
            }
            if (necessaryAdd) {
                rActivityList.add(endActivity);
            }
        }
        return rActivityList;
    }

    @Override
    @Transactional
    public void complete(CubeTask cubeTask) {
        String taskId = cubeTask.getTaskId();
        String instanceId = cubeTask.getInstanceId();
        String variablesStr = cubeTask.getVariablesStr();
        System.err.println("variables: " + variablesStr);
        Map<String, Object> variables = (Map<String, Object>) JSON.parse(variablesStr);
        String comment = cubeTask.getComment();
        boolean pass = cubeTask.getPass();
        String assignees = cubeTask.getAssignees();
        String approvedTask = cubeTask.getApprovedTask();
        // Map<String, Object> formData = (Map<String, Object>) JSON.parse(variables.get("formData").toString());
        String businessKey = cubeTask.getBusinessKey();
        String tableCode = cubeTask.getTableCode();

        // 获取当前登陆人
        CurrentUser currentUser = cubeTask.getCurrentUser();

        variables.put("pass", pass);
        // 被委派人处理完成任务
        // p.s. 被委托的流程需要先 resolved 这个任务再提交。
        // 所以在 complete 之前需要先 resolved

        // 判断该任务是否是委托任务（转办）
        TaskEntityImpl task = (TaskEntityImpl) taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        // DELEGATION_ 为 PENDING 表示该任务是转办任务
        if (task.getDelegationState() != null && task.getDelegationState().equals(DelegationState.PENDING)) {
            taskService.resolveTask(taskId, variables);
            // 批注说明是转办
            String delegateUserName = currentUser.getNickName();
            comment += "【由" + delegateUserName + "转办】";

            // 如果是 OWNER_ 为 null 的转办任务（候选组的待办），暂且用转办人来签收该任务
            if (StringUtils.isBlank(task.getOwner())) {
                taskService.claim(taskId, currentUser.getUserName());
            }
        } else {
            // 只有签收任务，act_hi_taskinst 表的 assignee 字段才不为 null
            taskService.claim(taskId, currentUser.getUserName());
        }

        if (StringUtils.isNotEmpty(comment)) {
            identityService.setAuthenticatedUserId(currentUser.getUserName());
            taskService.addComment(taskId, instanceId, comment);
        }


        taskService.complete(taskId, variables);

        /**
         * 构建流程业务实现类
         */
        CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
        cfib.setBusinessKey(businessKey);
        cfib.setTableCode(tableCode);
        cfib.setStatus(FlowableConstant.STATUS_DEALING);

        //分配下一个任务用户
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instanceId).list();
        /**
         * 判断是否选择了候选任务节点，如果选择了，筛选出候选任务节点
         */
        if (StringUtils.isNotBlank(approvedTask)) {
            // 过滤选中集合
            List<Task> newTasks = tasks.stream().filter(item -> item.getTaskDefinitionKey().equals(approvedTask)).collect(Collectors.toList());
            // 未选中过滤掉的集合
            tasks.removeAll(newTasks);
            // 删除未选中的任务
            for (Task t : tasks) {
                // taskService.deleteTask(t.getId()); // 报错：The task cannot be deleted because is part of a running process
                // 系统自动签收办理，不记录日志
                taskService.claim(t.getId(), "system");
                taskService.complete(t.getId());
            }
            tasks.clear();
            tasks.addAll(newTasks);
        }
        // 判断下一个节点
        if(tasks!=null&&tasks.size()>0){
            for(Task t : tasks){
                if(StringUtils.isBlank(assignees)){

                    /**
                     * 获取流程定义
                     */
                    String processDefinKey = t.getProcessDefinitionId();
                    // 如果下个节点未分配审批人为空 取消结束流程
                    List<CubeUser> users = cubeUserService.getNodeUserList(processDefinKey, t.getTaskDefinitionKey());
                    if(users==null||users.size()==0){
                        runtimeService.deleteProcessInstance(instanceId, "canceled-审批节点未分配审批人，流程自动中断取消");
                        cfib.setStatus(FlowableConstant.STATUS_CANCELED);
                        cfib.setResult(FlowableConstant.RESULT_CANCEL);
                        //修改业务表的流程字段
                        cubeFlowableInstanceBusinessMapper.updateInstanceBusiness(cfib);
                        break;
                    }else{
                        // 避免重复添加,查找act_hi_identitylink表里是否有候选用户
                        List<String> list = cubeFlowableInstanceBusinessMapper.selectIRunIdentity(t.getId(), FlowableConstant.EXECUTOR_candidate);
                        if(list==null||list.size()==0) {
                            // 分配了节点负责人分发给全部
                            for (CubeUser user : users) {
                                taskService.addCandidateUser(t.getId(), user.getId());
                            }
                            taskService.setPriority(t.getId(), task.getPriority());
                        }
                    }
                }else{
                    // 避免重复添加
                    List<String> list = cubeFlowableInstanceBusinessMapper.selectIRunIdentity(t.getId(), FlowableConstant.EXECUTOR_candidate);
                    if(list==null||list.size()==0) {

                        for(String assignee : assignees.split(",")){
                            taskService.addCandidateUser(t.getId(), assignee);
                            // taskService.setPriority(t.getId(), cubeTask.getPriority());
                        }
                    }
                }
            }
        } else {
            cfib.setStatus(FlowableConstant.STATUS_FINISH);
            cfib.setResult(FlowableConstant.RESULT_PASS);
            cubeFlowableInstanceBusinessMapper.updateInstanceBusiness(cfib);
        }

    }

    @Override
    public List<BackNodeVo> getBackNodeList(String taskId) {
        TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = taskEntity.getProcessInstanceId();
        String currActId = taskEntity.getTaskDefinitionKey();
        String processDefinitionId = taskEntity.getProcessDefinitionId();
        Process process = repositoryService.getBpmnModel(processDefinitionId).getMainProcess();
        FlowNode currentFlowElement = (FlowNode) process.getFlowElement(currActId, true);
        List<ActivityInstance> activitys =
                runtimeService.createActivityInstanceQuery().processInstanceId(processInstanceId).finished().orderByActivityInstanceStartTime().asc().list();
        List<String> activityIds =
                activitys.stream().filter(activity -> activity.getActivityType().equals(BpmnXMLConstants.ELEMENT_TASK_USER)).filter(activity -> !activity.getActivityId().equals(currActId)).map(ActivityInstance::getActivityId).distinct().collect(Collectors.toList());
        List<BackNodeVo> list = Lists.newArrayList();
        for (String activityId : activityIds) {
            FlowNode toBackFlowElement = (FlowNode) process.getFlowElement(activityId, true);
            if (FlowableUtils.isReachable(process, toBackFlowElement, currentFlowElement)) {
                BackNodeVo vo = new BackNodeVo();
                vo.setFlowElementId(activityId);
                vo.setFlowElementName(toBackFlowElement.getName());
                list.add(vo);
            }
        }
        return list;
    }

    @Override
    public void doBackNode(BackNodeDto backNodeDto) {
        String taskId = backNodeDto.getTaskId();
        String backNodeId = backNodeDto.getBackNodeId();
        String backNodeName = backNodeDto.getBackNodeName();
        String backReason = backNodeDto.getBackReason();
        String instanceId = backNodeDto.getInstanceId();
        String backSysMessage = "退回到【" + backNodeName + "】";
        /**
         * 获取当前登陆人
         */
        CurrentUser currentUser = backNodeDto.getCurrentUser();
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        Authentication.setAuthenticatedUserId(currentUser.getUserName());
        taskService.addComment(taskId, processInstanceId, "退回",  backSysMessage);
        taskService.setAssignee(taskId, currentUser.getUserName());
        String targetRealActivityId = managementService.executeCommand(new BackUserTaskCmd(runtimeService, taskId, backNodeId));

        //分配下一个任务用户
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instanceId).list();
        // 判断下一个节点
        if(tasks!=null&&tasks.size()>0){
            for(Task t : tasks){
                /**
                 * 获取流程定义
                 */
                String processDefinKey = t.getProcessDefinitionId();
                // 如果下个节点未分配审批人为空 取消结束流程
                List<CubeUser> users = cubeUserService.getNodeUserList(processDefinKey, t.getTaskDefinitionKey());
                if(users==null||users.size()==0){
                    runtimeService.deleteProcessInstance(instanceId, "canceled-审批节点未分配审批人，流程自动中断取消");
                    break;
                }else{
                    // 避免重复添加,查找act_hi_identitylink表里是否有候选用户
                    List<String> list = cubeFlowableInstanceBusinessMapper.selectIRunIdentity(t.getId(), FlowableConstant.EXECUTOR_candidate);
                    if(list==null||list.size()==0) {
                        // 分配了节点负责人分发给全部
                        for (CubeUser user : users) {
                            taskService.addCandidateUser(t.getId(), user.getId());
                        }
                        taskService.setPriority(t.getId(), t.getPriority());
                    }
                }
            }
        }

    }

    @Override
    public HashMap<String, Object> revokeProcess(CubeTask cubeTask) {
        HashMap<String, Object> returnVo = new HashMap<>();
        String instanceId = cubeTask.getInstanceId();
        String msg = cubeTask.getComment();
        /**
         * 1、获取转入的流程实例id，业务id
         * 2。根据实例id删除流程实例，并将实例业务表中实例id置空
         * */
        if (StringUtils.isNotNull(instanceId)){
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            if (null != processInstance) {
                runtimeService.deleteProcessInstance(instanceId, msg);
            }
            /**
             * 校验
             * 1.传入的流程实例id是否满足可撤回条件：提交后未进行审批
             */
//            List<HistoricActivityInstance> historyList = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceId).activityType("userTask")
//                    .finished()
//                    .orderByHistoricActivityInstanceStartTime()
//                    .asc()
//                    .list();
//            int size = historyList.size();
            CubeFlowableInstanceBusiness cfibObj = new CubeFlowableInstanceBusiness();
            cfibObj.setBusinessKey(cubeTask.getBusinessKey());
            cfibObj.setTableCode(cubeTask.getTableCode());
            cfibObj.setStatus("3");
            cfibObj.setResult("0");
            cfibObj.setInstanceId(" ");
            int result = cubeFlowableInstanceBusinessMapper.updateInstanceBusiness(cfibObj);
            if (result == 1)
            {
                returnVo.put("result","已删除");
            }
            else{
                returnVo.put("error","未找到流程实例");
            }
            return returnVo;
        }
        else {
            returnVo.put("error","流程实例ID不能为空！");
            return returnVo;
        }
    }

    /**
     * 获取业务表单数据和驳回数据
     * @param businessKey
     * @param tableCode
     * @param taskId
     * @return
     */
    @Override
    public Map<String, Object> getFormBusinessAndRejectData(String businessKey, String tableCode, String taskId) {
        Map<String, Object> map = cubeFlowFormMapper.getFormDataByBusinessKeyAndTableCode(businessKey, tableCode);
        List<BackNodeVo> list = cubeProcessService.getBackNodeList(taskId);
        map.put("rejectTo", list);
        return map;
    }

    @Override
    @Transactional
    public void submitApply(ApplayFormDto applayFormDto) {

        Long busindessKey = applayFormDto.getBusinessKey();
        String assignees = applayFormDto.getAssignees();
        String approvedTask = applayFormDto.getApprovedTask();
        String deploymentId = applayFormDto.getDeploymentId();
        String tableCode = applayFormDto.getTableCode();

        // 获取当前登陆人
        CurrentUser currentUser = applayFormDto.getCurrentUser();
        String userName = currentUser.getUserName();
        String nickName = currentUser.getNickName();

        // 流程参数
        Map variables = Maps.newHashMap();

        /**
         * 判断表单提交类型，如果是0，说明是直接从发起申请表单直接申请，并不是从列表申请提交,
         * 如果是从发起申请表单直接申请需要保存更新业务表单数据
         */
        if (applayFormDto.getSubmitType().equals("0")) {
            /**
             * 先保存更新业务表单数据
             */
//            CubeFlowForm cubeFlowForm = new CubeFlowForm();
//            cubeFlowForm.setBusinessKey(applayFormDto.getBusinessKey());
//            cubeFlowForm.setTableCode(applayFormDto.getTableCode());
//            cubeFlowForm.setFormData(applayFormDto.getFormData());
            try {
                busindessKey = cubeFlowFormService.saveFormData(applayFormDto);
            } catch (Exception e) {
                throw new RuntimeException("保存业务表数据失败");
            }
            variables = JSON.parseObject(applayFormDto.getFormData());
        } else {
            variables = cubeFlowFormMapper.getFormDataByBusinessKeyAndTableCode(String.valueOf(busindessKey), tableCode);
        }

        // 更新流程通用字段
        StringBuilder updateBuilder = new StringBuilder(" UPDATE ");
        updateBuilder.append(tableCode);
        updateBuilder.append(" SET apply_user_id = '");
        updateBuilder.append(userName);
        updateBuilder.append("', apply_user_name ='");
        updateBuilder.append(nickName);
        updateBuilder.append("', apply_time ='");
        updateBuilder.append(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        updateBuilder.append("', process_key = '");
        updateBuilder.append(tableCode);
        updateBuilder.append("', update_user_id = '");
        updateBuilder.append(userName);
        updateBuilder.append("'");
        updateBuilder.append(", update_user_name = '");
        updateBuilder.append(nickName);
        updateBuilder.append("'");
        updateBuilder.append(", update_time = '");
        updateBuilder.append(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        updateBuilder.append("', instance_id = '");


        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();

        // 流程申请标题
        String title = processDefinition.getName() + "_" + nickName;

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(userName);

        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
        // 启动流程时设置业务 key
        processInstanceBuilder.businessKey(tableCode)
                .businessKey(busindessKey + "")
                .variables(variables)
                .processDefinitionId(processDefinition.getId())
                .name(title);

        ProcessInstance instance = processInstanceBuilder.start();

        identityService.setAuthenticatedUserId(null);

        // 更新业务表流程实例id字段
        updateBuilder.append(instance.getId());
        updateBuilder.append("' where id = ");
        updateBuilder.append(busindessKey);

        System.out.println("update sql: " + updateBuilder.toString());

        cubeFlowFormMapper.rawUpdateSql(updateBuilder.toString());

        if (Objects.isNull(busindessKey)) {
            // 记录流程实例业务关系
            CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
            cfib.setInstanceId(instance.getId());
            // cfib.setBusinessKey(busindessKey + "");
            //cfib.setTableCode(tableCode);
            cfib.setStatus(FlowableConstant.STATUS_DEALING);
            cfib.setResult(FlowableConstant.RESULT_DEALING);
            cfib.setApplayTime(new Date());
//            cfib.setDeploymentId(deploymentId);
//            cfib.setTitle(title);
//            cfib.setProcDefId(processDefinition.getId());
//            cfib.setProcdesName(processDefinition.getName());
            cubeFlowableInstanceBusinessMapper.insertInstanceBusiness(cfib);
        } else {
//            StringBuilder updateBuilder2 = new StringBuilder(" UPDATE cube_flowable_instance_busines SET INSTANCE_ID = '");
//            updateBuilder2.append(instance.getId());
//            updateBuilder2.append("', CREATE_USER_ID = NULL, CREATE_TIME = NULL WHERE 1=1 AND TABLE_CODE = '");
//            updateBuilder2.append(tableCode);
//            updateBuilder2.append("' AND BUSINESS_KEY = ");
//            updateBuilder2.append(id);
//            System.out.println("updateBuilder2: " + updateBuilder2.toString());
//            cubeFlowFormMapper.rawUpdateSql(updateBuilder2.toString());

            CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
            cfib.setInstanceId(instance.getId());
            cfib.setTableCode(tableCode);
            cfib.setBusinessKey(busindessKey + "");
            cfib.setStatus(FlowableConstant.STATUS_DEALING);
            cfib.setResult(FlowableConstant.RESULT_DEALING);
            cfib.setApplayTime(new Date());
            cfib.setDeploymentId(deploymentId);
            cfib.setTitle(title);
            cfib.setProcDefId(processDefinition.getId());
            cfib.setProcdesName(processDefinition.getName());
            cubeFlowableInstanceBusinessMapper.updateInstanceBusiness(cfib);

        }

        String instanceId = instance.getId();

        /**
         * 构建流程业务实现类
         */
        CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
        cfib.setBusinessKey(String.valueOf(busindessKey));
        cfib.setTableCode(tableCode);
        cfib.setStatus(FlowableConstant.STATUS_DEALING);

        //分配第一个任务用户
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instanceId).list();

        /**
         * 判断是否选择了候选任务节点，如果选择了，筛选出候选任务节点
         */
        if (StringUtils.isNotBlank(approvedTask)) {
            List<Task> newTasks = tasks.stream().filter(item -> item.getTaskDefinitionKey().equals(approvedTask)).collect(Collectors.toList());
            tasks.clear();
            tasks.addAll(newTasks);
        }

        // 判断下一个节点
        if(tasks!=null&&tasks.size()>0){
            for(Task t : tasks){
                if(StringUtils.isBlank(assignees)){

                    /**
                     * 获取流程定义
                     */
                    String processDefinKey = t.getProcessDefinitionId();
                    // 如果下个节点未分配审批人为空 取消结束流程
                    List<CubeUser> users = cubeUserService.getNodeUserList(processDefinKey, t.getTaskDefinitionKey());
                    if(users==null||users.size()==0){
                        runtimeService.deleteProcessInstance(instanceId, "canceled-审批节点未分配审批人，流程自动中断取消");
                        cfib.setStatus(FlowableConstant.STATUS_CANCELED);
                        cfib.setResult(FlowableConstant.RESULT_CANCEL);
                        //修改业务表的流程字段
                        cubeFlowableInstanceBusinessMapper.updateInstanceBusiness(cfib);
                        break;
                    }else{
                        // 避免重复添加,查找act_hi_identitylink表里是否有候选用户
                        List<String> list = cubeFlowableInstanceBusinessMapper.selectIRunIdentity(t.getId(), FlowableConstant.EXECUTOR_candidate);
                        if(list==null||list.size()==0) {
                            // 分配了节点负责人分发给全部
                            for (CubeUser user : users) {
                                taskService.addCandidateUser(t.getId(), user.getId());
                            }
                            taskService.setPriority(t.getId(), applayFormDto.getPriority());
                        }
                    }
                }else{
                    // 避免重复添加
                    List<String> list = cubeFlowableInstanceBusinessMapper.selectIRunIdentity(t.getId(), FlowableConstant.EXECUTOR_candidate);
                    if(list==null||list.size()==0) {

                        for(String assignee : assignees.split(",")){
                            taskService.addCandidateUser(t.getId(), assignee);
                            taskService.setPriority(t.getId(), applayFormDto.getPriority());
                        }
                    }
                }
            }
        } else {
            cfib.setStatus(FlowableConstant.STATUS_FINISH);
            cfib.setResult(FlowableConstant.RESULT_PASS);
            cubeFlowableInstanceBusinessMapper.updateInstanceBusiness(cfib);
        }
    }


    /**
     * 获取流程图
     * @param processInstanceId 流程实例id
     * @return 流程图xml  已完成节点列表  正在进行中节点列表
     */
    @Override
    public Map<String, Object> getProcessDiagram(String processInstanceId) {
        //1.获取当前的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        List<String> activeActivityIds = null;
        List<String> historyActivityIds = Lists.newArrayList();

        //2.获取所有的历史轨迹对象
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();
        for(HistoricActivityInstance historicActivityInstance:list){
            //如果是审批节点，加入已办列表
            if(historicActivityInstance.getActivityType().equals("userTask") && !"system".equals(historicActivityInstance.getAssignee())){
                historyActivityIds.add(historicActivityInstance.getActivityId());
            }
        }
        //去重
        historyActivityIds = historyActivityIds.stream().distinct().collect(Collectors.toList());
        //流程定义id
        String processDefinitionId;
        //部署id
        String deploymentId;
        //判断该流程是否在进行中
        if (processInstance != null) {
            // 正在运行的流程实例
            List activityList = runtimeService.getActiveActivityIds(processInstanceId);
            activeActivityIds =cubeTaskMapper.getActiveActivityIds(processInstanceId);

            processDefinitionId = processInstance.getProcessDefinitionId();
            deploymentId = processInstance.getDeploymentId();
            //历史节点中移除正在激活中的节点
            historyActivityIds.removeAll(activityList);

        }else{
            // 运行结束的流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            deploymentId = historicProcessInstance.getDeploymentId();
        }

        //3. 获取流程图xml
        //根据流程定义id 获取resourceName
        String resourceName = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult().getResourceName();

        //获取流程图xml字符串
        InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);
        String xmlStr = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining(System.lineSeparator()));
        //4.构建返回值
        Map<String, Object> map = Maps.newHashMap();
        map.put("xml", xmlStr);
        map.put("activeIds",activeActivityIds);
        map.put("historyIds",historyActivityIds);
        return map;
    }

    /**
     * 签收
     * @param taskId 任务id
     * @param processInstanceId 流程实例id
     * @param currentUser 当前登录人
     */
    @Override
    public void claim(String taskId, String processInstanceId, String currentUser) {
        taskService.addComment(taskId, processInstanceId, "签收任务");
        taskService.claim(taskId, currentUser);
    }

    /**
     * 取消签收
     * @param taskId 任务id
     */
    @Override
    public void unclaim(String taskId) {
        taskService.unclaim(taskId);
    }

    /**
     * 获取第一个审批节点
     * @param nodeTaskDto
     * @return
     */
    @Override
    public List<ApprovalNodeVo> getFirstApprovalNode(NodeTaskDto nodeTaskDto) {
        String variablesStr = nodeTaskDto.getVariablesStr();
        Long businessKey = nodeTaskDto.getBusinessKey();
        String deploymentId = nodeTaskDto.getDeploymentId();
        String tableCode = nodeTaskDto.getTableCode();
        String submitType = nodeTaskDto.getSubmitType();
        Map<String, Object> variables = new HashMap<String, Object>();
        if (submitType.equals("0")){
            // 流程参数
            variables = (Map<String, Object>) JSON.parse(variablesStr);
        }
        else {
            variables = cubeFlowFormMapper.getFormDataByBusinessKeyAndTableCode(String.valueOf(businessKey), tableCode);
        }
        /**
         * 根据deploymentId获取流程定义
         */
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
        /**
         * 根据流程定义id获取 BpmnModel
         */
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        List<ApprovalNodeVo> approvalNodeVoList = Lists.newArrayList();


        List<Process> processes = bpmnModel.getProcesses();
        Collection<FlowElement> elements = processes.get(0).getFlowElements();
        // 流程开始节点
        StartEvent startEvent = null;
        for (FlowElement element : elements) {
            if (element instanceof StartEvent) {
                startEvent = (StartEvent) element;
                break;
            }
        }
        List<UserTask> userTaskList = Lists.newArrayList();
        /**
         * 判断开始后的流向节点
         */
        List<SequenceFlow> sequenceFlowList = startEvent.getOutgoingFlows();
        for (SequenceFlow sequenceFlow : sequenceFlowList) {
            for (FlowElement element : elements) {
                if(element.getId().equals(sequenceFlow.getTargetRef())){
                    if(element instanceof UserTask){
                        userTaskList.add((UserTask) element);
                        continue;
                    }else if(element instanceof ExclusiveGateway){  // 排他网关
                        List<UserTask> userTasks = setExclusiveGateway(element, variables);
                        if (!userTasks.isEmpty()) {
                            userTaskList.addAll(userTasks);
                        }
                        continue;
                    }else if(element instanceof ParallelGateway){   // 并行网关
                        List<UserTask> userTasks = setExclusiveGateway(element, variables);
                        if (!userTasks.isEmpty()) {
                            userTaskList.addAll(userTasks);
                        }
                        continue;
                    }else{
                        throw new RuntimeException("流程设计错误，开始节点后只能是用户任务节点、排他网关、并行网关");
                    }
                }
            }
        }

//        // 排他、平行网关直接返回
//        if(e instanceof ExclusiveGateway || e instanceof ParallelGateway){
//            return approvalNodeVo;
//        }
        for (UserTask ut : userTaskList) {
            ApprovalNodeVo approvalNodeVo = new ApprovalNodeVo();
            approvalNodeVo.setId(ut.getId());
            approvalNodeVo.setProcDefId(processDefinition.getId());
            approvalNodeVo.setType(1);
            approvalNodeVo.setTitle(ut.getName());
            // 设置关联用户
            List<CubeUser> users = cubeUserService.getNodeUserList(processDefinition.getId(), ut.getId());
            approvalNodeVo.setUserList(users);
            approvalNodeVoList.add(approvalNodeVo);
        }

        return approvalNodeVoList;
    }

    /**
     * 获取下一个审批节点
     * @param instanceId
     * @param approvedTask
     * @return
     */
    @Override
    public List<ApprovalNodeVo> getNextApprovalNode(String instanceId, String approvedTask) {

        List<ApprovalNodeVo> approvalNodeVoList = Lists.newArrayList();
        List<UserTask> userTaskList = Lists.newArrayList();

        //当前任务信息
        // Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(instanceId).list();
        Task task = taskList.stream().filter(item -> item.getTaskDefinitionKey().equals(approvedTask)).collect(Collectors.toList()).get(0);
        if (task != null) {
            //获取流程发布Id信息
            String procDefId = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult().getProcessDefinitionId();
            /**
             * 根据流程定义id获取 BpmnModel
             */
            BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
            //传节点定义key 获取当前节点
            FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(task.getTaskDefinitionKey());
            //输出连线
            List<SequenceFlow> outgoingFlows = flowNode.getOutgoingFlows();
            //遍历返回下一个节点信息
            for (SequenceFlow outgoingFlow : outgoingFlows) {
                //类型自己判断
                FlowElement targetFlowElement = outgoingFlow.getTargetFlowElement();
                //用户任务
                if (targetFlowElement instanceof UserTask) {
                    userTaskList.add((UserTask) targetFlowElement);
                    continue;
                }else if (targetFlowElement instanceof EndEvent) {
                    continue;
                }else if (targetFlowElement instanceof ExclusiveGateway) {
                    Map proInsMap = runtimeService.getVariables(instanceId);
                    List<UserTask> userTasks = setExclusiveGateway(targetFlowElement, proInsMap);
                    if (!userTasks.isEmpty()) {
                        userTaskList.addAll(userTasks);
                    }
                    continue;
                }else if (targetFlowElement instanceof ParallelGateway) {
                    Map proInsMap = runtimeService.getVariables(instanceId);
                    List<UserTask> userTasks = setExclusiveGateway(targetFlowElement, proInsMap);
                    if (!userTasks.isEmpty()) {
                        userTaskList.addAll(userTasks);
                    }
                    continue;
                }
            }

            for (UserTask ut : userTaskList) {
                ApprovalNodeVo approvalNodeVo = new ApprovalNodeVo();
                approvalNodeVo.setId(ut.getId());
                approvalNodeVo.setType(1);
                approvalNodeVo.setTitle(ut.getName());
                // 设置关联用户
                List<CubeUser> users = cubeUserService.getNodeUserList(procDefId, ut.getId());
                approvalNodeVo.setUserList(users);
                approvalNodeVoList.add(approvalNodeVo);
            }
        }

        return approvalNodeVoList;
    }

    /**
     * 委托转办
     * @param delegateDto
     */
    @Override
    public void delegate(DelegateDto delegateDto) {
        CubeUser delegateUser = cubeUserMapper.selectUserById(delegateDto.getDelegateToUser());
        taskService.addComment(delegateDto.getTaskId(), delegateDto.getInstanceId(), "委托转办给[ " + delegateUser.getName() + " ]");
        taskService.delegateTask(delegateDto.getTaskId(), delegateDto.getDelegateToUser());
    }

    /**
     * 设置独占网关
     * @param targetFlow
     * @param proInsMap
     * @return
     */
    private List<UserTask> setExclusiveGateway(FlowElement targetFlow, Map proInsMap) {

        // 返回的userTask集合
        List<UserTask> userTaskList = Lists.newArrayList();

        List<SequenceFlow> targetFlows = ((Gateway) targetFlow).getOutgoingFlows();
        for (SequenceFlow sequenceFlow : targetFlows) {
            try {
                if(checkFormDataByRuleEl(sequenceFlow.getConditionExpression(),proInsMap)) {
                    //目标节点信息
                    FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();

                    if (targetFlowElement instanceof UserTask) {
                        userTaskList.add((UserTask) targetFlowElement);
                        break;
                    } else if (targetFlowElement instanceof EndEvent) {
                        // return null;
                        break;
                    } else if (targetFlowElement instanceof ServiceTask) {
                        userTaskList.add((UserTask) targetFlowElement);//不一定对 按照UserTask 来
                        break;
                    } else if (targetFlowElement instanceof ExclusiveGateway) { // 排他网关
                        //递归寻找
                        List<UserTask> uts = setExclusiveGateway(targetFlowElement,proInsMap);
                        if (!uts.isEmpty()) {
                            userTaskList.addAll(uts);
                        }
                        break;
                    } else if (targetFlowElement instanceof ParallelGateway) {   // 并行网关
                        //递归寻找
                        List<UserTask> uts = setExclusiveGateway(targetFlowElement,proInsMap);
                        if (!uts.isEmpty()) {
                            userTaskList.addAll(uts);
                        }
                        break;
                    } else if (targetFlowElement instanceof SubProcess) {
                        // return null;//目前不管
                        break;
                    } else {
                        // return null;
                        break;
                    }
                } else if (sequenceFlow.getConditionExpression() == null) {
                    //目标节点信息
                    FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();

                    if (targetFlowElement instanceof UserTask) {
                        userTaskList.add((UserTask) targetFlowElement);
                        continue;
                    } else if (targetFlowElement instanceof EndEvent) {
                        // return null;
                        continue;
                    } else if (targetFlowElement instanceof ServiceTask) {
                        userTaskList.add((UserTask) targetFlowElement);//不一定对 按照UserTask 来
                        continue;
                    } else if (targetFlowElement instanceof ExclusiveGateway) { // 排他网关
                        //递归寻找
                        List<UserTask> uts = setExclusiveGateway(targetFlowElement,proInsMap);
                        if (!uts.isEmpty()) {
                            userTaskList.addAll(uts);
                        }
                        continue;
                    } else if (targetFlowElement instanceof ParallelGateway) {   // 并行网关
                        //递归寻找
                        List<UserTask> uts = setExclusiveGateway(targetFlowElement,proInsMap);
                        if (!uts.isEmpty()) {
                            userTaskList.addAll(uts);
                        }
                        continue;
                    } else if (targetFlowElement instanceof SubProcess) {
                        // return null;//目前不管
                        continue;
                    } else {
                        // return null;
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userTaskList;
    }

    private Boolean checkFormDataByRuleEl(String el, Map<String, Object> formData) throws Exception {

        if (el == null) {
            return false;
        }

        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        for (Object k : formData.keySet()) {
            if (formData.get(k) != null) {
                context.setVariable(k.toString(), factory.createValueExpression(formData.get(k), formData.get(k).getClass()));
            }
        }

        ValueExpression e = factory.createValueExpression(context, el, Boolean.class);
        return (Boolean) e.getValue(context);
    }

    /**
     * 检查当前节点的下一个节点是否是结束节点
     * @param procDefId
     * @param currentTask
     * @return
     */
    private Boolean checkNextNodeIsEndEvent(String procDefId, Task currentTask) {
        /**
         * 根据流程定义id获取 BpmnModel
         */
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        //传节点定义key 获取当前节点
        FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(currentTask.getTaskDefinitionKey());
        //输出连线
        List<SequenceFlow> outgoingFlows = flowNode.getOutgoingFlows();
        //遍历返回下一个节点信息
        for (SequenceFlow outgoingFlow : outgoingFlows) {
            //类型自己判断
            FlowElement targetFlowElement = outgoingFlow.getTargetFlowElement();
            //用户任务
            if (targetFlowElement instanceof EndEvent) {
                return true;
            }
        }
        return false;
    }
}
