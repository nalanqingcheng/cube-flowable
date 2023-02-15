package com.ruoyi.web.controller.flowable;

import com.liujianan.cube.flowable.entity.*;
import com.liujianan.cube.flowable.service.CubeDeptService;
import com.liujianan.cube.flowable.service.CubeNodeConfigService;
import com.liujianan.cube.flowable.service.CubeProcessDefinitionService;
import com.liujianan.cube.flowable.service.CubeUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.dto.ProcessDefinitionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @version v1.0
 * @ProjectName: RuoYi-Vue
 * @ClassName: ProcessDefinition
 * @Description: 流程定义controller
 * @Author: sunyan
 * @Date: 2022/11/21 14:50
 */
@RestController
@RequestMapping("/process/definition")
public class ProcessDefinitionController extends BaseController{

    @Autowired
    private CubeProcessDefinitionService cubeProcessDefinitionService;

    @Autowired
    private CubeNodeConfigService cubeNodeConfigService;


    @PostMapping("/list")
    public TableDataInfo list(@RequestBody ProcessDefinitionDto processDefinitionDto){
        Integer pageNum = processDefinitionDto.getPageNum();
        Integer pageSize = processDefinitionDto.getPageSize();
        CubeProcessDefinition processDefinition = new CubeProcessDefinition();
        processDefinition.setCategory(processDefinitionDto.getCategory());
        processDefinition.setKey(processDefinitionDto.getKey());
        processDefinition.setName(processDefinitionDto.getName());

        List<CubeProcessDefinition> list = cubeProcessDefinitionService.listProcessDefinition(processDefinition,pageNum,pageSize);
        return getDataTable(list);
    }

    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody Map<String,Object> params){
        cubeProcessDefinitionService.delete((String) params.get("processDefinitionId"),(Boolean) params.get("cascade"));
        return AjaxResult.success();
    }

    @PostMapping("/getXml")
    public AjaxResult getXml(@RequestBody Map<String,String> params){
        return AjaxResult.success(cubeProcessDefinitionService.getProcessXmlStr(params.get("deploymentId"),params.get("resourceName")));
    }


    @PostMapping("/suspendOrActiveDefinition")
    public AjaxResult suspendOrActiveDefinition(@RequestBody Map<String,String> params){
        cubeProcessDefinitionService.suspendOrActiveDefinition(params.get("id"),params.get("suspendState"));
        return AjaxResult.success();
    }

    @PostMapping("/list/node")
    public AjaxResult nodeList(@RequestBody Map<String,String> params){
        return AjaxResult.success(cubeProcessDefinitionService.getNodeList(params.get("procDefId")));
    }

    @PostMapping("/node/config")
    public AjaxResult getNodeConfig(@RequestBody Map<String,String> params){
        return AjaxResult.success(cubeNodeConfigService.getNodeConfigList(params.get("procDefId"),params.get("nodeId")));
    }

    @PostMapping("/node/config/save")
    public AjaxResult saveNodeConfig(@RequestBody List<CubeNodeConfig> list){
        LoginUser loginUser = getLoginUser();
        cubeNodeConfigService.updateNodeConfig(list,loginUser.getUsername());
        return AjaxResult.success();
    }

}
