package com.liujianan.cube.flowable.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeDepartment
 * @Description: 部门实体类
 * @Author: sunyan
 * @Date: 2022/11/22 14:50
 */
public class CubeDepartment implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 父部门id
     */
    private String parentId;
    /**
     * 部门名称
     */
    private String deptName;

    /** 子部门 */
    private List<CubeDepartment> children = new ArrayList<CubeDepartment>();

    public CubeDepartment(){}

    public CubeDepartment(String deptId, String parentId, String deptName) {
        this.deptId = deptId;
        this.parentId = parentId;
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<CubeDepartment> getChildren() {
        return children;
    }

    public void setChildren(List<CubeDepartment> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("deptId", deptId)
                .append("parentId", parentId)
                .append("deptName", deptName)
                .toString();
    }
}
