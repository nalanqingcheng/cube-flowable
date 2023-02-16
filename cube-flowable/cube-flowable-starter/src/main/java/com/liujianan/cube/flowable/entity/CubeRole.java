package com.liujianan.cube.flowable.entity;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeRole
 * @Description: 角色对象
 * @Author: sunyan
 * @Date: 2022/11/28 10:17
 */
public class CubeRole {
    /**
     * id
     */
    private String id;
    /**
     * name
     */
    private String name;

    public CubeRole(){}

    public CubeRole(String id, String name) {
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
}
