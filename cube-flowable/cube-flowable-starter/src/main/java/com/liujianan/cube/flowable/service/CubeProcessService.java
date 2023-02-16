package com.liujianan.cube.flowable.service;

import com.liujianan.cube.flowable.entity.CubeTask;
import com.liujianan.cube.flowable.entity.dto.ApplayFormDto;
import com.liujianan.cube.flowable.entity.dto.BackNodeDto;
import com.liujianan.cube.flowable.entity.dto.DelegateDto;
import com.liujianan.cube.flowable.entity.dto.NodeTaskDto;
import com.liujianan.cube.flowable.entity.vo.ApprovalNodeVo;
import com.liujianan.cube.flowable.entity.vo.BackNodeVo;
import com.liujianan.cube.flowable.entity.vo.HistoricActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeProcessService
 * @Description: 流程相关接口
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/24 8:47
 */

public interface CubeProcessService {


    /**
     * 提交申请
     * @param cubeFlowForm
     * @return
     */
    // int submitApply(CubeFlowForm cubeFlowForm) throws Exception;

    /**
     * 我的申请列表
     * @param task
     * @return
     */
    public List<CubeTask> myApplicationList(CubeTask task);

    /**
     * 我的待办列表
     * @param task
     * @return
     */
    public List<CubeTask> myTodoList(CubeTask task);

    /**
     * 我的已办列表
     * @param task
     * @return
     */
    public List<CubeTask> myDoneList(CubeTask task);

    /**
     * 删除我的申请草稿
     * @param id
     * @return int
     */
    public int deleteMyApplication(Long id);

    /**
     * 查询审批历史列表
     */
    public  List<HistoricActivity> selectHistoryList(HistoricActivity historicActivity);

    /**
     *撤回申请
     */
    public HashMap<String,Object> revokeProcess(CubeTask cubeTask);

    /**
     * 审批办理
     * @param cubeTask
     */
    public void complete(CubeTask cubeTask);

    /**
     * 获取可回退的节点列表
     * @param taskId
     * @return
     */
    public List<BackNodeVo> getBackNodeList(String taskId);

    /**
     * 退回任务操作
     * @param backNodeDto
     */
    public void doBackNode(BackNodeDto backNodeDto);

    /**
     * 获取业务表单数据和驳回数据
     * @param businessKey
     * @param tableCode
     * @return
     */
    public Map<String, Object> getFormBusinessAndRejectData(String businessKey, String tableCode, String taskId);

    /**
     * 提交申请
     * @param applayFormDto
     */
    public void submitApply(ApplayFormDto applayFormDto);

    /**
     * 获取流程图
     * @param processInstanceId 流程实例id
     * @return 流程图xml  已完成节点列表  正在进行中节点列表
     */
    public Map<String,Object> getProcessDiagram(String processInstanceId);

    /**
     * 签收
     * @param taskId 任务id
     * @param processInstanceId 流程实例id
     * @param currentUser 当前登录人
     */
    public void claim(String taskId, String processInstanceId, String currentUser);

    /**
     * 取消签收
     * @param taskId 任务id
     */
    public void unclaim(String taskId);

    /**
     * 获取第一个审批节点
     * @param nodeTaskDto
     * @return
     */
    public List<ApprovalNodeVo> getFirstApprovalNode(NodeTaskDto nodeTaskDto);


    /**
     * 获取下一个审批节点
     * @param instanceId
     * @return
     */
    public List<ApprovalNodeVo> getNextApprovalNode(String instanceId, String approvedTask);

    /**
     * 委托转办
     * @param delegateDto
     */
    void delegate(DelegateDto delegateDto);
}
