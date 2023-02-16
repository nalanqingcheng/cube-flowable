package com.liujianan.cube.flowable.entity;

import com.liujianan.cube.flowable.common.core.web.domain.TreeEntity;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowCategory
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/16 16:08
 */

public class CubeFlowCategory extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
