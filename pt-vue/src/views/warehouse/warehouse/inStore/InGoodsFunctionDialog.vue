<template>
  <el-dialog :visible.sync="isVisible" :title="inStoreFunctionDialog.title"
             :close-on-click-modal='false' :close-on-press-escape='false'
             :modal-append-to-body="false" @close="closeDialog">
    <div class="form">
      <el-form ref="form" :model="form" :rules="form_rules" :label-width="dialog.formLabelWidth" style="margin:10px;width:auto;">
        <el-form-item  label="物品:">
          <el-col :span="6">
            <el-cascader v-model="form.goodMsg"  :props="{ value: 'code' }" :options="goodsList" @focus="handlegoodChange"></el-cascader>
          </el-col>
        </el-form-item>

        <el-form-item prop='batch' label="批次号:">
          <el-col :span="6">
            <el-input v-model="form.batch" placeholder="请输入批次号"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="货位:">
          <el-col :span="6">
            <el-cascader v-model="locator"   :options="shelvesList"  @focus="handleChange"></el-cascader>
          </el-col>
        </el-form-item>

        <el-form-item prop='num' label="入库数量:">
          <el-col :span="6">
            <el-input v-model="form.num" placeholder="请输入入库数量"></el-input>
          </el-col>
        </el-form-item>



        <el-row>
          <el-col :span="24">
            <el-form-item label="供货商:">
              <el-select v-model="supplier"   placeholder="请选择供货商" value-key="id">
                <el-option v-for="(item, index) in supplierList"  :label="item.name" :value="item" :key="index"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

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
import { decribeShelvesAndLocationByWarehouseId, findAllSupplier, queryEditGoodMsg, queryEditshelvesMsg, querySupplierMsg } from '../../api/inStore/index.js'
import { insertOtherinDetail, updateWarehouseIdByOtherinNo, updateOtherinDetail } from '../../api/inStore/otherinDetail.js'
import { describeGoodAndGoodcatgory } from '../../api/goods/index.js'
import storage, {USER} from '../../utils/storage'
export default {
  name: 'InGoodsFunctionDialog',
  data: function () {
    var goodMsg = (rule, value, callback) => {
      if (value.length !== 2) {
        callback(new Error('入库商品不能为空！'))
      } else {
        callback()
      }
    }


    var batch = (rule, value, callback) => {
      if (value.length <= 0) {
        callback(new Error('批次号不能为空！'))
      } else {
        callback()
      }
    }

    var goodNum = (rule, value, callback) => {
      const boolean = new RegExp('^[1-9][0-9]*$').test(value)
      if (!boolean) {
        callback(new Error('请输入正整数！'))
      } else {
        callback()
      }
    }

    return {
      isVisible: this.isShow,
      goodsList: [], // 商品的级联选择数据
      shelvesList: [], // 货位级联数据
      supplierList: [],
      detailId: '',
      locator: {},
      goodCategoryId: '',
      supplier: {
        'id': '',
        'name': ''
      },
      addDialog: {
        dialogRow: {}
      },
      form: {
        goodMsg: {},
        shelvesMeg: '',
        num: 0,
        batch:''
      },
      form_rules: {
        goodMsg: [
          {required: true, message: '入库商品不能为空！', trigger: ['blur', 'change']},
          {validator: goodMsg, trigger: ['blur', 'change']}
        ],
        num: [
          {required: true, message: '入库数量不能为空！', trigger: ['blur', 'change']},
          {validator: goodNum, trigger: ['blur', 'change']}
        ],

        batch: [
          {required: true, message: '批次号不能为空！', trigger: ['blur', 'change']},
          {validator: batch, trigger: ['blur', 'change']}
        ],

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
    dialogRow: Object,
    orderNo: String,
    warehouseId: String,
    approver: Object
  },
  methods: {
    getGoodCategory: function () {
      describeGoodAndGoodcatgory().then((res) => {
        this.goodsList = res
        console.log(res[0])
      }).catch((e) => {
      })
    },

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
            'qty': this.form.num,
            'locatorId': this.locator[1],
            'commodityBar': this.form.goodMsg[1],
            'companyId':"1",
            'batch':this.form.batch

          }
          // 新增或者修改商品
          if (this.inStoreFunctionDialog.type === 'add') {
            // 新增需要先判断出库单数据的仓库id是否绑定
            if (!this.warehouseSelectDisabled) {
              // 出库未绑定仓库需要先更新出库数据表，绑定仓库信息
              let updateWarehouseData = {
                'otherinNo': this.orderNo,
                'warehouseId': this.warehouseId,
                'supplierId': this.supplier.id,
                'supplierName': this.supplier.name,
                'auditId': this.approver.userId,
                'auditor': this.approver.username,
                'companyId':'1'
              }
              // 需要先更新仓库信息
              updateWarehouseIdByOtherinNo(updateWarehouseData).then((updateWarehouseDataRes) => {
                data['createId'] = user.userId
                data['creator'] = user.username
                data['updateId'] = user.userId
                data['updator'] = user.username

                insertOtherinDetail(data).then((res) => {

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
              insertOtherinDetail(data).then((res) => {
                this.$message({
                  message: '成功',
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
          } else if (this.inStoreFunctionDialog.type === 'edit') {
            let updateWarehouseData2 = {
              'otherinNo': this.orderNo,
              'supplierId': this.supplier.id,
              'supplierName': this.supplier.name
            }
            updateWarehouseIdByOtherinNo(updateWarehouseData2).then((updateWarehouseDataRes) => {
            })

            // 修改
            data['id'] = this.detailId
            data['updateId'] = user.userId
            data['updator'] = user.username
            data['qty'] = this.form.num
            updateOtherinDetail(data).then((res) => {
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
    },
    handleChange: function () {
      console.log(this.form.goodMsg[1])
      if (this.form.goodMsg[1] === 'undefined') {
        this.$message({
          message: '请先选择商品',
          type: 'success',
          duration: 5 * 1000
        })
      } else {
        let data = {
          'warehouseId': this.warehouseId,
          'goodsNo': this.form.goodMsg[1],
          'batch':this.form.batch
        }
        decribeShelvesAndLocationByWarehouseId(data).then((res) => {
          this.shelvesList = res
        }).catch((res) => {
        })
      }
    },
    initEdit: function () {
      let inStoreFunctionDialog = this.$store.getters.inStoreFunctionDialog
      if (inStoreFunctionDialog != null) {
        this.inStoreFunctionDialog.type = inStoreFunctionDialog.type
        this.inStoreFunctionDialog.title = inStoreFunctionDialog.title
        if (inStoreFunctionDialog.type === 'edit') {
          this.detailId = this.dialogRow.id
          this.form.num = this.dialogRow.qty
          this.form.goodMsg = this.dialogRow.goodMsg
          this.locator = this.dialogRow.locator
          let data1 = this.dialogRow.commodityBar
          queryEditGoodMsg(data1).then((res) => {
            this.form.goodMsg = [Number(res.goodCategoryId), this.dialogRow.commodityBar]
          }).catch((res) => {
          })
          let data2 = this.dialogRow.locatorCode
          queryEditshelvesMsg(data2).then((res) => {
            this.locator = [res.shelvesId, res.id]
          }).catch((res) => {
          })
          let data3 = this.orderNo
          querySupplierMsg(data3).then((res) => {
            this.supplier.id = res.supplierId
            this.supplier.name = res.supplierName
          }).catch((res) => {
          })
        }
      }
    },
    getSupplierList: function () {
      findAllSupplier().then((res) => {
        this.supplierList = res
      }).catch((res) => {
      })
    }
  },

  computed: {
    ...mapGetters([
      'inStoreFunctionDialog'
    ])
  },
  created () {
    this.initEdit()
    this.getSupplierList()
    this.getGoodCategory()
  }
}
</script>

<style scoped>

</style>
