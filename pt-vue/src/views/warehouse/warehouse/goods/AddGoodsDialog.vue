<template>
  <el-dialog :visible.sync="isVisible" :title="addGoodsDialog.title"
    :close-on-click-modal='false' :close-on-press-escape='false'
    :modal-append-to-body="false" @close="closeDialog">
    <div class="form">
      <el-form ref="form" :model="form" :rules="form_rules" :label-width="dialog.formLabelWidth" style="margin:10px;width:auto;">
        <el-form-item prop='goodName' label="商品名称:" >
          <el-col :span="6">
            <el-input type="text" v-model="form.goodName"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop='goodType' label="商品类型:">
          <el-col :span="6">
            <el-input type="text" v-model="form.goodType"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop='goodNo' label="商品编码:">
          <el-col :span="6">
            <el-input type="text" v-model="form.goodNo"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop='unit' label="单位:">
          <el-col :span="6">
            <el-input type="text" v-model="form.unit"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label-width="1px">
          <el-row>
            <el-col :span="6">
              <el-form-item prop='length' label="长:">
                <el-input type="text" v-model="form.length"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop='width' label="宽:">
                <el-input type="text" v-model="form.width"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop='heigth' label="高:">
                <el-input type="text" v-model="form.heigth"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop='weigth' label="重量:">
                <el-input type="text" v-model="form.weigth"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item  class="text_right">
          <el-button @click="isVisible = false">取 消</el-button>
          <el-button type="primary" @click='onSubmit("form")'>提  交</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import { addGoods, updateGoodsByPrimaryKey } from '../../api/goods/index.js'
import storage, { USER } from '../../utils/storage'
export default {
  name: 'AddGoodsDialog',
  data () {
    return {
      isVisible: this.isShow,
      addGoodsDialog: {
        title: '',
        type: ''
      },
      form: {
        goodName: '',
        goodType: '',
        goodNo: '',
        unit: '',
        length: '',
        width: '',
        heigth: '',
        weigth: '',
        createUserName: '',
        createUserId: '',
        modifyUserName: '',
        modifyUserId: ''
      },
      form_rules: {
        goodName: [
          {required: true, message: '商品名不能为空！', trigger: 'blur'}
        ]
      },
      // 详情弹框信息
      dialog: {
        width: '400px',
        formLabelWidth: '120px'
      }
    }
  },
  props: {
    isShow: Boolean,
    dialogRow: Object
  },
  methods: {
    handleSelect: function (item) {
    },
    closeDialog: function () {
      this.$emit('closeDialog')
    },
    onSubmit: function (form) {
      this.$refs[form].validate((valid) => {
        if (valid) { // 表单数据验证完成之后，提交数据;
          //    从 sessionStorage 中获取当前用户对象
          let user = JSON.parse(storage.get(USER))
          this.form.createUserName = user.username
          this.form.createUserId = user.userId
          let data = {
            'goodName': this.form.goodName,
            'goodNo': this.form.goodNo,
            'goodType': this.form.goodType,
            'unit': this.form.unit,
            'width': this.form.width,
            'length': this.form.length,
            'heigth': this.form.heigth,
            'weigth': this.form.weigth,
            'createUserName': this.form.createUserName,
            'createUserId': this.form.createUserId,
            'modifiedUserName': this.form.modifyUserName,
            'modifiedUserId': this.form.modifyUserId,
            'companyId':'1'
          }
          if (this.addGoodsDialog.type === 'add') {
            // 新增
            addGoods(data).then((res) => {
              this.$message({
                message: '新增成功',
                type: 'success',
                duration: 5 * 1000
              })
              // 关闭弹框
              this.closeDialog()
              // 刷新页面,执行父组件的refresh，也是就getGoodsList
              this.$emit('refresh')
              // 组件页面刷新，这里只是测试，需要注入 inject 属性
              // this.reload()
            }).catch((res) => {
            })
          } else if (this.addGoodsDialog.type === 'edit') {
            data.id = this.form.id
            // 更新
            this.form.modifyUserName = user.username
            this.form.modifyUserId = user.userId

            updateGoodsByPrimaryKey(data).then((res) => {
              this.$message({
                message: '修改成功',
                type: 'success',
                duration: 5 * 1000
              })
              // 关闭弹框
              this.closeDialog()
              // 刷新页面,执行父组件的refresh，也是就getEmployeeList
              this.$emit('refresh')
              // 组件页面刷新，这里只是测试，需要注入 inject 属性
              // this.reload()
            }).catch((res) => {
            })
          }
        }
      })
    }
  },
  mounted () {
    // 从 vuex 中 获取 functionDialog 基本信息
    let functionDialog = this.$store.getters.functionDialog
    if (functionDialog != null) {
      this.addGoodsDialog.type = functionDialog.type
      this.addGoodsDialog.title = functionDialog.title
      if (functionDialog.type === 'edit') {
        this.form = this.dialogRow
      }
    }
  }

}

</script>

<style scoped>
</style>
