package com.liujianan.cube.flowable.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liujianan.cube.flowable.common.core.web.domain.CurrentUser;
import com.liujianan.cube.flowable.common.utils.DateUtils;
import com.liujianan.cube.flowable.common.utils.StringUtils;
import com.liujianan.cube.flowable.constant.FlowableConstant;
import com.liujianan.cube.flowable.entity.CubeFlowForm;
import com.liujianan.cube.flowable.entity.CubeFlowableInstanceBusiness;
import com.liujianan.cube.flowable.entity.dto.ApplayFormDto;
import com.liujianan.cube.flowable.mapper.CubeFlowFormMapper;
import com.liujianan.cube.flowable.mapper.CubeFlowableInstanceBusinessMapper;
import com.liujianan.cube.flowable.service.CubeFlowFormService;
import com.liujianan.cube.flowable.service.CubeProcessService;
import com.liujianan.cube.flowable.util.FormUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowFormServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/20 16:02
 */

@Service
public class CubeFlowFormServiceImpl implements CubeFlowFormService {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private CubeFlowFormMapper cubeFlowFormMapper;

    @Autowired
    private CubeFlowableInstanceBusinessMapper instanceBusinessMapper;

    @Autowired
    private CubeProcessService cubeProcessService;

    @Override
    public List<CubeFlowForm> selectList(CubeFlowForm cubeFlowForm) {
        List<CubeFlowForm> list = cubeFlowFormMapper.selectWfFormList(cubeFlowForm);
        if (StringUtils.isNotEmpty(cubeFlowForm.getFilter()) && "1".equals(cubeFlowForm.getFilter())) {
            list = cubeFlowFormMapper.selectWfFormListFilter(cubeFlowForm);
        }
        return list;
    }

    @Override
    public CubeFlowForm selectFormById(Long id) {
        return cubeFlowFormMapper.selectWfFormById(id);
    }

    @Override
    public int insertWfForm(CubeFlowForm cubeFlowForm) {
        CurrentUser currentUser = cubeFlowForm.getCurrentUser();
        cubeFlowForm.setCreateTime(new Date());
        cubeFlowForm.setCreateBy(currentUser.getUserName());
        return cubeFlowFormMapper.insertWfForm(cubeFlowForm);
    }

    @Override
    public int updateWfForm(CubeFlowForm cubeFlowForm) {
        CurrentUser currentUser = cubeFlowForm.getCurrentUser();
        cubeFlowForm.setUpdateTime(new Date());
        cubeFlowForm.setUpdateBy(currentUser.getUserName());
        return cubeFlowFormMapper.updateWfForm(cubeFlowForm);
    }

    @Override
    public int deleteWfFormByIds(Long[] ids) {
        return cubeFlowFormMapper.deleteWfFormByIds(ids);
    }

    @Override
    public int deleteWfFormById(Long id) {
        return cubeFlowFormMapper.deleteWfFormById(id);
    }

    @Override
    public int saveFormDesign(CubeFlowForm cubeFlowForm, String tableSchema) throws Exception {

        CurrentUser currentUser = cubeFlowForm.getCurrentUser();
        cubeFlowForm.setUpdateTime(new Date());
        cubeFlowForm.setUpdateBy(currentUser.getUserName());
        /**
         * 判断表单类型，0：表单设计器；1：自定义表单，
         * 如果是表单设计器则动态建表，如果是自定义表单则存储表单路径
         */
        if (cubeFlowForm.getFormType().equals("0")) {
            if (Objects.nonNull(cubeFlowForm.getFormData())) {
                dynamicCreateTable(cubeFlowForm.getFormData(), tableSchema);
            }
        }
        return cubeFlowFormMapper.updateWfForm(cubeFlowForm);
    }

