<template>
  <el-dialog :visible.sync="isVisible" :title="locatorDialog.title"
             :close-on-click-modal='false' :close-on-press-escape='false'
             :modal-append-to-body="false" @close="closeDialog">
    <div class="form">
      <el-form ref="form" :model="form" :rules="form_rules" :label-width="dialog.formLabelWidth" style="margin:10px;width:auto;">
        <el-row>
          <el-col :span="12">
            <el-form-item prop='shelvesMsg' label="所属货架:">
              <el-col :span="15">
                <el-cascader placeholder="请选择所属货架" v-model="form.shelvesMsg" :options="shelvesOptionList" clearable></el-cascader>
              </el-col>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop='name' label="货位名称:">
              <el-col :span="15">
                <el-input type="text" v-model="form.name"></el-input>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item prop='code' label="货位编码:">
              <el-col :span="15">
                <el-input type="text" v-model="form.code"></el-input>
              </el-col>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop='length' label="货位长度(cm):">
              <el-col :span="15">
                <el-input type="number" v-model="form.length"></el-input>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item prop='width' label="货位宽度(cm):">
              <el-col :span="15">
                <el-input type="number" v-model="form.width"></el-input>
              </el-col>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop='height' label="货位高度(cm):">
              <el-col :span="15">
                <el-input type="number" v-model="form.height"></el-input>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item prop='weight' label="最大载重(kg):">
              <el-col :span="15">
                <el-input type="number" v-model="form.weight"></el-input>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>
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
import { describeAllMineGroupByWarehouse } from '../../api/shelves/index.js'
import { insertLocator, updateLocatorByPrimaryKey } from '../../api/locator/index.js'
export default {
  name: 'LocatorFunctionDialog',
  data () {
    var codeNumber = (rule, value, callback) => {
      if (value && (!(/^[\w]{6,12}$/).test(value) || value.length !== 12)) {
        callback(new Error('货位编码只能由12位数字、字母、下划线组成'))
      } else {
        callback()
      }
    }
    var lengthNumber = (rule, value, callback) => {
      if (!(/^\d+(\.\d+)?$/).test(value) || value <= 0 || value === 0) {
        callback(new Error('货位长度必须为正数'))
      } else if (value > 100000) {
        callback(new Error('货位长度必须小于100000'))
      } else {
        callback()
      }
    }
    var widthNumber = (rule, value, callback) => {
      if (!(/^\d+(\.\d+)?$/).test(value) || value <= 0) {
        callback(new Error('货位宽度必须为正数'))
      } else if (value > 100000) {
        callback(new Error('货位宽度必须小于100000'))
      } else {
        callback()
      }
    }
    var heightNumber = (rule, value, callback) => {
      if (!(/^\d+(\.\d+)?$/).test(value) || value <= 0) {
        callback(new Error('货位高度必须为正数'))
      } else if (value > 100000) {
        callback(new Error('货位高度必须小于100000'))
      } else {
        callback()
      }
    }
    var weightNumber = (rule, value, callback) => {
      if (!(/^\d+(\.\d+)?$/).test(value) || value <= 0) {
        callback(new Error('货位重量必须为正数'))
      } else if (value > 10000000) {
        callback(new Error('货位重量必须小于10000000'))
      } else {
        callback()
      }
    }

    return {
      isVisible: this.isShow,
      locatorDialog: {
        title: '',
        type: ''
      },
      shelvesOptionList: [],
      form: {
        name: '',
        code: '',
        length: 0,
        width: 0,
        height: 0,
        weight: 0,
        shelvesMsg: '',
        id: '' // 修改所需要的字段
      },
      form_rules: {
        name: [
          {required: true, message: '货位名称不能为空！', trigger: ['blur', 'change']}
        ],
        shelvesMsg: [
          {required: true, message: '所属货架不能为空！', trigger: ['blur', 'change']}
        ],
        code: [
          {required: true, message: '货位编码不能为空！', trigger: ['blur', 'change']},
          {validator: codeNumber, trigger: ['blur', 'change']}
        ],
        length: [
          {required: true, message: '货位长度不能为空！', trigger: ['blur', 'change']},
          {validator: lengthNumber, trigger: ['blur', 'change']}
        ],
        width: [
          {required: true, message: '货位宽度不能为空！', trigger: ['blur', 'change']},
          {validator: widthNumber, trigger: ['blur', 'change']}
        ],
        height: [
          {required: true, message: '货位高度不能为空！', trigger: ['blur', 'change']},
          {validator: heightNumber, trigger: ['blur', 'change']}
        ],
        weight: [
          {required: true, message: '最大载重不能为空！', trigger: ['blur', 'change']},
          {validator: weightNumber, trigger: ['blur', 'change']}
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
          let data = {
            'name': this[form].name,
            'code': this[form].code,
            'length': this[form].length,
            'width': this[form].width,
            'height': this[form].height,
            'weight': this[form].weight,
            'type': '[2,7]',
            'warehouseId': this[form].shelvesMsg[0],
            'shelvesId': this[form].shelvesMsg[1],
            'createId': user.userId,
            'creator': user.username,
            'update_id': user.userId,
            'updator': user.username,
            'state': '0',
            'companyId':'1'
          }
          if (this.locatorDialog.type === 'add') {
            // 新增
            insertLocator(data).then((res) => {
              this.$message({
                message: '新增成功',
                type: 'success',
                duration: 5 * 1000
              })
              // 关闭弹框
              this.closeDialog()
              // 刷新页面,执行父组件的refresh，也是就getShelvesList
              this.$emit('refresh')
            }).catch((res) => {
            })
          } else if (this.locatorDialog.type === 'edit') {
            data.id = this.form.id
            // 更新
            updateLocatorByPrimaryKey(data).then((res) => {
              this.$message({
                message: '修改成功',
                type: 'success',
                duration: 5 * 1000
              })
              // 关闭弹框
              this.closeDialog()
              // 刷新页面,执行父组件的refresh，也是就getShelvesList
              this.$emit('refresh')
            }).catch((res) => {
            })
          }
        }
      })
    },
    initShelvesOptionList: function () {
      // 从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      describeAllMineGroupByWarehouse(user.userId).then((res) => {
        this.shelvesOptionList = res
      })
    }
  },
  mounted () {
    this.initShelvesOptionList()
    // 从 vuex 中 获取 functionDialog 基本信息
    let functionDialog = this.$store.getters.locatorFunctionDialog
    if (functionDialog != null) {
      this.locatorDialog.type = functionDialog.type
      this.locatorDialog.title = functionDialog.title
      if (functionDialog.type === 'edit') {
        this.form = this.dialogRow
        this.form.shelvesMsg = [this.dialogRow.warehouseId, this.dialogRow.shelvesId]
      }
    }
  },
  computed: {
    ...mapGetters([
      'locatorFunctionDialog'
    ])
  }
}
</script>

<style scoped>

</style>
