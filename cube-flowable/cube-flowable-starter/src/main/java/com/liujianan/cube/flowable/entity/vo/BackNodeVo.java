package com.liujianan.cube.flowable.entity.vo;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: BackNodeVo
 * @Description: 可退回节点vo类
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/29 13:56
 */

public class BackNodeVo {

    private String flowElementId;

    private String flowElementName;

    public String getFlowElementId() {
        return flowElementId;
    }

    public void setFlowElementId(String flowElementId) {
        this.flowElementId = flowElementId;
    }

    public String getFlowElementName() {
        return flowElementName;
    }

    public void setFlowElementName(String flowElementName) {
        this.flowElementName = flowElementName;
    }
}
