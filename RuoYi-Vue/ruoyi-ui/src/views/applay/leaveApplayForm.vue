<template>
  <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
    <el-form-item label="请假人" prop="username">
      <el-input v-model="formData.username" placeholder="请输入请假人" clearable :style="{width: '100%'}"></el-input>
    </el-form-item>
    <el-form-item label="请假原因" prop="reason">
      <el-input v-model="formData.reason" placeholder="请输入请假原因" clearable :style="{width: '100%'}"></el-input>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "leaveApplayForm",
  components: {},
  props: [],
  data() {
    return {
      formData: {
        username: undefined,
        reason: undefined
      },
      rules: {
        username: [{
          required: true,
          message: '请输入请假人',
          trigger: 'blur'
        }],
        reason: [{
          required: true,
          message: '请输入请假原因',
          trigger: 'blur'
        }]
      },
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}
</script>

<style>

</style>