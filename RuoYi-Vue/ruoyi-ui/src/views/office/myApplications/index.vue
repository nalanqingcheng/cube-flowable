<template>
    <CubeMyApplications
      @getApplicationsListCall="onGetList"
      @submitApplicationCall="onSubmitApplication"
      @getApprovedByListCall="onGetApprovedByList"
      @modifyApplicationCall="onModifyApplication"
      @deleteApplicationCall="onDeleteApplication"
      @revokeApplicationCall="onRevokeApplication"
      @getProcessDiagramCall="onGetProcessDiagram"
      @getFormDataCall="onGetFormData"
      @getHistoryCall="onGetHistory"
    ></CubeMyApplications>
</template>

<script>

import request from '@/utils/request'

export default {
  data() {
    return {

    }
  },

  methods:{
    /**获取我的申请列表 */
    onGetList: (queryParams, callback) => {
      // 根据参数ajax请求
      console.log("onGetList",queryParams)
      request({
        url: '/flowable/process/getList',
        method: 'get',
        params: queryParams
      }).then(response =>{
        console.log("list",response)
        callback(response)
      })
    },
    /**提交申请 */
    onSubmitApplication: (submitConfirmForm, callback) =>{
      console.log("onSubmitApplication",submitConfirmForm)
      request({
        url: '/flowable/process/submitApply',
        method: 'post',
        data: submitConfirmForm
      }).then(response => {
        callback(response)
      })
    },
    /**获取审批人列表 */
    onGetApprovedByList: (nodeTask, callback) =>{
      console.log("onGetApprovedByList",nodeTask)
      request({
        url: "/flowable/process/getFirstApprovalNode",
        method: "get",
        params: nodeTask,
      }).then(response => {
        console.log("onGetApprovedByList",response)
        callback(response.data)
      })
    },
    /**编辑提交数据 */
    onModifyApplication: (modifyDataForm, callback) =>{
      console.log("onModifyApplication",modifyDataForm)
      //走暂存数据方法
      request({
        url:'/flowable/applay/tempSave',
        method: 'post',
        data:modifyDataForm,
      }).then(response =>{
        console.log("onModifyApplication", response)
        callback(response)
      })
    },
    /**删除申请 */
    onDeleteApplication: (myApplicationId, callback) =>{
      console.log("onDeleteApplication", myApplicationId)
      request({
        url:'/flowable/process/deleteApplication',
        method: 'post',
        data: myApplicationId
      }).then(response =>{
        console.log("onDeleteApplication", response)
        callback(response)
      })
    },
    /**撤回申请 */
    onRevokeApplication: (revokeForm, callback) =>{
      // 根据参数ajax请求
      console.log(revokeForm)
      request({
        url: '/flowable/process/revokeProcess',
        method: 'post',
        data: revokeForm
      }).then(response => {
        console.log(response)
        callback(response)
      })
    },
    /**获取表单数据 */
    onGetFormData: (formDataDto, callback) => {
      // console.log("onGetFormData",formDataDto)
      request({
        url: '/flowable/applay/getBusinessFormData',
        method: 'get',
        params: formDataDto
      }).then(response =>{
        console.log("getBusinessFormData",response)
        callback(response.data)
      })
    },
    /**获取流程实例审批历史记录 */
    onGetHistory: (historicActivity, callback) => {
      console.log("OnHistory",historicActivity)
      request({
        url: '/flowable/process/listHistory',
        method: 'post',
        data: historicActivity,
      }).then(response =>{
        console.log("history",response)
        callback(response)
      })
    },
    /**获取审批历史记录流程图 */
    onGetProcessDiagram: (instanceId, callback) => {
      request({
        url: '/flowable/process/processDiagram',
        method: 'post',
        data: instanceId,
      }).then(response => {
        console.log("流程图", response)
        callback(response)
      })
    }
  }
}
</script>

<style>

</style>
