package com.liujianan.cubeflowable.mapper;

import com.liujianan.cubeflowable.entity.Count;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CountMapper
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/13 18:57
 */

@Repository
public interface CountMapper {

    List<Count> selectList();

}
