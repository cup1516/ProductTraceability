<template>
  <el-dialog :visible.sync="isVisible" :title="storeDialog.title"
    :close-on-click-modal='false' :close-on-press-escape='false'
    :modal-append-to-body="false" @close="closeDialog">
    <div class="form">
      <el-form ref="form" :model="form" :rules="form_rules" :label-width="dialog.formLabelWidth" style="margin:10px;width:auto;">
        <el-form-item prop='name' label="仓库名称:">
          <el-col :span="6">
            <el-input type="text" v-model="form.name"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop='alias' label="仓库简称:">
          <el-col :span="6">
            <el-input type="text" v-model="form.alias"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop='name' label="仓库地址:">
          <el-col :span="8">
            <el-input type="text" v-model="form.address"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop='remark' label="仓库描述:">
          <el-col :span="8">
            <el-input type="text" v-model="form.remark"></el-input>
          </el-col>
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
// 导入vue 获取属性方法
import { mapGetters } from 'vuex'
import storage, {USER} from '../../utils/storage'
import { insertStore, updateStoreByPrimaryKey } from '../../api/store'
export default {
  name: 'StoreFunctionDialog',
  data () {
    return {
      isVisible: this.isShow,
      storeDialog: {
        title: '',
        type: ''
      },
      form: {
        name: '',
        alias: '',
        address: '',
        remark: '',
        createId: '',
        creator: '',
        createTime: '',
        updateId: '',
        updator: '',
        updateTime: '',
        id: ''
      },
      form_rules: {
        name: [
          {required: true, message: '仓库名称不能为空！', trigger: ['blur', 'change']}
        ],
        alias: [
          {required: true, message: '仓库简称不能为空！', trigger: ['blur', 'change']}
        ],
        address: [
          {required: false, message: '仓库地址不能为空！', trigger: ['blur', 'change']}

        ],
        remark: [
          {required: false, trigger: ['blur', 'change']}
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
    closeDialog: function () {
      this.$emit('closeDialog')
    },
    // 提交 表单
    onSubmit: function (form) {
      this.$refs[form].validate((valid) => {
        if (valid) { // 表单数据验证完成之后，提交数据;
          // 从 sessionStorage 中获取当前用户对象
          let user = JSON.parse(storage.get(USER))
          this.form.createId = user.userId
          this.form.creator = user.username
          let data = {
            'name': this[form].name,
            'alias': this.form.alias,
            'address': this[form].address,
            'remark': this[form].remark,
            'createId': this[form].createId,
            'creator': this[form].creator,
            'updateId': this[form].createId,
            'updator': this[form].creator,
            'principalId': this[form].createId,
            'principalName': this[form].creator,
            'companyId': "1"
          }
          if (this.storeDialog.type === 'add') {
            // 新增
            insertStore(data).then((res) => {
              this.$message({
                message: '新增成功',
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
          } else if (this.storeDialog.type === 'edit') {
            data.id = this.form.id
            // 更新
            updateStoreByPrimaryKey(data).then((res) => {
              this.$message({
                message: '修改成功',
                type: 'success',
                duration: 5 * 1000
              })
              // 关闭弹框
              this.closeDialog()
              // 刷新页面,执行父组件的refresh，也是就getEmployeeList
              this.$emit('refresh')
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
      this.storeDialog.type = functionDialog.type
      this.storeDialog.title = functionDialog.title
      if (functionDialog.type === 'edit') {
        this.form = this.dialogRow
      }
    }
  },
  computed: {
    ...mapGetters([
      'functionDialog'
    ])
  }
}
</script>

<style scoped>
</style>
