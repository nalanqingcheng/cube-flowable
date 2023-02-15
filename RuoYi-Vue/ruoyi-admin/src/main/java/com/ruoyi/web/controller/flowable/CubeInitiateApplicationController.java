package com.ruoyi.web.controller.flowable;

import com.liujianan.cube.flowable.common.core.web.domain.CurrentUser;
import com.liujianan.cube.flowable.entity.CubeProcessDefinition;
import com.liujianan.cube.flowable.entity.dto.ApplayFormDto;
import com.liujianan.cube.flowable.service.CubeInitiateApplicationService;
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

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: RuoYi-Vue
 * @ClassName: CubeInitiateApplicationController
 * @Description: 发起申请controller
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/23 13:03
 */

@RestController
@RequestMapping("/flowable/applay")
public class CubeInitiateApplicationController extends BaseController {

    @Autowired
    private CubeInitiateApplicationService cubeInitiateApplicationService;

    /**
     * 获取发起申请列表
     * @param processDefinition
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(CubeProcessDefinition processDefinition) {
        List<CubeProcessDefinition> list = cubeInitiateApplicationService.initiateApplicationList(processDefinition);
        return getDataTable(list);
    }

    /**
     * 根据部署模型id查找表单结构并渲染
     * @param deploymentId
     * @return
     */
    @GetMapping("/queryFormByDeploymentId")
    public AjaxResult queryFormByDeploymentId(String deploymentId) {
        return AjaxResult.success(cubeInitiateApplicationService.queryFormByDeploymentId(deploymentId));
    }

    /**
     * 暂存表单
     * @param applayFormDto
     * @return
     */
    @PostMapping("/tempSave")
    public AjaxResult tempSave(@RequestBody ApplayFormDto applayFormDto) {
        try {
            // 获取业务系统当前登陆人并传给流程引擎
            LoginUser loginUser = getLoginUser();
            CurrentUser user = new CurrentUser();
            user.setId(loginUser.getUserId());
            user.setUserName(loginUser.getUsername());
            user.setNickName(loginUser.getUser().getNickName());
            applayFormDto.setCurrentUser(user);
            cubeInitiateApplicationService.tempSave(applayFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("暂存表单报错");
        }
        return AjaxResult.success();
    }

    /**
     * 获取表单数据
     * @return
     */
    @GetMapping("/getBusinessFormData")
    public AjaxResult getBusinessFormData(String businessKey, String tableCode) {
        return AjaxResult.success(cubeInitiateApplicationService.getBusinessFormData(businessKey, tableCode));
    }

}
