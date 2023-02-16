package com.liujianan.cube.flowable.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: AutoCreateTableConfig
 * @Description: 自动创建工作流引擎相关数据表
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/12/12 11:23
 */

@Component
@Order(1)
public class AutoCreateTableConfig implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(AutoCreateTableConfig.class);

    @Autowired
    DataSource dataSource;

    @Value("${cubeflow.dbType}")
    private String dbType;

    @Value("${cubeflow.autoCreateTable}")
    private boolean autoCreateTable = true;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (autoCreateTable) {
            String initSqlScript = null;

            if (dbType.equals("mysql")) {
                logger.info("<init:starting>自动创建Cube工作流引擎数据库脚本：cube_flowable_mysql.sql");
                initSqlScript = "cube_flowable_mysql.sql";
            } else if (dbType.equals("oracle")) {
                logger.info("<init:starting>自动创建Cube工作流引擎数据库脚本：cube_flowable_oracl.sql");
                initSqlScript = "cube_flowable_oracle.sql";
            }
            Resource classPathResource = new ClassPathResource(initSqlScript);
            EncodedResource encodedResource = new EncodedResource(classPathResource, "utf-8");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
            logger.info("<init:end>自动创建Cube工作流引擎数据库脚本结束");
        }
    }
}
