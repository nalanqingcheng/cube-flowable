package com.ruoyi.web.controller.flowable;

import com.liujianan.cube.flowable.entity.CubeRole;
import com.liujianan.cube.flowable.entity.CubeUser;
import com.liujianan.cube.flowable.service.CubeDeptService;
import com.liujianan.cube.flowable.service.CubeUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: RuoYi-Vue
 * @ClassName: CubeUserControlle
 * @Description: 用户相关controller
 * @Author: sunyan
 * @Date: 2022/11/29 10:10
 */
@RestController
public class CubeUserController extends BaseController {

    @Autowired
    private CubeDeptService cubeDeptService;

    @Autowired
    private CubeUserService cubeUserService;

    @PostMapping("/deptTree")
    public AjaxResult getDeptTree(){
        return AjaxResult.success(cubeDeptService.selectDeptTree());
    }

    @PostMapping("/list/user")
    public TableDataInfo getUserList(@RequestBody Map<String,String> params){
        startPage();
        List<CubeUser> list = cubeUserService.selectUserList(params.get("userName"),params.get("deptId"));
        return getDataTable(list);
    }

    @PostMapping("/list/role")
    public TableDataInfo getRoleList(@RequestBody CubeRole cubeRole){
        startPage();
        List<CubeRole> list = cubeUserService.getRoleList(cubeRole);
        return getDataTable(list);
    }
}
