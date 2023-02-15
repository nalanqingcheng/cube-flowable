package com.ruoyi.web.controller.flowable;

import com.liujianan.cube.flowable.entity.ModelRequest;
import com.liujianan.cube.flowable.service.CubeFlowableModelerService;
import com.liujianan.cube.flowable.util.CubePage;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.dto.ProcessDesignDto;
import org.flowable.engine.impl.persistence.entity.ModelEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version v1.0
 * @ProjectName: RuoYi-Vue
 * @ClassName: ProcessDesignController
 * @Description: 流程设计 控制层
 * @Author: 张馨月
 * @Date: 2022/11/22 13:58
 */
@RestController
@RequestMapping("/flowable/processDesign")
public class ProcessDesignController extends BaseController {

    @Autowired
    private CubeFlowableModelerService cubeFlowableModelerService;

    /**
     * 查询流程模型列表
     * @param processDesignDto  模型对象参数
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(ProcessDesignDto processDesignDto) {
        startPage();
        Integer pageNum = processDesignDto.getPageNum();
        Integer pageSize = processDesignDto.getPageSize();
        ModelEntityImpl modelEntity = new ModelEntityImpl();
        modelEntity.setKey(processDesignDto.getKey());
        modelEntity.setName(processDesignDto.getName());
        CubePage list = cubeFlowableModelerService.modelerList(modelEntity, pageNum, pageSize);
        return getDataTable(list);
    }

    /**
     * 根据流程模型id获取流程模型信息
     * @param modelId 流程模型id
     * @return
     */
    @GetMapping(value = "/getModeler")
    public AjaxResult getModeler(String modelId) {
        return AjaxResult.success(cubeFlowableModelerService.queryModelerById(modelId));
    }

    /**
     * 创建流程模型
     * @param processDesignDto 模型对象参数
     * @return
     */
    @PostMapping(value = "/create")
    public AjaxResult create(@RequestBody ProcessDesignDto processDesignDto) {
        return AjaxResult.success(cubeFlowableModelerService.createModeler(processDesignDto.getKey(),
                processDesignDto.getName(),
                processDesignDto.getFormId(),
                processDesignDto.getCategory(),
                processDesignDto.getDescription()));
    }

    /**
     * 保存模型编辑器
     * @param modelRequest 模型对象
     */
    @PostMapping(value = "/saveModelEditor")
    public AjaxResult saveModelEditor(@RequestBody ModelRequest modelRequest) {
        cubeFlowableModelerService.saveModelEditor(modelRequest);
        return AjaxResult.success();
    }

    /**
     * 修改流程模型
     * @param modelRequest 模型对象
     */
    @PostMapping(value = "/update")
    public AjaxResult update(@RequestBody ModelRequest modelRequest) {
        cubeFlowableModelerService.updateModeler(modelRequest);
        return AjaxResult.success();
    }

    /**
     * 根据模型ID删除信息
     * @param modelId 流程模型id
     * @param cascade 是否级联删除
     */
    @GetMapping("/remove")
    public AjaxResult remove(String modelId, boolean cascade) {
        cubeFlowableModelerService.deleteModeler(modelId, cascade);
        return AjaxResult.success();
    }

    /**
     * 部署流程设计
     * @param modelId 流程模型id
     * @return
     */
    @PostMapping(value = "/deploy")
    public AjaxResult deployModel(String modelId) {
        cubeFlowableModelerService.deployModeler(modelId);
        return AjaxResult.success();
    }

    /**
     * 根据流程模型id获取流程模型信息
     * @param modelId 流程模型id
     * @return
     */
    @GetMapping(value = "/queryModelerById")
    public AjaxResult queryModelerById(String modelId) {
        return AjaxResult.success(cubeFlowableModelerService.queryModelerById(modelId));
    }

}
