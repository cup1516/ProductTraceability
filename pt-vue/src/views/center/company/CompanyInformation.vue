<template>
  <div>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form  label-width="130px" :model="formLabelAlign">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="formLabelAlign.username" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="企业名称" prop="name">
              <el-input v-model="formLabelAlign.name"></el-input>
            </el-form-item>
            <el-form-item label="法人代表" prop="person">
              <el-input v-model="formLabelAlign.person"></el-input>
            </el-form-item>
            <el-form-item label="统一社会信用码" prop="code">
              <el-input v-model="formLabelAlign.code"></el-input>
            </el-form-item>
            <el-form-item label="工商注册号" prop="number">
              <el-input v-model="formLabelAlign.number"></el-input>
            </el-form-item>
            <el-form-item label="企业地址" prop="address">
              <el-input v-model="formLabelAlign.address"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
          <el-form  label-width="130px" :model="formStatus">
            <el-form-item label="审核信息" prop="validity">
              <el-input type="textarea" :rows="4" maxlength="200" v-model="formStatus.validity" readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="网址" prop="website">
              <el-input v-model="formStatus.website" readonly="true"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-button type="primary" style="float: left" v-on:click="updateCompany">修改</el-button>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: 'CompanyInformation',
    data () {
      return {
        formLabelAlign: {
          username: 'testfr',
          name: '水果企业A',
          person: '法人A',
          code: '1120',
          code: '1120',
          number: '1230',
          address: '北京'
        },
        formStatus: {
          validity: '未通过审核',
          website: '暂无'
        }
      }
    },
    created () {
      const _this = this
      var ff = this.$store.state.user.name;
      ff = ff.split('_')[0];//获取到的用户名最后一位多了下划线，必须删除
      // console.log(ff)
      this.$axios.get('/center/company/findCompanyByUsername/' + ff).then(function (resp2) {
        //console.log(resp2)
        _this.formLabelAlign.username = ff
        _this.formLabelAlign.name = resp2.name
        _this.formLabelAlign.person = resp2.person
        _this.formLabelAlign.code = resp2.code
        _this.formLabelAlign.number = resp2.number
        _this.formLabelAlign.address = resp2.address
      })
      this.$axios.get('/center/companyAccount/findCompanyAccount/' + ff).then(function (resp3) {
        //console.log(resp3)
        var username = resp3.username.toString()
        var password = resp3.password.toString()
        var va = '未通过审核'
        // eslint-disable-next-line eqeqeq
        if (resp3.validity.toString() == '1') {
          va = '通过审核 \n' + '帐号： admin' + '\n' + '密码： 123456';
          _this.formStatus.validity = va
          _this.formStatus.website = resp3.website
        } else {
          _this.formStatus.validity = va
          _this.formStatus.website = '无'
        }
      })
    },
    methods: {
      updateCompany () {
        var _this = this
        this.$axios.post('center/company/update', this.formLabelAlign).then(function (resp) {
          //console.log(resp)
          if (resp == "success") {//eslint-disable-line
            // console.log(resp.data)
            _this.$alert('修改成功', '提示', {
              confirmButtonText: '确定'
            })
            // _this.$router.replace('/login')
          } else {
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        });
        /*this.$axios.post('http://localhost:9018/company_id/insert/' + '1234').then(function (resp2) {
          console.log(resp2)
          if (resp2 == "success!") {//eslint-disable-line
            // console.log(resp.data)
            _this.$alert('修改成功！', '提示', {
              confirmButtonText: '确定'
            })
            // _this.$router.replace('/login')
          } else {
            this.$alert(resp2.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        });*/
      }
    }
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px
    /*    &:last-child {
         margin-bottom: 0;
       }*/
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
