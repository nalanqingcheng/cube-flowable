package com.liujianan.cube.flowable.mapper;

import com.liujianan.cube.flowable.entity.CubeTask;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeTaskMapper
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/24 8:58
 */

@Repository
public interface CubeTaskMapper {

    /**
     * 我的申请列表
     * @param task
     * @return
     */
    List<CubeTask> findApplicationList(CubeTask task);

    /**
     * 我的申请列表总数
     * @param task
     * @return
     */
    Integer findApplicationCount(CubeTask task);

    /**
     * 我的待办列表
     * @param task
     * @return
     */
    List<CubeTask> findTodoList(CubeTask task);

    /**
     * 我的待办列表总数
     * @param task
     * @return
     */
    Integer findTodoCount(CubeTask task);

    /**
     * 我的已办列表
     * @param task
     * @return
     */
    List<CubeTask> findDoneList(CubeTask task);

    /**
     * 我的已办列表总数
     * @param task
     * @return
     */
    Integer findDoneCount(CubeTask task);

    /**
     * 激活节点列表
     * @param instanceId 实例id
     * @return 节点列表
     */
    List<String> getActiveActivityIds(String instanceId);

}