    @Override
    public Long saveFormData(ApplayFormDto applayFormDto) throws Exception {

        Long busindessKey = applayFormDto.getBusinessKey();
        String deploymentId = applayFormDto.getDeploymentId();
        String tableCode = applayFormDto.getTableCode();
        // 获取当前登陆人
        CurrentUser currentUser = applayFormDto.getCurrentUser();
        String userName = currentUser.getUserName();
        String nickName = currentUser.getNickName();

        CubeFlowForm cubeFlowForm = new CubeFlowForm();
        cubeFlowForm.setBusinessKey(busindessKey);
        cubeFlowForm.setTableCode(tableCode);
        cubeFlowForm.setFormData(applayFormDto.getFormData());
        cubeFlowForm.setCurrentUser(currentUser);

        // 业务表id
        busindessKey = cubeFlowForm.getBusinessKey();;
        if (Objects.isNull(busindessKey)) {
            busindessKey = this.handleInsertFormData(cubeFlowForm);
        } else {
            this.handleUpdateFormData(cubeFlowForm);
        }

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(applayFormDto.getDeploymentId())
                .singleResult();

        // 流程申请标题
        String title = processDefinition.getName() + "_" + nickName;

        if (Objects.isNull(busindessKey)) {
            // 记录流程实例业务关系
            CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
            // cfib.setInstanceId(instance.getId());
            cfib.setBusinessKey(busindessKey + "");
            cfib.setTableCode(tableCode);
            cfib.setStatus(FlowableConstant.STATUS_TO_APPLY);
            cfib.setResult(FlowableConstant.RESULT_TO_SUBMIT);
            // cfib.setApplayTime(new Date());
            cfib.setDeploymentId(deploymentId);
            cfib.setTitle(title);
            cfib.setProcDefId(processDefinition.getId());
            cfib.setProcdesName(processDefinition.getName());
            instanceBusinessMapper.insertInstanceBusiness(cfib);
        } else {
//            StringBuilder updateBuilder2 = new StringBuilder(" UPDATE cube_flowable_instance_busines SET INSTANCE_ID = '");
//            updateBuilder2.append(instance.getId());
//            updateBuilder2.append("', CREATE_USER_ID = NULL, CREATE_TIME = NULL WHERE 1=1 AND TABLE_CODE = '");
//            updateBuilder2.append(tableCode);
//            updateBuilder2.append("' AND BUSINESS_KEY = ");
//            updateBuilder2.append(id);
//            System.out.println("updateBuilder2: " + updateBuilder2.toString());
//            cubeFlowFormMapper.rawUpdateSql(updateBuilder2.toString());

            CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
            //cfib.setInstanceId(instance.getId());
            cfib.setTableCode(tableCode);
            cfib.setBusinessKey(busindessKey + "");
            cfib.setStatus(FlowableConstant.STATUS_TO_APPLY);
            cfib.setResult(FlowableConstant.RESULT_TO_SUBMIT);
            // cfib.setApplayTime(new Date());
            cfib.setDeploymentId(deploymentId);
            cfib.setTitle(title);
            cfib.setProcDefId(processDefinition.getId());
            cfib.setProcdesName(processDefinition.getName());
            instanceBusinessMapper.updateInstanceBusiness(cfib);

        }

        return busindessKey;
    }

