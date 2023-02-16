package com.liujianan.cube.flowable.util;

import java.util.UUID;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: UUIDUtil
 * @Description: UUID工具类
 * @Author: 风清扬 [刘佳男]
 * @Date: 2023/1/4 11:03
 */

public class UUIDUtil {

    /**
     * 封装JDK自带的UUID，通过Random数字生成，中间无-分割
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(uuid());
    }
}
