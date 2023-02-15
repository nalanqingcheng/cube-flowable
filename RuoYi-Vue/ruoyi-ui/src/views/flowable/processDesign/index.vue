<template>
  <cube-process-design
    @getProcessListCall="onGetList"
    @getProcessCall="onGetProcess"
    @addProcessDesignCall="onAddDesign"
    @editProcessDesignCall="onEditDesign"
    @delProcessDesignCall="onDelDesign"
    @deployProcessDesignCall="onDeployDesign"
    @getProcessFormCall="onGetForm"
    @getProcessDesignCall="onProcessDesign"
    @saveModelerCall="onSaveModeler">
  </cube-process-design>
</template>

<script>

import request from '@/utils/request'

export default {
  data() {
    return {

    }
  },
  methods: {
    // 查询流程模型信息列表
    onGetList: (queryParams, callback) => {
      
      // 根据参数ajax请求
      console.log(queryParams)

      request({
        url: '/flowable/processDesign/list',
        method: 'get',
        params: queryParams
      }).then(response => {
        callback(response)
      })
      
    },
    // 按照id获取流程模型信息
    onGetProcess: (modelId, callback) => {
      // 根据参数ajax请求
      console.log(modelId)

      request({
        url: '/flowable/processDesign/getModeler',
        method: 'get',
        params: {'modelId': modelId}
      }).then(response => {
        callback(response)
      })
    },
    // 新增按钮操作
    onAddDesign: (formObject, callback) => {
      // 根据参数ajax请求
      console.log(formObject)

      request({
        url: '/flowable/processDesign/create',
        method: 'post',
        data: formObject
      }).then(response => {
        callback(response)
      })
    },
    // 保存修改操作
    onEditDesign: (formObject, callback) => {
      // 根据参数ajax请求
      console.log(formObject)

      request({
        url: '/flowable/processDesign/update',
        method: 'post',
        data: formObject
      }).then(response => {
        callback(response)
      })
    },
    // 删除按钮操作
    onDelDesign: (modelId, cascade, callback) => {
      // 根据参数ajax请求
      console.log(modelId, cascade)

      request({
        url: '/flowable/processDesign/remove',
        method: 'get',
        params: {'modelId': modelId, "cascade": cascade}
      }).then(response => {
        callback(response.data)
      })
    },
    // 部署按钮操作
    onDeployDesign: (modelId, callback) => {
      // 根据参数ajax请求
      console.log(modelId)

      request({
        url: '/flowable/processDesign/deploy',
        method: 'post',
        params: {'modelId': modelId}
      }).then(response => {
        callback(response.data)
      })
    },
    // 获取可绑定表单
    onGetForm: (queryFormParams, callback) => {
      // 根据参数ajax请求
      console.log(queryFormParams)

      request({
        url: '/flowable/formDesign/list',
        method: 'get',
        params: queryFormParams
      }).then(response => {
        console.log(response)
        callback(response)
      })
    },
    // 设计按钮操作
    onProcessDesign: (modelId, callback) => {
      console.log(modelId)

      request({
        url: '/flowable/processDesign/queryModelerById',
        method: 'get',
        params: {'modelId': modelId}
      }).then(response => {
        console.log(response)
        callback(response)
      })
    },
    // 保存模型编辑器
    onSaveModeler: (modelRequest, callback) => {
      // 根据参数ajax请求
      console.log(modelRequest);

      request({
        url: '/flowable/processDesign/saveModelEditor',
        method: 'post',
        data: modelRequest
      }).then(response => {
        callback(response)
      })
    },
  }
}
</script>

<style>

</style>