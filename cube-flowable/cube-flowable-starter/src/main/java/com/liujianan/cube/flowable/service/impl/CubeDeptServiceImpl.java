package com.liujianan.cube.flowable.service.impl;

import com.liujianan.cube.flowable.common.utils.StringUtils;
import com.liujianan.cube.flowable.entity.CubeDepartment;
import com.liujianan.cube.flowable.entity.CubeDeptUserMapping;
import com.liujianan.cube.flowable.entity.vo.TreeSelect;
import com.liujianan.cube.flowable.mapper.CubeDeptMapper;
import com.liujianan.cube.flowable.service.CubeDeptService;
import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeDeptServiceImpl
 * @Description: 部门service实现类
 * @Author: sunyan
 * @Date: 2022/11/22 15:38
 */
@Service
public class CubeDeptServiceImpl implements CubeDeptService {

    @Autowired
    CubeDeptMapper cubeDeptMapper;

    @Override
    public List<TreeSelect> selectDeptTree() {
        return buildDeptTreeSelect(cubeDeptMapper.selectDeptList());
    }

    @Override
    public int addCubeDept(CubeDepartment cubeDepartment) {
        return cubeDeptMapper.saveDept(cubeDepartment);
    }

    @Override
    public int addCubeDept(List<CubeDepartment> cubeDepartmentList) {
        return cubeDeptMapper.insertDeptList(cubeDepartmentList);
    }

    @Override
    public int deleteDept(String deptId) {
        return cubeDeptMapper.deleteDept(deptId);
    }

    @Override
    public int setDeptUser(CubeDeptUserMapping cubeDeptUserMapping) {
        return cubeDeptMapper.saveDeptUser(cubeDeptUserMapping);
    }

    @Override
    public int setDeptUser(List<CubeDeptUserMapping> cubeDeptUserMappingList) {
        return cubeDeptMapper.insertDeptUserList(cubeDeptUserMappingList);
    }

    @Override
    public void deleteDeptUser(String userId, String deptId) {
        cubeDeptMapper.deleteDeptUser(userId,deptId);
    }

    /**
     * 构建下拉选择树
     * @param depts 部门列表
     * @return 树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<CubeDepartment> depts)
    {
        List<CubeDepartment> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建下拉选择树结构
     * @param depts 部门列表
     * @return 部门列表
     */
    public List<CubeDepartment> buildDeptTree(List<CubeDepartment> depts)
    {
        List<CubeDepartment> returnList = new ArrayList<CubeDepartment>();
        List<String> tempList = new ArrayList<String>();
        for (CubeDepartment dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<CubeDepartment> iterator = depts.iterator(); iterator.hasNext();)
        {
            CubeDepartment dept = (CubeDepartment) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<CubeDepartment> list, CubeDepartment t)
    {
        // 得到子节点列表
        List<CubeDepartment> childList = getChildList(list, t);
        t.setChildren(childList);
        for (CubeDepartment tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<CubeDepartment> getChildList(List<CubeDepartment> list, CubeDepartment t)
    {
        List<CubeDepartment> tList = new ArrayList<CubeDepartment>();
        Iterator<CubeDepartment> it = list.iterator();
        while (it.hasNext())
        {
            CubeDepartment n = (CubeDepartment) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().equals(t.getDeptId()))
            {
                tList.add(n);
            }
        }
        return tList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<CubeDepartment> list, CubeDepartment t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
