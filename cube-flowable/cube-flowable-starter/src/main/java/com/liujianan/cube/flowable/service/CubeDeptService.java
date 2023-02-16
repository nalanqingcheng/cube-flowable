package com.liujianan.cube.flowable.service;

import com.liujianan.cube.flowable.entity.CubeDepartment;
import com.liujianan.cube.flowable.entity.CubeDeptUserMapping;
import com.liujianan.cube.flowable.entity.vo.TreeSelect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeDeptService
 * @Description: 部门管理 service
 * @Author: sunyan
 * @Date: 2022/11/22 15:32
 */
public interface CubeDeptService {

    /**
     * 查询部门列表
     * @return 部门列表
     */
    List<TreeSelect> selectDeptTree();

    /**
     * 保存部门
     * @param cubeDepartment 部门对象
     * @return 修改数据库条数
     */
    int addCubeDept(CubeDepartment cubeDepartment);

    /**
     * 保存部门列表
     * @param cubeDepartmentList 部门对象列表
     * @return 修改数据库条数
     */
    int addCubeDept(List<CubeDepartment> cubeDepartmentList);

    /**
     * 根据部门id删除部门
     * @param deptId 部门id
     * @return 修改数据库条数
     */
    int deleteDept(String deptId);

    /**
     * 设置部门的用户
     * @param cubeDeptUserMapping 部门用户映射关系对象
     * @return 修改数量
     */
    int setDeptUser(CubeDeptUserMapping cubeDeptUserMapping);

    /**
     * 设置部门的用户列表
     * @param cubeDeptUserMappingList 部门用户映射关系对象列表
     * @return 修改数量
     */
    int setDeptUser(List<CubeDeptUserMapping> cubeDeptUserMappingList);

    /**
     * 删除用户和部门关系
     * @param userId 用户id
     * @param deptId 部门id
     */
    void deleteDeptUser(String userId,String deptId);
}