    @Override
    public CubeFlowForm selectFormDesignByDeploymentId(String deploymentId) {
        return cubeFlowFormMapper.selectFormDesignByDeploymentId(deploymentId);
    }

//    @Override
//    public int submitApply(CubeFlowForm cubeFlowForm) throws Exception {
//        // 业务表id
//        Long id = cubeFlowForm.getBusinessKey();
//        if (Objects.isNull(id)) {
//            id = this.handleInsertFormData(cubeFlowForm);
//        } else {
//            this.handleUpdateFormData(cubeFlowForm);
//        }
//
//        String tableCode = cubeFlowForm.getTableCode();
//
//        // 获取当前登陆人
//        CurrentUser currentUser = cubeFlowForm.getCurrentUser();
//        String userName = currentUser.getUserName();
//        String nickName = currentUser.getNickName();
//
////        R<LoginUser> loginUserR = remoteUserService.getUserInfo(SecurityUtils.getUsername());
////        String nickName = loginUserR.getData().getSysUser().getNickName();
//        // System.err.println("NAME:" + loginUserR.getData().getSysUser().getUserName());
//
//        // 更新流程通用字段
//        StringBuilder updateBuilder = new StringBuilder(" UPDATE ");
//        updateBuilder.append(tableCode);
//        updateBuilder.append(" SET apply_user_id = '");
//        updateBuilder.append(userName);
//        updateBuilder.append("', apply_user_name ='");
//        updateBuilder.append(nickName);
//        updateBuilder.append("', apply_time ='");
//        updateBuilder.append(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
//        updateBuilder.append("', process_key = '");
//        updateBuilder.append(tableCode);
//        updateBuilder.append("', update_user_id = '");
//        updateBuilder.append(userName);
//        updateBuilder.append("'");
//        updateBuilder.append(", update_user_name = '");
//        updateBuilder.append(nickName);
//        updateBuilder.append("'");
//        updateBuilder.append(", update_time = '");
//        updateBuilder.append(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
//        updateBuilder.append("', instance_id = '");
//
//        Map variables = JSON.parseObject(cubeFlowForm.getFormData());
//
//        String deploymentId = cubeFlowForm.getDeploymentId();
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .deploymentId(deploymentId)
//                .singleResult();
//
//        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
//        identityService.setAuthenticatedUserId(userName);
//
//        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
//        // 启动流程时设置业务 key
//        processInstanceBuilder.businessKey(tableCode)
//                .businessKey(id + "")
//                .variables(variables)
//                .processDefinitionId(processDefinition.getId())
//                .name(processDefinition.getName() + "_" + nickName + "_" + DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, new Date()));
//
//        ProcessInstance instance = processInstanceBuilder.start();
//
//        identityService.setAuthenticatedUserId(null);
//
//        // 更新业务表流程实例id字段
//        updateBuilder.append(instance.getId());
//        updateBuilder.append("' where id = ");
//        updateBuilder.append(id);
//
//        System.out.println("update sql: " + updateBuilder.toString());
//
//        cubeFlowFormMapper.rawUpdateSql(updateBuilder.toString());
//
//        if (Objects.isNull(id)) {
//            // 记录流程实例业务关系
//            CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
//            cfib.setInstanceId(instance.getId());
//            cfib.setBusinessKey(id + "");
//            cfib.setTableCode(tableCode);
//            cfib.setStatus(FlowableConstant.STATUS_DEALING);
//            cfib.setResult(FlowableConstant.RESULT_DEALING);
//            cfib.setApplayTime(new Date());
//            instanceBusinessMapper.insertInstanceBusiness(cfib);
//        } else {
////            StringBuilder updateBuilder2 = new StringBuilder(" UPDATE cube_flowable_instance_busines SET INSTANCE_ID = '");
////            updateBuilder2.append(instance.getId());
////            updateBuilder2.append("', CREATE_USER_ID = NULL, CREATE_TIME = NULL WHERE 1=1 AND TABLE_CODE = '");
////            updateBuilder2.append(tableCode);
////            updateBuilder2.append("' AND BUSINESS_KEY = ");
////            updateBuilder2.append(id);
////            System.out.println("updateBuilder2: " + updateBuilder2.toString());
////            cubeFlowFormMapper.rawUpdateSql(updateBuilder2.toString());
//
//            CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
//            cfib.setInstanceId(instance.getId());
//            cfib.setTableCode(tableCode);
//            cfib.setBusinessKey(id + "");
//            cfib.setStatus(FlowableConstant.STATUS_DEALING);
//            cfib.setResult(FlowableConstant.RESULT_DEALING);
//            cfib.setApplayTime(new Date());
//            instanceBusinessMapper.updateInstanceBusiness(cfib);
//
//        }
//
//        String instanceId = instance.getId();
//
//        //分配第一个任务用户
//        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instanceId).list();
//        for (Task t : tasks) {
//            String processDefinKey = t.getProcessDefinitionId();
//
//        }
//
//        return 0;
//    }



