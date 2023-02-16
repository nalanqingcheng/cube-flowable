package com.liujianan.cube.flowable.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowProperties
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2023/1/3 13:31
 */

@Component
@ConfigurationProperties(prefix = "cubeflow")
public class CubeFlowProperties {

    private String dbType;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
