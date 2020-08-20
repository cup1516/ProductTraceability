<template>
  <el-dialog :visible.sync="isVisible" :title="shelvesDialog.title"
             :close-on-click-modal='false' :close-on-press-escape='false'
             :modal-append-to-body="false" @close="closeDialog">
    <div class="form">
      <el-form ref="form" :model="form" :rules="form_rules" :label-width="dialog.formLabelWidth" style="margin:10px;width:auto;">
        <el-form-item prop='shelvesName' label="货架名称:">
          <el-col :span="6">
            <el-input type="text" v-model="form.shelvesName"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop='warehouseId' label="所属仓库:">
          <el-col :span="6">
            <el-select value-key="id" v-model="form.warehouseId" clearable placeholder="请选择所属仓库" @change="changeWarehouse">
              <el-option v-for="item in warehouseOptionList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
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
import { insertShelves, updateShelvesByPrimaryKey } from '../../api/shelves/index.js'
import { getAllStoreByPrincipalId } from '../../api/warehouse/index.js'
export default {
  name: 'ShelvesFunctionDialog',
  data () {
    return {
      isVisible: this.isShow,
      shelvesDialog: {
        title: '',
        type: ''
      },
      warehouseOptionList: [],
      form: {
        shelvesName: '',
        warehouseId: '',
        warehouseName: '',
        id: '' // 修改所需要的字段
      },
      form_rules: {
        shelvesName: [
          {required: true, message: '货架名称不能为空！', trigger: ['blur', 'change']}
        ],
        warehouseId: [
          {required: true, message: '所属仓库不能为空！', trigger: ['blur', 'change']}
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
            'shelvesName': this[form].shelvesName,
            'warehouseId': this[form].warehouseId,
            'warehouseName': this[form].warehouseName,
            'createUserId': user.userId,
            'createUserName': user.username,
            'updateUserId': user.userId,
            'updateUserName': user.username,
            'status': '0',
            'companyId':'1'
          }
          if (this.shelvesDialog.type === 'add') {
            // 新增
            insertShelves(data).then((res) => {
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
          } else if (this.shelvesDialog.type === 'edit') {
            data.id = this.form.id
            // 更新
            updateShelvesByPrimaryKey(data).then((res) => {
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
    changeWarehouse: function (val) {
      for (let i = 0; i < this.warehouseOptionList.length; i++) {
        if (val === this.warehouseOptionList[i].id) {
          this.form.warehouseName = this.warehouseOptionList[i].name
        }
      }
    },
    initWarehouseOptionList: function () {
      // 从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let data ={
        'userId':user.userId,
        'companyId':"4"
      }
      getAllStoreByPrincipalId(data).then((res) => {
        this.warehouseOptionList = res
        // 初始化 下拉框（自动选择）
        let functionDialog = this.$store.getters.shelvesFunctionDialog
        if (functionDialog != null) {
          if (functionDialog.type === 'edit') {
            for (let i = 0; i < res.length; i++) {
              if (res[i].id === this.dialogRow.warehouseId) {
                // this.form.warehouse = res[i]
                this.form.warehouseId = res[i].id
                this.form.warehouseName = res[i].name
              }
            }
          }
        }
      })
    }
  },
  mounted () {
    this.initWarehouseOptionList()
    // 从 vuex 中 获取 functionDialog 基本信息
    let functionDialog = this.$store.getters.shelvesFunctionDialog
    if (functionDialog != null) {
      this.shelvesDialog.type = functionDialog.type
      this.shelvesDialog.title = functionDialog.title
      if (functionDialog.type === 'edit') {
        this.form = this.dialogRow
      }
    }
  },
  computed: {
    ...mapGetters([
      'shelvesFunctionDialog'
    ])
  }
}
</script>

<style scoped>

</style>
