package com.ruoyi.web.controller.flowable;

import com.liujianan.cube.flowable.common.core.web.domain.CurrentUser;
import com.liujianan.cube.flowable.entity.CubeTask;
import com.liujianan.cube.flowable.entity.dto.ApplayFormDto;
import com.liujianan.cube.flowable.entity.dto.BackNodeDto;
import com.liujianan.cube.flowable.entity.dto.DelegateDto;
import com.liujianan.cube.flowable.entity.dto.NodeTaskDto;
import com.liujianan.cube.flowable.entity.vo.HistoricActivity;
import com.liujianan.cube.flowable.service.CubeProcessService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @version v1.0
 * @ProjectName: RuoYi-Vue
 * @ClassName: ProcessController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/25 10:32
 */

@RestController
@RequestMapping("/flowable/process")
public class ProcessController extends BaseController {

    @Autowired
    private CubeProcessService cubeProcessService;

    /**
     * 提交申请流程操作
     * @param applayFormDto
     * @return
     */
    @PostMapping("/submitApply")
    public AjaxResult submitApply(@RequestBody ApplayFormDto applayFormDto) {
        try {
            // 获取业务系统当前登陆人并传给流程引擎
            LoginUser loginUser = getLoginUser();
            CurrentUser user = new CurrentUser();
            user.setId(loginUser.getUserId());
            user.setUserName(loginUser.getUsername());
            user.setNickName(loginUser.getUser().getNickName());
            applayFormDto.setCurrentUser(user);
            cubeProcessService.submitApply(applayFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("提交申请报错");
        }
        return AjaxResult.success();
    }


    /**
     * 我的待办列表
     */
    @GetMapping("/todoList")
    public TableDataInfo taskList(CubeTask cubeTask) {
        startPage();
        // 获取业务系统当前登陆人并传给流程引擎
        LoginUser loginUser = getLoginUser();
        CurrentUser user = new CurrentUser();
        user.setId(loginUser.getUserId());
        user.setUserName(loginUser.getUsername());
        cubeTask.setCurrentUser(user);

        List<CubeTask> list = cubeProcessService.myTodoList(cubeTask);

        return getDataTable(list);
    }


    /**
     * 获取表单数据
     */
    @GetMapping("/formData")
    public AjaxResult getFormBusinessAndRejectData(CubeTask cubeTask){

        Map<String, Object> map = cubeProcessService.getFormBusinessAndRejectData(cubeTask.getBusinessKey(), cubeTask.getTableCode(), cubeTask.getTaskId());
//        System.out.println("look here！！！");
//        map.forEach(new BiConsumer<String, Object>() {
//            @Override
//            public void accept(String t, Object u) {
//                System.out.println(t+"->>>"+u);
//
//            }
//        });
        return AjaxResult.success(map);
    }

    /**
     * 驳回操作
     */
    @PostMapping("/doBackNode")
    public AjaxResult doBackNode(@RequestBody BackNodeDto backNodeDto){
        try{
            // 获取业务系统当前登陆人并传给流程引擎
            LoginUser loginUser = getLoginUser();
            CurrentUser user = new CurrentUser();
            user.setId(loginUser.getUserId());
            user.setUserName(loginUser.getUsername());
            user.setNickName(loginUser.getUser().getNickName());
            backNodeDto.setCurrentUser(user);
            cubeProcessService.doBackNode(backNodeDto);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("驳回失败");
        }
        return AjaxResult.success();

    }

    /**
     * 办理操作
     * @param cubeTask
     * @return
     */
    @PostMapping("/complete")
    public AjaxResult complete(@RequestBody CubeTask cubeTask){
        try{
            // 获取业务系统当前登陆人并传给流程引擎
            LoginUser loginUser = getLoginUser();
            CurrentUser user = new CurrentUser();
            user.setId(loginUser.getUserId());
            user.setUserName(loginUser.getUsername());
            user.setNickName(loginUser.getUser().getNickName());
            cubeTask.setCurrentUser(user);
            cubeProcessService.complete(cubeTask);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("办理失败");
        }
        return AjaxResult.success();
    }

    /**
     * 获取我的申请列表
     * */
    @GetMapping("/getList")
    public TableDataInfo getList(CubeTask cubeTask){
        startPage();
        // 获取业务系统当前登陆人并传给流程引擎
        LoginUser loginUser = getLoginUser();
        CurrentUser user = new CurrentUser();
        user.setId(loginUser.getUserId());
        user.setUserName(loginUser.getUsername());
        user.setNickName(loginUser.getUser().getNickName());
        cubeTask.setCurrentUser(user);
        List<CubeTask> applicationList = cubeProcessService.myApplicationList(cubeTask);
        return getDataTable(applicationList);
    }

    /**
     * 撤回申请
     * */
    @PostMapping("/revokeProcess")
    public AjaxResult revokeProcess(@RequestBody CubeTask cubeTask){
        // 获取业务系统当前登陆人并传给流程引擎
        LoginUser loginUser = getLoginUser();
        CurrentUser user = new CurrentUser();
        user.setId(loginUser.getUserId());
        user.setUserName(loginUser.getUsername());
        user.setNickName(loginUser.getUser().getNickName());
        cubeTask.setCurrentUser(user);
        HashMap<String, Object> returnVo = cubeProcessService.revokeProcess(cubeTask);
        if (Objects.isNull(returnVo.get("error"))){
            return  AjaxResult.success();
        }
        else {
            return AjaxResult.error(returnVo.get("error").toString());
        }
    }
    /**
     * 删除申请
     * */
    @PostMapping("/deleteApplication")
    public AjaxResult deleteApplication(@RequestBody Long myApplicationId){
        try{
            cubeProcessService.deleteMyApplication(myApplicationId);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    /**
     * 获取审批历史记录
     * */
    @PostMapping("/listHistory")
    public TableDataInfo listHistory(@RequestBody HistoricActivity historicActivity) {
        List<HistoricActivity> list = cubeProcessService.selectHistoryList(historicActivity);
        return getDataTable(list);
    }

    /**
     * 签收
     * @param taskId 任务id
     * @param processInstanceId 流程实例id
     */
    @GetMapping("/claim")
    public AjaxResult claim(String taskId, String processInstanceId) {
        LoginUser loginUser = getLoginUser();
        cubeProcessService.claim(taskId, processInstanceId, loginUser.getUsername());
        return AjaxResult.success();
    }

    /**
     * 取消签收
     * @param taskId 任务id
     */
    @GetMapping("/unclaim")
    public AjaxResult unclaim(String taskId) {
        LoginUser loginUser = getLoginUser();
        cubeProcessService.unclaim(taskId);
        return AjaxResult.success();
    }

    /**
     * 我的已办列表
     */
    @PostMapping("/doneList")
    public TableDataInfo doneList(@RequestBody CubeTask cubeTask) {
        startPage();
        // 获取业务系统当前登陆人并传给流程引擎
        LoginUser loginUser = getLoginUser();
        CurrentUser user = new CurrentUser();
        user.setId(loginUser.getUserId());
        user.setUserName(loginUser.getUsername());
        cubeTask.setCurrentUser(user);

        List<CubeTask> list = cubeProcessService.myDoneList(cubeTask);

        return getDataTable(list);
    }

    /**
     * 获取流程图
     */
    @PostMapping("/processDiagram")
    public AjaxResult getProcessDiagram(@RequestBody Map<String,String> params) {
        Map map = cubeProcessService.getProcessDiagram(params.get("processInstanceId"));
        return AjaxResult.success(map);
    }


    /**
     * 根据部署的模型id获取第一个审批节点信息
     * @param nodeTaskDto
     * @return
     */
    @GetMapping("/getFirstApprovalNode")
    public AjaxResult getFirstApprovalNode(NodeTaskDto nodeTaskDto) {
        return AjaxResult.success(cubeProcessService.getFirstApprovalNode(nodeTaskDto));
    }

    /**
     * 根据流程实例id获取下一个审批节点信息
     * @param instanceId
     * @return
     */
    @GetMapping("/getNextApprovalNode")
    public AjaxResult getNextApprovalNode(String instanceId, String approvedTask) {
        return AjaxResult.success(cubeProcessService.getNextApprovalNode(instanceId, approvedTask));
    }

    /**
     * 委托转办
     * @param delegateDto
     * @return
     */
    @PostMapping("/delegate")
    public AjaxResult delegate(@RequestBody DelegateDto delegateDto) {
        // 获取业务系统当前登陆人并传给流程引擎
        LoginUser loginUser = getLoginUser();
        CurrentUser user = new CurrentUser();
        user.setId(loginUser.getUserId());
        user.setUserName(loginUser.getUsername());
        user.setNickName(loginUser.getUser().getNickName());
        delegateDto.setCurrentUser(user);
        cubeProcessService.delegate(delegateDto);
        return AjaxResult.success();
    }
}
