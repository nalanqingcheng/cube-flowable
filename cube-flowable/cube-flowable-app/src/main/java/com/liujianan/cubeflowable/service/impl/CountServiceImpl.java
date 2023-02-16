package com.liujianan.cubeflowable.service.impl;

import com.liujianan.cubeflowable.entity.Count;
import com.liujianan.cubeflowable.mapper.CountMapper;
import com.liujianan.cubeflowable.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CountService
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/13 19:00
 */

@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private CountMapper countMapper;

    @Override
    public List<Count> selectList() {
        return countMapper.selectList();
    }
}
