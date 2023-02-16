package com.liujianan.cube.flowable.entity;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeUserVo
 * @Description: Cube用户对象
 * @Author: sunyan
 * @Date: 2022/11/28 9:34
 */
public class CubeUser {

    /**
     * id
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门名称
     */
    private String dept;

    public CubeUser(){}

    public CubeUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
