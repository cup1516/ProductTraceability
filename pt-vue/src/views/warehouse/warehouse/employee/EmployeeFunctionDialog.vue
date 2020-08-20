<template>
  <el-dialog :visible.sync="isVisible" :title="employeeDialog.title"
    :close-on-click-modal='false' :close-on-press-escape='false'
    :modal-append-to-body="false" @close="closeDialog">
    <div class="form">
      <el-form ref="form" :model="form" :rules="form_rules" :label-width="dialog.formLabelWidth" style="margin:10px;width:auto;">
        <el-form-item prop='employeeName' label="职工姓名:">
          <el-col :span="6">
            <el-input type="text" v-model="form.employeeName"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别:" prop="sex">
          <el-radio-group v-model="form.sex" size="medium">
            <el-radio border :label="1">男</el-radio>
            <el-radio border :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop='nation' label="民族:" >
          <el-autocomplete class="inline-input" v-model="form.nation" :fetch-suggestions="querySearch" placeholder="请输入民族" @select="handleSelect"></el-autocomplete>
        </el-form-item>
        <el-form-item prop='birthday' label="出生日期">
          <el-col :span="6">
            <el-date-picker value-format="yyyy-MM-dd" type="date" placeholder="选择日期" v-model="form.birthday" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="是否已婚:" prop='isMerried'>
          <el-radio-group v-model="form.isMerried" size="medium">
            <el-radio border :label="1">已婚</el-radio>
            <el-radio border :label="0">未婚</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop='telephone'  label="电话号码:">
          <el-col :span="6">
            <el-input type="text" v-model="form.telephone"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop='addr'  label="家庭住址:">
          <el-input type="text" v-model="form.addr"></el-input>
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
import { allNation } from '../../utils/env'
import { insertEmployee, updateEmployeeByPrimaryKey } from '../../api/employee'
export default {
  name: 'EmployeeFunctionDialog',
  data () {
    var phoneNumber = (rule, value, callback) => {
      if (value && (!(/^[1][345789]\d{9}$/).test(value) || !(/^[1-9]\d*$/).test(value) || value.length !== 11)) {
        callback(new Error('电话号码不符合规范'))
      } else {
        callback()
      }
    }
    var nationValue = (rule, value, callback) => {
      if (value && this.nations.indexOf(value) === -1) {
        callback(new Error('民族不存在'))
      } else {
        callback()
      }
    }
    return {
      isVisible: this.isShow,
      employeeDialog: {
        title: '',
        type: ''
      },
      form: {
        employeeName: '',
        sex: '',
        nation: '',
        telephone: '',
        isMerried: '',
        addr: '',
        birthday: '',
        id: '' // 修改所需要的字段
      },
      form_rules: {
        employeeName: [
          {required: true, message: '职工姓名不能为空！', trigger: ['blur', 'change']}
        ],
        sex: [
          {required: true, message: '性别不能为空！', trigger: ['blur', 'change']}
        ],
        nation: [
          {required: true, message: '民族不能为空！', trigger: ['blur', 'change']},
          {validator: nationValue, trigger: 'blur'}
        ],
        birthday: [
          {required: true, message: '出生日期不能为空！', trigger: ['blur', 'change']}
        ],
        telephone: [
          {required: true, message: '电话号码不能为空！', trigger: ['blur', 'change']},
          {validator: phoneNumber, trigger: 'blur'}
        ],
        isMerried: [
          {required: true, message: '是否已婚不能为空！', trigger: ['blur', 'change']}
        ],
        addr: [
          {required: true, message: '家庭住址不能为空！', trigger: ['blur', 'change']}
        ]
      },
      // 详情弹框信息
      dialog: {
        width: '400px',
        formLabelWidth: '120px'
      },
      nations: []
    }
  },
  props: {
    isShow: Boolean,
    dialogRow: Object
  },
  methods: {
    handleSelect: function (item) {
      this.form.nation = item.value
    },
    querySearch: function (queryString, cb) {
      let nations = this.nations
      let results = queryString ? nations.filter(this.createFilter(queryString)) : nations
      let arr = []
      for (let i = 0; i < results.length; i++) {
        let obj = {'value': results[i]}
        arr.push(obj)
      }
      // 调用 callback 返回建议列表的数据
      cb(arr)
    },
    createFilter: function (queryString) {
      return (nation) => {
        return (nation.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    closeDialog: function () {
      this.$emit('closeDialog')
    },
    // 提交 表单
    onSubmit: function (form) {
      this.$refs[form].validate((valid) => {
        if (valid) { // 表单数据验证完成之后，提交数据;
          // 从 sessionStorage 中获取当前用户对象
          let user = JSON.parse(storage.get(USER))
          let data = {
            'employeeName': this[form].employeeName,
            'password': '',
            'telephone': this[form].telephone,
            'addr': this[form].addr,
            'sex': this[form].sex,
            'birthday': this.form.birthday,
            'isMerried': this[form].isMerried,
            'department': '',
            'nation': this[form].nation,
            'leaderId': user.userId,
            'companyId':'1'
          }
          if (this.employeeDialog.type === 'add') {
            // 新增
            insertEmployee(data).then((res) => {
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
          } else if (this.employeeDialog.type === 'edit') {
            data.id = this.form.id
            // 更新
            updateEmployeeByPrimaryKey(data).then((res) => {
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
    // 挂载所有的民族信息
    this.nations = allNation
    // 从 vuex 中 获取 functionDialog 基本信息
    let functionDialog = this.$store.getters.functionDialog
    if (functionDialog != null) {
      this.employeeDialog.type = functionDialog.type
      this.employeeDialog.title = functionDialog.title
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
