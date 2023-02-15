<template>
  <CubeInitiateApplication
    @listDefinitionCall="onListDefinition"
    @queryFormByDeploymentIdCall="onQueryFormDesignByDeploymentId"
    @tempSaveCall="onTempSave"
    @submitApplyCall="onSubmitApply"
    @getFirstApprovalNodeCall="onGetFirstApprovalNode">
  </CubeInitiateApplication>
</template>

<script>

import request from '@/utils/request'

export default {
  data() {
    return {

    }
  },
  methods: {
    onListDefinition: (queryParams, callback) => {
      // 根据参数ajax请求
      console.log(queryParams)

      request({
        url: '/flowable/applay/list',
        method: 'get',
        params: queryParams
      }).then(response => {
        console.log(response)
        callback(response)
      })

    },
    onQueryFormDesignByDeploymentId: (deploymentId, callback) => {
      // 根据参数ajax请求
      console.log(deploymentId)

      // let res = {"msg":"操作成功","code":200,"data":{"searchValue":null,"createBy":"","createTime":null,"updateBy":"","updateTime":null,"remark":"备注ddd","params":{},"currentUser":null,"id":66,"name":"测试","tableCode":"test_table","categoryId":16,"delFlag":"0","categoryName":"人事管理","formData":null,"formType":"1","componentPath":"leaveApplayForm","deploymentId":null,"businessKey":null,"filter":null,"ancestorsQuery":null}}

      request({
        url: '/flowable/applay/queryFormByDeploymentId',
        method: 'get',
        params: {'deploymentId': deploymentId}
      }).then(response => {
        console.log(response)
        callback(response.data)
      })

    },
    // 暂存
    onTempSave: (formObject, callback) => {
      console.log(formObject)

      request({
        url: '/flowable/applay/tempSave',
        method: 'post',
        data: formObject
      }).then(response => {
        console.log(response)
        callback(response.data)
      })
    },
    // 提交申请
    onSubmitApply: (formObject, callback) => {
      console.log(formObject)

      request({
        url: '/flowable/process/submitApply',
        method: 'post',
        data: formObject
      }).then(response => {
        console.log(response)
        callback(response.data)
      })
    },
    // 根据部署模型id获取第一个审批节点信息
    onGetFirstApprovalNode: (nodeTask, callback) => {
      console.log(nodeTask)
      request({
        url: '/flowable/process/getFirstApprovalNode',
        method: 'get',
        params: nodeTask
      }).then(response => {
        console.log(response)
        callback(response.data)
      })
    }
  }
}
</script>

<style>

</style>