# cube-flowable工作流引擎

## 简介

cube-flowable工作流引擎旨在打造一套零代码、领先、且快速实用的引擎工具，助力开发者在面对工作流开发任务时，除去学习工作流框架知识和API的学习成本且不去关心工作流是什么技术，无需了解学习，安装使用cube-flowable工作流引擎并应用落地。此工作流引擎是零代码或低代码的工作流引擎，安装配置开箱即可使用，完全适用于中国国情的工作流引擎。主要特点包括：在线拖拽可视化业务表单并自动创建业务表，关联业务表单创建工作流并在线设计工作流程模型，发布流程模型生成实例，在线申请，在线审批，撤回，驳回至节点，多实例任务审批，多重网关条件审批，指定工作流节点分支审批，委托（转办）办理，流程进度监控跟踪，流程历史日志跟踪等等丰富功能。

## 近期更新

- 2022.12.15 V 0.1.10 版本发布[公开测试版]


## 下载工作流引擎组件
* 前端引擎安装
```
npm i @liujianan/cube-vue-bpmn
```
* 后端服务引用
pom文件加入以下依赖
```java
<dependency>
    <groupId>com.liujianan</groupId>
    <artifactId>cube-flowable-starter</artifactId>
    <version>0.1.10</version>
</dependency>

<repositories>
    <!-- 阿里云镜像仓库 -->
    <repository>
        <id>aliyun</id>
        <name>aliyun Repository</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </repository>
    <!-- 私服 -->
     <repository>
        <id>xinjishu-releases</id>
        <name>maven-releases</name>        
        <url>http://192.168.28.90:8081/repository/maven-releases/</url>
    </repository>
</repositories>
```

### 前端引用组件，在自己项目的main.js里
```
import store from "./store"

// 使用工作流
import CubeVueBpmn from '@liujianan/cube-vue-bpmn'
import '@liujianan/cube-vue-bpmn/lib/cube-vue-bpmn.css'
Vue.use(CubeVueBpmn, { store })
```
### 后端服务配置
* 数据源连接参数配置
``` java
useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true
```
* mybatis配置，配置mapper的扫描，找到所有的mapper.xml映射文件
``` java
# MyBatis配置
mybatis:
    # 搜索指定包别名
    typeAliasesPackage: com.ruoyi.**.domain
    # 配置mapper的扫描，找到所有的mapper.xml映射文件
    mapperLocations: classpath*:mapper/**/*Mapper.xml,classpath:cube/flowable/mapper/*.xml
    # 加载全局的配置文件
    configLocation: classpath:mybatis/mybatis-config.xml
```
* 分页配置
``` java
# PageHelper分页插件
pagehelper: 
  helperDialect: mysql
  supportMethodsArguments: true
  params: count=countSql 
```
* 指定要扫描的Mapper类的包的路径配置
``` java
// 指定要扫描的Mapper类的包的路径
@MapperScan(basePackages = {"com.ruoyi.**.mapper", "com.liujianan.cube.flowable.mapper"})
```

## 文档教程

- [初始化用户和组织机构](./doc/%E7%94%A8%E6%88%B7%E6%93%8D%E4%BD%9C.md)
- [表单设计](./doc/%E8%A1%A8%E5%8D%95%E8%AE%BE%E8%AE%A1.md)
- [流程设计](./doc/%E6%B5%81%E7%A8%8B%E8%AE%BE%E8%AE%A1.md)
    - [流程设计器](./doc/%E6%B5%81%E7%A8%8B%E8%AE%BE%E8%AE%A1%E5%99%A8.md)
- [流程定义](./doc/%E6%B5%81%E7%A8%8B%E5%AE%9A%E4%B9%89.md)
- [发起申请](./doc/%E5%8F%91%E8%B5%B7%E7%94%B3%E8%AF%B7.md)
- [我的申请](./doc/%E6%88%91%E7%9A%84%E7%94%B3%E8%AF%B7.md)
- [我的待办](./doc/%E6%88%91%E7%9A%84%E5%BE%85%E5%8A%9E.md)
- [我的已办](./doc/%E6%88%91%E7%9A%84%E5%B7%B2%E5%8A%9E.md)
