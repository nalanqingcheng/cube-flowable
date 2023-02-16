package com.liujianan.cubeflowable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.liujianan.cubeflowable.mapper", "com.liujianan.cube.flowable.mapper"})
public class CubeFlowableAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CubeFlowableAppApplication.class, args);
    }

}
