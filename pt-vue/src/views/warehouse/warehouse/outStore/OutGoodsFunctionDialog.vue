<template>
  <el-dialog
    :visible.sync="isVisible"
    :title="outStoreFunctionDialog.title"
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
            <el-cascader placeholder="请选择出库物品" v-model="form.goodMsg" :options="goodsList"></el-cascader>
          </el-col>
        </el-form-item>




        <el-form-item prop='num' label="出库数量:">
          <el-col :span="6">
            <el-input type="number" v-model="form.num" placeholder="请输入出库数量"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="转运公司" prop="outcompany">
          <el-select
            v-model="outcompany"
            size="mini"
            style="width:140px"
            filterable
            clearable
            remote
            reserve-keyword
            placeholder=" "
            :remote-method="listCompany"
          >
            <el-option
              v-for="item in companyOptions"
              :key="item.companyId"
              :label="item.companyName"
              :value="item.companyId">
            </el-option>
          </el-select>
        </el-form-item>

        <el-row>
          <el-col :span="24">
            <el-form-item label="供货商:">
              <el-select v-model="customer"   placeholder="请选择供货商" value-key="id">
                <el-option v-for="(item, index) in customerList"  :label="item.name" :value="item" :key="index"></el-option>
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
import { describeShelvesAndStockByWarehouseId, findAllCustomer } from '../../api/otherout/otherout.js'
import { insertByStockId, describeByLocatorId, updateByStockId, updateWarehouseIdByOtheroutNo } from '../../api/otherout/otheroutDetail.js'
import storage, {USER} from '../../utils/storage'
import {ListCompany} from '../../api/inOrder/inOrder.js'
import {describeGoodAndGoodcatgory} from "../../api/goods";
export default {
  name: 'OutGoodsFunctionDialog',
  data: function () {
    var goodMsg = (rule, value, callback) => {
      if (value.length !== 2) {
        callback(new Error('出库商品不能为空！'))
      } else {
        callback()
      }
    }
    var goodNum = (rule, value, callback) => {
      const boolean = new RegExp('^[1-9][0-9]*$').test(value)
      let numNotBigger = false
      // 出库数量不能大于物品数量
      for (let i = 0; i < this.goodsNumMsg.length; i++) {
        console.log(this.goodsNumMsg[i].id+"_____"+this.form.goodMsg[1])
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
        callback(new Error('出库数量大于物品数量！'))
      } else {
        callback()
      }
    }

    return {
      isVisible: this.isShow,
      goodsList: [], // 商品的级联选择数据
      customerList: [],
      customer: {
        'id': '',
        'name': ''
      },
      outcompany:'',
      incompany:'',
      form: {
        goodMsg: {},
        goodsId: '',
        num: 0,
        batch:''
      },
      form_rules: {
        goodMsg: [
          {required: true, message: '出库商品不能为空！', trigger: ['blur', 'change']},
          {validator: goodMsg, trigger: ['blur', 'change']}
        ],
        num: [
          {required: true, message: '出库数量不能为空！', trigger: ['blur', 'change']},
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
    listCompany(query){
      ListCompany(query).then(res=>{
        this.companyOptions = res
      })
    },
    setBuyerName(val){
      this.companyOptions.forEach(company => {
        if(company.companyId == val){
          this.form.buyerName = company.companyName
          return
        }
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
            'stockId': this.form.goodMsg[1],
            'num': this.form.num,
            'outcompany':this.outcompany,
            'companyId':'4'

        }
          // 新增或者修改商品
          if (this.outStoreFunctionDialog.type === 'add') {
            // todo 如果该报出库单已出库了该物品，则不能再新增数据，只能在原来的数据上修改
            // 新增需要先判断出库单数据的仓库id是否绑定
            if (!this.warehouseSelectDisabled) {
              // 出库未绑定仓库需要先更新出库数据表，绑定仓库信息
              let updateWarehouseData = {
                'otheroutNo': this.orderNo,
                'warehouseId': this.warehouseId,
                'customerId': this.customer.id,
                'customerName': this.customer.name,
                'companyId':'1'
              }
              // 需要先更新仓库信息
              updateWarehouseIdByOtheroutNo(updateWarehouseData).then((updateWarehouseDataRes) => {
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
          } else if (this.outStoreFunctionDialog.type === 'edit') {
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
    },
    getCustomerList: function () {
      findAllCustomer().then((res) => {
        this.customerList = res
      }).catch((res) => {
      })
    }
  },
  computed: {
    ...mapGetters([
      'outStoreFunctionDialog'
    ])
  },
  mounted () {
    // 根据 仓库id 获取 货架物品 信息
    describeShelvesAndStockByWarehouseId(this.warehouseId).then((res) => {
      this.goodsList = res
      // 从 vuex 中 获取 outStoreFunctionDialog 基本信息
      let outStoreFunctionDialog = this.$store.getters.outStoreFunctionDialog
      if (outStoreFunctionDialog != null) {
        this.outStoreFunctionDialog.type = outStoreFunctionDialog.type
        this.outStoreFunctionDialog.title = outStoreFunctionDialog.title
        if (outStoreFunctionDialog.type === 'edit') {
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
        console.log(res.length)
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
  },
  created () {
    this.getCustomerList()
    this.getGoodCategory()
  }

}
</script>

<style scoped>

</style>
