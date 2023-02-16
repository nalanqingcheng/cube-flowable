package com.liujianan.cubeflowable;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CubeFlowableAppApplicationTests {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    void contextLoads() {
    }

    @Test
    void deployment() {
        // 资源路径
        String path = "leave-applay.bpmn20.xml";
        // 创建部署构建器
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        // 添加资源
        deploymentBuilder.addClasspathResource(path);
        // 执行部署
        deploymentBuilder.deploy();
        // 验证部署
        long count = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leaveTest").count();
        // count等于1，则说明部署成功
        System.out.println("部署成功");
    }

    @Test
    void deployList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        System.out.println(list.toString());
    }

    @Test
    void startProcess() {
        runtimeService.startProcessInstanceByKey("qwj3y412");
    }

    @Test
    void getTasks() {
        String assignee = "zhangsan";
        List list = taskService.createTaskQuery().taskAssignee(assignee).list();
        System.out.println(list.toString());
    }

}
