<template>
  <cube-to-do-list
    @getToDoListCall="onGetTodoList"
    @getProcessDiagramCall="onGetProcessDiagram"
    @getApprovalHistoryCall="onGetApprovalHistory"
    @getTreeCall="onGetTreeList"
    @getUserCall="onGetUserList"
    @getFormDataCall="onGetForm"
    @rejectCall="onRejectForm"
    @passCall="onCompleteForm"
    @entrustCall="onEntrustOthersForm"
    @getNextApprovalNodeCall="onGetNextApprovalNode"
  ></cube-to-do-list>
</template>

<script>
import request from '@/utils/request'

export default {
  data() {
    return {};
  },
  methods: {
    //获取待办列表
    onGetTodoList: (queryParams, callback) => {
      //根据参数ajax请求

      request({
        url: '/flowable/process/todoList',
        method: 'get',
        params: queryParams
      }).then(response => {
        callback(response)
      })

    },
    //获取表单数据
    onGetForm: (queryParams, callback) => {
      request({
        url: '/flowable/process/formData',
        method: 'get',
        params: queryParams
      }).then(response => {
        callback(response.data)
      })
    },
    
    //获取部门树
    onGetTreeList: (callback) => {

      request({
        url: '/deptTree',
        method: 'post',
      }).then(response => {
        callback(response.data)
      })
    },

    //获取用户
    onGetUserList: (queryParams, callback) => {
      // 根据参数ajax请求

      request({
      url: '/list/user',
      method: 'post',
      data: queryParams
      }).then(response => {
        callback(response)
      })
    },
    onGetProcessImg: (params, callback) => {
      callback(xmlStr);
    },
    //办理
    onCompleteForm: (formObject, callback) => {
      // 根据参数ajax请求

      request({
        url: '/flowable/process/complete',
        method: 'post',
        data: formObject
      }).then(response => {
        callback(response.data)
      })

    },
    //驳回
    onRejectForm: (backNodeDto, callback) => {
      // 根据参数ajax请求

      request({
        url: '/flowable/process/doBackNode',
        method: 'post',
        data: backNodeDto
      }).then(response => {
        callback(response.data)
      })

    },

    onEntrustOthersForm: (delegateDto, callback) => {
      // 根据参数ajax请求
      request({
        url: '/flowable/process/delegate',
        method: 'post',
        data: delegateDto
      }).then(response => {
        callback(response.data)
      })
    },
    onGetNextApprovalNode: (instanceId, approvedTask, callback) => {
      request({
        url: '/flowable/process/getNextApprovalNode',
        method: 'get',
        params: {'instanceId': instanceId, 'approvedTask': approvedTask}
      }).then(response => {
        callback(response.data)
      })
    },

    onGetProcessDiagram : (params,callback) => {
      request({
        url: "/flowable/process/processDiagram",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },

    onGetApprovalHistory:(params,callback) => {
      request({
        url: "/flowable/process/listHistory",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    }
  },

};
</script>