    /**
     * 动态建表
     * @param formData 表单数据
     */
    private void dynamicCreateTable(String formData, String tableSchema) throws Exception {
        JSONObject formDataObj = JSONObject.parseObject(formData);
        String tableCode = formDataObj.getString("tableCode"); // 表名
        String tableComment = formDataObj.getString("formRef"); // 表名备注
        if (Objects.isNull(tableCode)) {
            throw new Exception("表名不能为空");
        }

        // 如果物理表已有数据，则报错
        String isExistsTable = " select t.table_name from information_schema.TABLES t where t.TABLE_SCHEMA ='" + tableSchema + "' and t.TABLE_NAME ='" + tableCode + "' ";
        Map<String, Object> objectMap = cubeFlowFormMapper.rawSelectSql(isExistsTable);
        if ((!Objects.isNull(objectMap) && tableCode.equals(objectMap.get("table_name")))
                && Integer.valueOf(cubeFlowFormMapper.rawSelectSql(" SELECT COUNT(1) COUNT FROM " + tableCode).get("COUNT").toString()) > 0) {
            throw new RuntimeException("保存失败：该表单对应物理表已有数据");
        }

        JSONArray fields = formDataObj.getJSONArray("fields");
        if (CollectionUtils.isEmpty(fields)) {
            throw new Exception("字段不能为空");
        }
        StringBuilder builder = new StringBuilder(" CREATE TABLE ");
        builder.append(tableCode);
        builder.append(" ( \n ");
        builder.append("\tid BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n ");
        for (int i = 0; i < fields.size(); i++) {
            JSONObject field = (JSONObject) fields.get(i);
            String column = field.getString("__vModel__"); // 字段名称
            JSONObject config = (JSONObject) field.get("__config__");
            String dataTypeKey = config.getString("tagIcon"); // 数据类型 key
            String comment = config.getString("label"); // 字段备注
            String dataTypeVal = FormUtil.loadDataTypeMap().get(dataTypeKey); // 真正的数据类型
            if (Objects.isNull(dataTypeVal)) {
                throw new Exception("暂不支持表单组件：" + dataTypeKey);
            }
            builder.append("\t");
            builder.append(column);
            builder.append(" ");
            builder.append(dataTypeVal);
            builder.append(" COMMENT '");
            builder.append(comment);
            builder.append("',\n ");
        }
        // 通用字段
        builder.append("\t`apply_user_id` varchar(100) DEFAULT '' COMMENT '申请人',\n" +
                "\t`apply_user_name` varchar(100) DEFAULT '' COMMENT '申请人姓名',\n" +
                "\t`apply_time` datetime DEFAULT NULL COMMENT '申请时间',\n" +
                "\t`instance_id` varchar(36) DEFAULT '' COMMENT '流程实例ID',\n" +
                "\t`process_key` varchar(32) DEFAULT '' COMMENT '流程定义key',\n" +
                "\t`del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',\n" +
                "\t`create_user_id` varchar(64) DEFAULT '' COMMENT '创建者',\n" +
                "\t`create_user_name` varchar(64) DEFAULT '' COMMENT '创建者姓名',\n" +
                "\t`create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "\t`update_user_id` varchar(64) DEFAULT '' COMMENT '更新者',\n" +
                "\t`update_user_name` varchar(64) DEFAULT '' COMMENT '更新者姓名',\n" +
                "\t`update_time` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "\t`remark` varchar(500) DEFAULT '' COMMENT '备注',\n");
        builder.append(" \tPRIMARY KEY (`id`)\n ");
        builder.append(" ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='");
        builder.append(tableComment);
        builder.append("' ");
        System.out.println("执行建表 SQL 语句：");
        System.out.println(builder.toString());
        cubeFlowFormMapper.dropTableIfExists(tableCode);
        cubeFlowFormMapper.rawUpdateSql(builder.toString());
    }

    private Long handleInsertFormData(CubeFlowForm wfForm) {
        // 物理表名
        String tableCode = wfForm.getTableCode();
        if (StringUtils.isEmpty(tableCode)) {
            throw new RuntimeException("物理表名不能为空");
        }
        Map<String, Object> formData = (Map<String, Object>) JSONObject.parse(wfForm.getFormData());

        // 所有要存储的字段和值
        Set<Map.Entry<String, Object>> entries = formData.entrySet();
        List<String> columnList = new ArrayList<>(entries.size());
        List<Object> valueList = new ArrayList<>(entries.size());
        for (Map.Entry<String, Object> entry: entries) {
            columnList.add(entry.getKey());
            valueList.add(entry.getValue());
        }

        StringBuilder builder = new StringBuilder(" INSERT INTO ");
        builder.append(tableCode);
        builder.append(" ( ");

        columnList.forEach(column -> {
            builder.append(column);
            builder.append(", ");
        });

        builder.append(" create_user_id, create_user_name, create_time ");

        builder.append(" ) VALUES ( ");

        valueList.forEach(value -> {
            if (value instanceof String) {
                builder.append("'");
                builder.append(value);
                builder.append("'");
            } else {
                builder.append(value);
            }
            builder.append(",");
        });

        CurrentUser currentUser = wfForm.getCurrentUser();
        String userName = currentUser.getUserName();
        String nickName = currentUser.getNickName();

        builder.append("'");
        builder.append(userName);
        builder.append("'");
        builder.append(", '");
        builder.append(nickName);
        builder.append("'");
        builder.append(", '");
        builder.append(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        builder.append("'");

        builder.append(" ) ");

        System.out.println("insert sql: " + builder.toString());

        cubeFlowFormMapper.rawUpdateSql(builder.toString());

        String selectKey = " SELECT LAST_INSERT_ID() ID LIMIT 1 ";
        Map<String, Object> resultMap = cubeFlowFormMapper.rawSelectSql(selectKey);
        Long id = Long.valueOf(resultMap.get("ID").toString());

        // 记录流程实例业务关系(instance_id 字段为空，因为尚未发起流程)
        CubeFlowableInstanceBusiness cfib = new CubeFlowableInstanceBusiness();
        cfib.setBusinessKey(id + "");
        cfib.setTableCode(tableCode);
        cfib.setStatus(FlowableConstant.STATUS_TO_APPLY);
        cfib.setResult(FlowableConstant.RESULT_TO_SUBMIT);
        cfib.setCreateUserId(currentUser.getUserName());
        cfib.setCreateTime(new Date());
        instanceBusinessMapper.insertInstanceBusiness(cfib);

        return id;
    }

    private void handleUpdateFormData(CubeFlowForm wfForm) {
        // 物理表名
        String tableCode = wfForm.getTableCode();
        if (StringUtils.isEmpty(tableCode)) {
            throw new RuntimeException("物理表名不能为空");
        }
        Map<String, Object> formData = (Map<String, Object>) JSONObject.parse(wfForm.getFormData());

        // 业务表id
        Long businessKey = wfForm.getBusinessKey();

        // 所有要更新的字段和值
        Set<Map.Entry<String, Object>> entries = formData.entrySet();
        List<String> columnList = new ArrayList<>(entries.size());
        List<Object> valueList = new ArrayList<>(entries.size());
        for (Map.Entry<String, Object> entry: entries) {
            columnList.add(entry.getKey());
            valueList.add(entry.getValue());
        }

        StringBuilder builder = new StringBuilder(" UPDATE ");
        builder.append(tableCode);
        builder.append(" SET ");

        for (int i = 0; i < columnList.size(); i++) {
            String column = columnList.get(i);
            Object value = valueList.get(i);
            builder.append(column);
            builder.append(" = ");
            if (value instanceof String) {
                builder.append("'");
                builder.append(value);
                builder.append("'");
            } else {
                builder.append(value);
            }
            builder.append(",");
        }

        CurrentUser currentUser = wfForm.getCurrentUser();
        String userName = currentUser.getUserName();
        String nickName = currentUser.getNickName();

        builder.append(" update_user_id = '");
        builder.append(userName);
        builder.append("'");
        builder.append(", update_user_name = '");
        builder.append(nickName);
        builder.append("'");
        builder.append(", update_time = '");
        builder.append(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        builder.append("' where 1=1 and id = ");
        builder.append(businessKey);

        System.out.println("update sql: " + builder.toString());

        cubeFlowFormMapper.rawUpdateSql(builder.toString());
    }





}
