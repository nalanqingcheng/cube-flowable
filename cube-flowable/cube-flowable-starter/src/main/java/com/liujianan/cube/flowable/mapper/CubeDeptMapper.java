package com.liujianan.cube.flowable.mapper;

import com.liujianan.cube.flowable.entity.CubeDepartment;
import com.liujianan.cube.flowable.entity.CubeDeptUserMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeDeptMapper
 * @Description: 部门相关操作 mapper
 * @Author: sunyan
 * @Date: 2022/11/22 14:48
 */
public interface CubeDeptMapper {


    /**
     * 查询部门列表
     * @return 部门列表
     */
    List<CubeDepartment> selectDeptList();

    /**
     * 根据id查询部门列表
     * @param deptId 部门id
     * @return 部门列表
     */
    CubeDepartment selectDeptListById(String deptId);

    /**
     * 保存部门
     * @param cubeDepartment 部门对象
     * @return 修改数据库条数
     */
    int saveDept(CubeDepartment cubeDepartment);

    /**
     * 保存部门列表
     * @param cubeDepartmentList 部门对象列表
     * @return 修改数据库条数
     */
    int insertDeptList(List<CubeDepartment> cubeDepartmentList);

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
    int saveDeptUser(CubeDeptUserMapping cubeDeptUserMapping);

    /**
     * 设置部门的用户
     * @param cubeDeptUserMappingList 部门用户映射关系对象列表
     * @return 修改数量
     */
    int insertDeptUserList(List<CubeDeptUserMapping> cubeDeptUserMappingList);

    /**
     * 根据用户id查找部门
     * @param userId 用户id
     * @return CubeDepartment
     */
    CubeDepartment selectDeptByUserId(String userId);

    /**
     * 删除部门和用户关系
     * @param userId 用户id
     * @param deptId 部门id
     */
    void deleteDeptUser(@Param("userId") String userId,@Param("deptId")  String deptId);


}
