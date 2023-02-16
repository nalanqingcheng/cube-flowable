package com.liujianan.cube.flowable.service.impl;

import com.liujianan.cube.flowable.entity.House;
import com.liujianan.cube.flowable.mapper.HouseMapper;
import com.liujianan.cube.flowable.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: HouseServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/13 20:02
 */

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    HouseMapper houseMapper;

    @Override
    public List<House> selectList() {
        return houseMapper.selectList();
    }
}
