<template>
  <cube-form-design
    :categoryOptions="categoryOpts"
    @getListCall="onGetList"
    @getFormCall="onGetForm"
    @addFormCall="onAddForm"
    @updateFormCall="onUpdateForm"
    @delFormCall="onDelForm"
    @saveDesignCall="onSaveDesign">
  </cube-form-design>
</template>

<script>

import request from '@/utils/request'

export default {
  data() {
    return {
      categoryOpts: [{"id":16,"label":"人事管理"},{"id":17,"label":"OA办公"},{"id":19,"label":"测试0316-001"},{"id":20,"label":"受理流程"}]
    }
  },
  methods: {
    onGetList: (queryParams, callback) => {
      
      // 根据参数ajax请求
      console.log(queryParams)

      request({
        url: '/flowable/formDesign/list',
        method: 'get',
        params: queryParams
      }).then(response => {
        console.log(response)
        callback(response)
      })
      
    },
    onGetForm: (id, callback) => {
      // 根据参数ajax请求
      console.log(id)
      request({
        url: '/flowable/formDesign/getForm',
        method: 'get',
        params: {'id': id}
      }).then(response => {
        callback(response.data)
      })

    },
    onAddForm: (formObject, callback) => {
      // 根据参数ajax请求
      console.log(formObject)
      request({
        url: '/flowable/formDesign/add',
        method: 'post',
        data: formObject
      }).then(response => {
        callback(response)
      })

    },
    onUpdateForm: (formObject, callback) => {
      // 根据参数ajax请求
      console.log(formObject)

      request({
        url: '/flowable/formDesign/edit',
        method: 'post',
        data: formObject
      }).then(response => {
        callback(response)
      })
    },
    onDelForm: (ids, callback) => {
      // 根据参数ajax请求
      console.log(ids)

      request({
        url: '/flowable/formDesign/delete',
        method: 'get',
        params: {'ids': ids}
      }).then(response => {
        callback(response.data)
      })
    },
    onSaveDesign: (formObject, callback) => {
      // 根据参数ajax请求
      console.log(formObject)

      request({
        url: '/flowable/formDesign/saveFormDesign',
        method: 'post',
        data: formObject
      }).then(response => {
        callback(response)
      })
    }
  }
}
</script>

<style>

</style>