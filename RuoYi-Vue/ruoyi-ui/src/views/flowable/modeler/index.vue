<template>
  <cube-bpmn
    :xml="xml"
    :form-size="formSize"
    :form-property="formProperty"
    :form-key="formKey"
    :show-dialog="true"
    :is-view="false"
    :complete-list="completeList"
    :todo-list="todoList"
    :form-detail="formDetail"
  />
</template>

<script>

import { xmlStr } from './xmlStr'

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {
      xml: xmlStr,
      formSize: 'small',
      formKey: '1',
      formProperty: [
        {
          id: '1',
          name: '请假流程',
          property: []
        }
      ],
      completeList: ['Activity_17aaev3'],
      todoList: ['Activity_1w6a7ng', 'Activity_05frkae'],
      formDetail: [{
        id: 'Activity_1w6a7ng',
        value: [{
          name: '流程节点名称：', value: '经理审批' },
        { name: '审批时间', value: '2022-01-01 00:00:00' },
        { name: '审批人', value: '张三' }]
      }, {
        id: 'Activity_0gzc1m9',
        value: [
          { name: '流程节点名称：', value: '财务审批' },
          { name: '审批时间', value: '2022-01-01 00:00:00' },
          { name: '审批人：', value: '李四' }]
      }]
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
