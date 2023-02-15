<template>
  <div>
    <CubeDoneList
      @getDoneListCall="onGetDoneList"
      @getFormDataCall="onGetFormData"
      @deleteCall="onDelete"
      @getProcessDiagramCall="onGetProcessDiagram"
      @getApprovalHistoryCall="onGetApprovalHistory"
    ></CubeDoneList>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  methods: {
    onGetDoneList: (params,callback) => {
      request({
        url: "/flowable/process/doneList"+ "?pageNum=" + params.pageNum + '&pageSize=' + params.pageSize,
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onGetFormData: (params, callback) => {
      request({
        url: '/flowable/applay/getBusinessFormData',
        method: 'get',
        params: params
      }).then(response =>{
        callback(response)
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
    onDelete:(params,callback) => {
      console.log("delete")
      callback("")
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
  }
};
</script>

<style></style>
