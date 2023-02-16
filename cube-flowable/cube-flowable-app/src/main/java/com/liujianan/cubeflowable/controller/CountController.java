package com.liujianan.cubeflowable.controller;

import com.liujianan.cube.flowable.entity.House;
import com.liujianan.cube.flowable.service.HouseService;
import com.liujianan.cubeflowable.entity.Count;
import com.liujianan.cubeflowable.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CountController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/13 19:05
 */

@RestController
@RequestMapping(value = "/app")
public class CountController {

    @Autowired
    private CountService countService;

    @Autowired
    private HouseService houseService;

    @GetMapping(value = "countList")
    public List<Count> countList() {
        return countService.selectList();
    }

    @GetMapping(value = "houseList")
    public List<House> houseList() {
        return houseService.selectList();
    }

}
