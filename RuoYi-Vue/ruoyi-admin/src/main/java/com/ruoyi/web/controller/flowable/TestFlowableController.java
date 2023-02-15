package com.ruoyi.web.controller.flowable;

import com.liujianan.cube.flowable.entity.House;
import com.liujianan.cube.flowable.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: RuoYi-Vue
 * @ClassName: TestFlowableController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/14 10:50
 */

@RestController
@RequestMapping("/flowable")
public class TestFlowableController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/test")
    public List<House> selectList() {
        return houseService.selectList();
    }

}
