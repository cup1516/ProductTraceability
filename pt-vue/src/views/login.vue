<template>
  <div class="login">
    <el-dialog title="企业注册" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="addForm">
        <el-form-item prop="username" label="帐号" :label-width="formLabelWidth">
          <el-input v-model="addForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" :label-width="formLabelWidth">
          <el-input v-model="addForm.password" autocomplete="off" :type="passw">
            <i slot="suffix" :class="icon" @click="showPass"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="name" label="企业名称" :label-width="formLabelWidth">
          <el-input v-model="addForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类别" :label-width="formLabelWidth">
          <el-select v-model="addForm.type" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="url" label="网址" :label-width="formLabelWidth">
          <el-input v-model="addForm.url" placeholder="输入格式：/url 如/baidu" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="person" label="法人代表" :label-width="formLabelWidth">
          <el-input v-model="addForm.person" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="code" label="统一社会信用码" :label-width="formLabelWidth">
          <el-input v-model="addForm.code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="number" label="工商注册号" :label-width="formLabelWidth">
          <el-input v-model="addForm.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="address" label="企业地址" :label-width="formLabelWidth">
          <el-input v-model="addForm.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitCompanyUserInfo">确 定</el-button>
      </div>
    </el-dialog>

    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">后台管理系统</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <!-- <el-form-item prop="code">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" />
        </div>
      </el-form-item> -->
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="isShow1"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div><el-button v-if= "isNeedAudit" size="medium" type="primary" @click="dialogFormVisible = true" style="width:100%;" >注册</el-button></div>
      </el-form-item>
    </el-form>
    <Vcode
      :show="isShow"
      @success="handleLogin"
      @close="onClose"
    />

    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2019 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
  import Vcode from "vue-puzzle-vcode";
  import { getCodeImg } from "@/api/login";
  import Cookies from "js-cookie";
  import { encrypt, decrypt } from '@/utils/jsencrypt'
  import { listUser, getUser, delUser, addUser, updateUser, addCompanyUser, registerUser, addCompany} from "@/api/system/user";
  import store from '../store/modules/portal'
  export default {
    name: "Login",
    components: {
      Vcode
    },
    data() {
      return {
        isNeedAudit: false,
        dialogFormVisible: false,
        codeUrl: "",
        cookiePassword: "",
        passw: "password",
        icon: "el-input__icon el-icon-view",
        formLabelWidth: '120px',
        options: [{
          value: '1',
          label: '庄园'
        }, {
          value: '2',
          label: '经销商'
        }, {
          value: '3',
          label: '仓储'
        }],
        addForm: {
          username: '',
          password: '',
          name: '',
          type: '',
          url: '',
          person: '',
          code: '',
          number: '',
          address: ''
        },
        loginForm: {
          // username: "admin",
          // password: "123456",
          // rememberMe: false,
          // code: "",
          // client: "Basic ZGVhbGVyOnNlY3JldA=="//dealer:secret
        },
        loginRules: {
          username: [
            { required: true, trigger: "blur", message: "用户名不能为空" }
          ],
          password: [
            { required: true, trigger: "blur", message: "密码不能为空" }
          ],
          // code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
        },
        loading: false,
        isShow:false,
        redirect: undefined
      };
    },
    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect;
        },
        immediate: true
      }
    },
    created() {
      this.queryParam()
    },
    methods: {
      async queryParam(){//根据url来显示按钮
        const url = store.state.url
        if (url == 'center') {
          this.isNeedAudit = true
        } else {
          this.isNeedAudit = false
        }
      },
      isShow1() {
        this.isShow = true;
      },
      onClose() {
        this.isShow = false;
      },
      showPass(){
        //点击图标是密码隐藏或显示
        if( this.passw=="text"){
          this.passw="password"
          //更换图标
          this.icon="el-input__icon el-icon-view"
        }else {
          this.passw="text"
          this.icon="el-input__icon el-icon-loading"
        }
      },
      submitCompanyUserInfo () {
        var _this = this
        this.$axios.post('/center/company/save', this.addForm).then(function (resp) {
          //console.log(resp)
          if (resp == "success") {//eslint-disable-line
            _this.$alert('新增企业信息成功', '提示', {
              confirmButtonText: '确定'
            })
            // _this.$router.replace('/login')
          } else {
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        });
        this.$axios.post('/company_id/company_id/save',
          {
            companyId: '',
            companyName: this.addForm.name,
            type: this.addForm.type,
            url: this.addForm.url
          }).then(function (resp2) {
          //console.log(resp2)
          if (resp2 == "success") {//eslint-disable-line
            _this.$alert('新增company_id成功', '提示', {
              confirmButtonText: '确定'
            })
            //_this.$router.replace('/login')
          } else {
            this.$alert(resp2, '提示', {
              confirmButtonText: '确定'
            })
          }
        });
        registerUser(
          {
            userName: this.addForm.username,
            password: this.addForm.password,
            postIds: ['1'],
            roleIds: ['1'],
            companyId: "#"
          },
          "%23"
        ).then(() => {
          this.msgSuccess("新增用户成功");
          this.open = false;
        }).catch(response=>{
          this.msgError(response);
        });
      },

      getCookie() {
        const username = Cookies.get("username");
        const password = Cookies.get("password");
        const rememberMe = Cookies.get('rememberMe')
        this.loginForm = {
          username: username === undefined ? this.loginForm.username : username,
          password: password === undefined ? this.loginForm.password : decrypt(password),
          rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
        };
      },
      handleLogin() {
        this.isShow = false;// 通过验证后，需要手动隐藏模态框
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true;
            this.$store
              .dispatch("Login", this.loginForm)
              .then(() => {
                this.$router.push({ path: this.redirect || "/" });
              })
              .catch(res => {
                this.loading = false;
              });
          }
        });
      }
    }
  };
</script>

<style rel="stylesheet/scss" lang="scss">
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-image: url("../assets/image/login-background.jpg");
    background-size: cover;
  }
  .title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #707070;
  }

  .login-form {
    border-radius: 6px;
    background: #ffffff;
    width: 400px;
    padding: 25px 25px 5px 25px;
    .el-input {
      height: 38px;
      input {
        height: 38px;
      }
    }
    .input-icon {
      height: 39px;
      width: 14px;
      margin-left: 2px;
    }
  }
  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 33%;
    height: 38px;
    float: right;
    img {
      cursor: pointer;
      vertical-align: middle;
    }
  }
  .el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }
</style>
