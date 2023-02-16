package com.liujianan.cube.flowable.mapper;

import com.liujianan.cube.flowable.entity.House;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: HouseMapper
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/13 19:59
 */

@Repository
public interface HouseMapper {

    public List<House> selectList();

}
