package com.ruoyi.web.controller.flowable;

import com.liujianan.cube.flowable.common.core.web.domain.CurrentUser;
import com.liujianan.cube.flowable.entity.CubeFlowForm;
import com.liujianan.cube.flowable.service.CubeFlowFormService;
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
 * @ClassName: FormDesignController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/21 9:37
 */

@RestController
@RequestMapping("/flowable/formDesign")
public class FormDesignController extends BaseController {

    @Autowired
    private CubeFlowFormService cubeFlowFormService;

    /**
     * 查询列表
     * @param cubeFlowForm
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(CubeFlowForm cubeFlowForm) {
        startPage();
        List<CubeFlowForm> list = cubeFlowFormService.selectList(cubeFlowForm);
        return getDataTable(list);
    }

    /**
     * 通过id获取表单设计对象
     * @param id
     * @return
     */
    @GetMapping("/getForm")
    public AjaxResult getForm(String id) {
        return AjaxResult.success(cubeFlowFormService.selectFormById(id));
    }

    /**
     * 新增表单设计
     * @param cubeFlowForm
     * @return
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody CubeFlowForm cubeFlowForm) {
        // 获取业务系统当前登陆人并传给流程引擎
        LoginUser loginUser = getLoginUser();
        CurrentUser user = new CurrentUser();
        user.setId(loginUser.getUserId());
        user.setUserName(loginUser.getUsername());
        cubeFlowForm.setCurrentUser(user);
        return AjaxResult.success(cubeFlowFormService.insertWfForm(cubeFlowForm));
    }

    /**
     * 修改表单设计
     * @param cubeFlowForm
     * @return
     */
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody CubeFlowForm cubeFlowForm) {
        // 获取业务系统当前登陆人并传给流程引擎
        LoginUser loginUser = getLoginUser();
        CurrentUser user = new CurrentUser();
        user.setId(loginUser.getUserId());
        user.setUserName(loginUser.getUsername());
        cubeFlowForm.setCurrentUser(user);
        return AjaxResult.success(cubeFlowFormService.updateWfForm(cubeFlowForm));
    }

    /**
     * 删除表单设计
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public AjaxResult delete(Long[] ids) {
        return AjaxResult.success(cubeFlowFormService.deleteWfFormByIds(ids));
    }

    /**
     * 保存设计
     * @param cubeFlowForm
     * @return
     * @throws Exception
     */
    @PostMapping("/saveFormDesign")
    public AjaxResult saveFormDesign(@RequestBody CubeFlowForm cubeFlowForm) throws Exception {
        // 获取业务系统当前登陆人并传给流程引擎
        LoginUser loginUser = getLoginUser();
        CurrentUser user = new CurrentUser();
        user.setId(loginUser.getUserId());
        user.setUserName(loginUser.getUsername());
        cubeFlowForm.setCurrentUser(user);
        return AjaxResult.success(cubeFlowFormService.saveFormDesign(cubeFlowForm, "cube-ruoyi"));
    }
}
