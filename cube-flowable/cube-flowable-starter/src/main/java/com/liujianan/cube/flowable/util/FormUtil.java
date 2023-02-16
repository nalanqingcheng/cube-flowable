package com.liujianan.cube.flowable.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: FormUtil
 * @Description: 流程表单工具类
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/15 11:09
 */

public class FormUtil {

    /**
     * 加载表字段类型 map
     * @return
     */
    public static Map<String, String> loadDataTypeMap() {
        Map<String, String> map = new HashMap<>();
        map.put("input", "VARCHAR(255) DEFAULT ''"); // 单行文本
        map.put("textarea", "VARCHAR(1000) DEFAULT ''"); // 多行文本
        map.put("password", "VARCHAR(50) DEFAULT ''"); // 密码框
        map.put("number", "FLOAT DEFAULT NULL"); // 计数器
        //map.put("select", "TINYINT(4) DEFAULT NULL"); // 下拉选择
        map.put("select", "VARCHAR(100) DEFAULT ''"); // 下拉选择
        map.put("cascader", "VARCHAR(100) DEFAULT ''"); // 级联选择
        //map.put("radio", "TINYINT(4) DEFAULT NULL"); // 单选框组
        map.put("radio", "VARCHAR(100) DEFAULT ''"); // 单选框组
        //map.put("checkbox", "VARCHAR(100) DEFAULT ''"); // 多选框组
        map.put("checkbox", "VARCHAR(1000) DEFAULT ''"); // 多选框组
        //map.put("switch", "BIT(1) DEFAULT NULL"); // 开关
        map.put("switch", "VARCHAR(10) DEFAULT ''"); // 开关
        //map.put("slider", "TINYINT(3) DEFAULT NULL"); // 滑块
        map.put("slider", "VARCHAR(10) DEFAULT ''"); // 滑块
        map.put("time", "VARCHAR(8) DEFAULT NULL"); // 时间选择
        map.put("time-range", "VARCHAR(17) DEFAULT NULL"); // 时间范围
        map.put("date", "VARCHAR(10) DEFAULT NULL"); // 日期选择
        map.put("date-range", "VARCHAR(21) DEFAULT NULL"); // 日期范围
        map.put("rate", "TINYINT(4) DEFAULT NULL"); // 评分
        map.put("color", "VARCHAR(7) DEFAULT ''"); // 颜色选择
        map.put("upload", "VARCHAR(1000) DEFAULT ''"); // 上传
        map.put("tree-table", "VARCHAR(100) DEFAULT ''");//下拉树
        map.put("tree", "VARCHAR(100) DEFAULT ''");//树组件
        return map;
    }

}
