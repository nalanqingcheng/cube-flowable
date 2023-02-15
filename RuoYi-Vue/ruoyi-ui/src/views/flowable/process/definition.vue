<template>
  <div>
    <CubeProcessDefinition
      @getDefinitionListCall="onGetDefinitionList"
      @delDefinitionCall="onDelDefinition"
      @suspendOrActiveDefinitionCall="onSuspendOrActiveDefinition"
      @getProcessImgCall="onGetProcessImg"
      @getNodeListCall="onGetNodeList"
      @getNodeConfigCall="onGetNodeConfig"
      @saveNodeConfigCall="onSaveCubeNodeConfig"
      @getDeptTreeCall="onGetDeptTree"
      @getUserListCall="onGetUserList"
      @getRoleListCall="onGetRoleList"
    >
    </CubeProcessDefinition>
  </div>
</template>
<script>
import request from "@/utils/request";

export default {
  data() {
    return {};
  },
  methods: {
    onGetDefinitionList: (params, callback) => {
      request({
        url: "/process/definition/list",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onDelDefinition: (params, callback) => {
      request({
        url: "/process/definition/delete",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onSuspendOrActiveDefinition: (params, callback) => {
      request({
        url: "/process/definition/suspendOrActiveDefinition",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onGetProcessImg: (params, callback) => {
      request({
        url: "/process/definition/getXml",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onGetNodeList: (params, callback) => {
      request({
        url: "/process/definition/list/node",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onGetNodeConfig: (params, callback) => {
      request({
        url: "/process/definition/node/config",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onSaveCubeNodeConfig: (params, callback) => {
      request({
        url: "/process/definition/node/config/save",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onGetDeptTree: (callback) => {
      request({
        url: "/deptTree",
        method: "post",
      }).then((response) => {
        callback(response);
      });
    },
    onGetUserList: (params, callback) => {
      request({
        url: "/list/user",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
    onGetRoleList: (params, callback) => {
      request({
        url: "/list/role",
        method: "post",
        data: params,
      }).then((response) => {
        callback(response);
      });
    },
  },
};
</script>
<style scoped></style>
