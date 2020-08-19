<template>
  <el-dialog
    :visible.sync="isVisible"
    :title="dispatchFunctionDialog.title"
    :close-on-click-modal='false'
    :close-on-press-escape='false'
    :modal-append-to-body="false"
    @close="closeDialog">
    <div class="form">
      <el-form
        ref="form"
        :model="form"
        :rules="form_rules"
        :label-width="dialog.formLabelWidth"
        style="margin:10px;width:auto;">
        <el-form-item prop='goodMsg' label="物品:">
          <el-col :span="6">
            <el-cascader placeholder="请选择报损物品" v-model="form.goodMsg" :options="goodsList"></el-cascader>
          </el-col>
        </el-form-item>
        <el-form-item prop='num' label="报损数量:">
          <el-col :span="6">
            <el-input type="number" v-model="form.num" placeholder="请输入报损数量"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item  class="text_right">
          <el-button @click="isVisible = false">取 消</el-button>
          <el-button type="primary" @click='onSubmit("form")'>确 认</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
// 导入vue 获取属性方法
import { mapGetters } from 'vuex'
import { describeShelvesAndStockByWarehouseId } from '../../api/otherout/otherout.js'
import { describeByLocatorId } from '../../api/otherout/otheroutDetail.js'
import { updateWarehouseIdByDispatchNo } from '../../api/dispatch/dispatch.js'
import { updateByStockId, insertByStockId } from '../../api/dispatch/dispatchDetail.js'
import storage, {USER} from '../../utils/storage'
export default {
  name: 'DispatchGoodsFunctionDialog',
  data: function () {
    var goodMsg = (rule, value, callback) => {
      if (value.length !== 2) {
        callback(new Error('报损商品不能为空！'))
      } else {
        callback()
      }
    }
    var goodNum = (rule, value, callback) => {
      const boolean = new RegExp('^[1-9][0-9]*$').test(value)

      let numNotBigger = false

      // 报损数量不能大于物品数量
      for (let i = 0; i < this.goodsNumMsg.length; i++) {
        if (this.goodsNumMsg[i].id === this.form.goodMsg[1]) {
          if (this.form.num <= this.goodsNumMsg[i].cqty) {
            numNotBigger = true
          }
        }
      }

      let dataNotNull = false
      if (this.form.goodMsg[1] != null && this.form.goodMsg[1] !== undefined) {
        dataNotNull = true
      }

      if (!boolean) {
        callback(new Error('请输入正整数！'))
      } else if (!numNotBigger && dataNotNull) {
        callback(new Error('报损数量大于物品数量！'))
      } else {
        callback()
      }
    }
    return {
      isVisible: this.isShow,
      goodsList: [], // 商品的级联选择数据
      form: {
        goodMsg: {},
        goodsId: '',
        num: 0
      },
      form_rules: {
        goodMsg: [
          {required: true, message: '报损商品不能为空！', trigger: ['blur', 'change']},
          {validator: goodMsg, trigger: ['blur', 'change']}
        ],
        num: [
          {required: true, message: '报损数量不能为空！', trigger: ['blur', 'change']},
          {validator: goodNum, trigger: ['blur', 'change']}
        ]
      },
      // 详情弹框信息
      dialog: {
        width: '400px',
        formLabelWidth: '120px'
      },
      // 更新数据id
      detailId: '',
      goodsNumMsg: []
    }
  },
  props: {
    isShow: Boolean,
    warehouseId: String,
    orderNo: String,
    warehouseSelectDisabled: Boolean,
    dialogRow: Object
  },
  methods: {
    closeDialog: function () {
      this.$emit('closeDialog')
    },
    onSubmit: function (form) {
      this.$refs[form].validate((valid) => {
        if (valid) { // 表单数据验证完成之后，提交数据;
          // 1、从sessionStorage 中 获取 user 对象
          let user = JSON.parse(storage.get(USER))
          let data = {
            'pid': this.orderNo,
            'stockId': this.form.goodMsg[1],
            'num': this.form.num,
            'companyId':'1'
          }
          // 新增或者修改商品
          if (this.dispatchFunctionDialog.type === 'add') {
            // todo 如果该报损单已报损了该物品，则不能再新增数据，只能在原来的数据上修改
            // 新增需要先判断报损单数据的仓库id是否绑定
            if (!this.warehouseSelectDisabled) {
              // 报损未绑定仓库需要先更新报损数据表，绑定仓库信息
              let updateWarehouseData = {
                'dispatchNo': this.orderNo,
                'warehouseId': this.warehouseId,
                'companyId':'1'
              }
              // 需要先更新仓库信息
              updateWarehouseIdByDispatchNo(updateWarehouseData).then((updateWarehouseDataRes) => {
                data['createId'] = user.userId
                data['creator'] = user.username
                data['updateId'] = user.userId
                data['updator'] = user.username
                insertByStockId(data).then((res) => {
                  this.$message({
                    message: '新增成功',
                    type: 'success',
                    duration: 5 * 1000
                  })
                  // 关闭弹框
                  this.closeDialog()
                  // 刷新页面,执行父组件的refresh，也是就getEmployeeList
                  this.$emit('refresh')
                }).catch((res) => {
                })
              })
            } else {
              data['createId'] = user.userId
              data['creator'] = user.username
              data['updateId'] = user.userId
              data['updator'] = user.username
              insertByStockId(data).then((res) => {
                this.$message({
                  message: '新增成功',
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
          } else if (this.dispatchFunctionDialog.type === 'edit') {
            // 修改
            data['updateId'] = user.userId
            data['updator'] = user.username
            data['detailId'] = this.detailId
            updateByStockId(data).then((res) => {
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
  computed: {
    ...mapGetters([
      'dispatchFunctionDialog'
    ])
  },
  mounted () {
    // 根据 仓库id 获取 货架物品 信息
    describeShelvesAndStockByWarehouseId(this.warehouseId).then((res) => {
      this.goodsList = res
      // 从 vuex 中 获取 outStoreFunctionDialog 基本信息
      let dispatchFunctionDialog = this.$store.getters.dispatchFunctionDialog
      if (dispatchFunctionDialog != null) {
        this.dispatchFunctionDialog.type = dispatchFunctionDialog.type
        this.dispatchFunctionDialog.title = dispatchFunctionDialog.title
        if (dispatchFunctionDialog.type === 'edit') {
          this.detailId = this.dialogRow.id
          this.form.num = this.dialogRow.qty
          describeByLocatorId(this.dialogRow.locatorId).then((slavesRes) => {
            this.form.goodMsg = [slavesRes.id, this.dialogRow.stockId]
          }).catch((res) => {
          })
        }
      }
      // 赋值 物品 个数 信息
      let data = []
      if (res.length > 0) {
        for (let i = 0; i < res.length; i++) {
          if (res[i].children != null && undefined !== res[i].children && res[i].children.length > 0) {
            for (let j = 0; j < res[i].children.length; j++) {
              data.push(res[i].children[j].stockDTO)
            }
          }
        }
      }
      this.goodsNumMsg = data
    }).catch((res) => {
    })
  }
}
</script>

<style scoped>

</style>
