<template>
  <div id="app">
    <img src="./assets/logo.png" />
    <p>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="登录账号">
          <el-input v-model="formInline.acc" placeholder="登录账号"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input
            type="password"
            v-model="formInline.pswd"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="设备码">
          <el-input
            v-model="formInline.device_code"
            placeholder="设备码"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </p>
    <center>
      <el-alert
        v-if="show"
        :title="title"
        show-icon
        style="width: 50%"
        :type="status"
        :description="description"
        :closable="false"
      >
      </el-alert>
    </center>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formInline: {
        acc: "",
        pswd: "",
        device_code: "",
      },
      description: "",
      status: "success",
      title: "",
      show: false,
      code: "",
      google_key: "",
    };
  },
  methods: {
    onSubmit() {
      this.$axios
        .get(
          "/api/getAuth?acc=" +
            this.formInline.acc +
            "&pswd=" +
            this.formInline.pswd +
            "&device_code=" +
            this.formInline.device_code
        )
        .then((res) => {
          this.show = true;
          if (res.data.code === 200) {
            this.status = "success";
            this.title = "获取成功";

            this.description =
              "当前登录验证码： " +
              res.data.data.code +
              "  谷歌验证器秘钥： " +
              res.data.data.google_key;
          } else {
            this.status = "error";
            this.title = "获取失败，如显示内部错误请检查设备码是否正确";

            this.description = res.data.msg;
          }
        });
    },
  },
};
</script>

<style>
#app {
  font-family: Helvetica, sans-serif;
  text-align: center;
}
</style>
