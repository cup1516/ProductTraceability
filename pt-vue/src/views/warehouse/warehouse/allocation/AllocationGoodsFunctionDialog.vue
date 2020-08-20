<template>
  <el-dialog
    :visible.sync="isVisible"
    :title="allocationDetailFunctionDialog.title"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :modal-append-to-body="false"
    @close="closeDialog"
  >
    <div class="form">
      <el-form
        ref="form"
        :model="form"
        :rules="form_rules"
        :label-width="dialog.formLabelWidth"
        style="margin:10px;width:auto;"
      >
        <el-form-item prop="goodMsg" label="物品:">
          <el-col :span="6">
            <el-cascader ref="cascader-good" placeholder="请选择出库物品" v-model="form.goodMsg" :options="goodsList" @change="goodsHandleChange"></el-cascader>
          </el-col>
        </el-form-item>
        <el-form-item label="原货位:">
          <el-col :span="6">
            <el-cascader v-model="fromLocator" :options="fromShelvesList" @focus="handleChange1"></el-cascader>
          </el-col>
        </el-form-item>
        <el-form-item label="目标货位:">
          <el-col :span="6">
            <el-cascader ref="cascader"  :options="toShelvesList" @focus="handleChange2" @change="change3"></el-cascader>
          </el-col>
        </el-form-item>
        <el-form-item prop="num" label="出库数量:">
          <el-col :span="6">
            <el-input type="number" v-model="form.num" placeholder="请输入调拨数量"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item class="text_right">
          <el-button @click="isVisible = false">取 消</el-button>
          <el-button type="primary" @click="onSubmit('form')">确 认</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
// 导入vue 获取属性方法
import { mapGetters } from 'vuex'
import { decribeShelvesAndLocationByWarehouseId } from '../../api/inStore/index.js'
import { describeShelvesAndStockByWarehouseId } from '../..//api/otherout/otherout.js'
import { describeByLocatorId } from '../../api/otherout/otheroutDetail.js'
import storage, {USER} from '../../utils/storage'
import { updateAllocationByAllocationNo, selectStock } from '../../api/allocation/index.js'
import { insertAllocationDetail, updateAllocationDetail } from '../../api/allocation/allocationDetail.js'
export default {
  name: 'allocationGoodsFunctionDialog',
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
        if (this.goodsNumMsg[i].commodityBar === this.goodNo) {
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
      goodNo: '',
      isVisible: this.isShow,
      goodsList: [], // 商品的级联选择数据
      fromShelvesList: [],
      toShelvesList: [],
      fromLocator: {},
      toLocatorId: '',
      form: {
        goodMsg: {},
        goodsId: '',
        num: 0
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
    fromWarehouseId: String,
    toWarehouseId: String,
    orderNo: String,
    fromWarehouseSelectDisabled: Boolean,
    toWarehouseSelectDisabled: Boolean,
    dialogRow: Object
  },
  methods: {
    closeDialog: function () {
      this.$emit('closeDialog')
    },
    goodsHandleChange: function (val) {
      console.log(val)
      let dataObj = this.$refs['cascader-good'].getCheckedNodes()
      this.goodNo = dataObj[0].data.stockDTO.commodityBar
    },
    handleChange1: function () {
      // this.toLocatorId = dataObj[0].data.locatorDTO.id
      console.log('货架仓储级联数据', this.form.goodMsg)
      console.log('货架仓储级联数据', this.form.goodMsg[1])
      let data = {
        'warehouseId': this.fromWarehouseId,
        'goodsNo': this.goodNo
      }
      decribeShelvesAndLocationByWarehouseId(data).then((res) => {
        this.fromShelvesList = res
      }).catch((res) => {
      })
    },
    handleChange2: function () {
      // 入库操作
      let data = {
        'warehouseId': this.toWarehouseId,
        'goodsNo': this.goodNo
      }
      // console.log(this.toWarehouseId)
      console.log('--this.form.goodMsg[1]', this.form)
      console.log('--this.form.goodMsg[1]...', this.form.goodMsg[1])
      console.log('--goodNo--', data.goodsNo)
      decribeShelvesAndLocationByWarehouseId(data).then((res) => {
        this.toShelvesList = res
      }).catch((res) => {
      })
    },
    change3: function () {
      let dataObj = this.$refs['cascader'].getCheckedNodes()
      this.toLocatorId = dataObj[0].data.locatorDTO.id
    },
    onSubmit: function (form) {
      this.$refs[form].validate((valid) => {
        if (valid) { // 表单数据验证完成之后，提交数据;
          // 1、从sessionStorage 中 获取 user 对象
          let user = JSON.parse(storage.get(USER))
          let data = {
            'pid': this.orderNo,
            'qty': this.form.num,
            'companyId':"1"
          }
          // stockId为出库的仓储ID
          let date ={
            'stockId': this.form.goodMsg[1],
            'companyId':"1"
          }
          // 根据出库仓储ID查询出出货商品相关数据
          selectStock(date).then((res) => {
            console.log('res', res)
            // 出库的货位ID
            data['locatorId'] = res.locatorId
            data['commodityId'] = res.commodityId
            data['commodityName'] = res.commodityName
            data['commodityBar'] = res.commodityBar
            // 新增或者修改商品
            if (this.allocationDetailFunctionDialog.type === 'add') {
            // todo 如果该报出库单已出库了该物品，则不能再新增数据，只能在原来的数据上修改
            // 新增需要先判断出库单数据的仓库id是否绑定
              if (!this.fromWarehouseSelectDisabled || !this.toWarehouseSelectDisabled) {
              // 出库未绑定仓库需要先更新出库数据表，绑定仓库信息
                let updateAllocationData = {
                  'allocationNo': this.orderNo,
                  'bwarehouseId': this.fromWarehouseId,
                  'awarehouseId': this.awarehouseId,
                  'companyId':"1"
                }
                // 需要先给调拨单绑定原来的菜单和目的菜单
                updateAllocationByAllocationNo(updateAllocationData).then((res) => {
                })
              }
              // 无论是否绑定菜单，都会执行下面代码
              data['createId'] = user.userId
              data['creator'] = user.username
              data['updateId'] = user.userId
              data['updator'] = user.username
              // TODO 获取不到
              console.log('toLocatorId', this.toLocatorId)
              data['toLocatorId'] = this.toLocatorId
              insertAllocationDetail(data).then((res) => {
                this.$message({
                  message: '新增成功',
                  type: 'success',
                  duration: 5 * 1000
                })
                // 关闭弹框
                this.closeDialog()
                this.$emit('refresh')
              }).catch((res) => {})
            } else if (this.allocationDetailFunctionDialog.type === 'edit') {
            // 修改
              data['updateId'] = user.userId
              data['updator'] = user.username
              data['toLocatorId'] = this.toLocatorId
              // 修改ID
              data['id'] = this.detailId
              updateAllocationDetail(data).then((res) => {
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
          })
        }
      })
    }
  },
  computed: {
    ...mapGetters([
      'allocationDetailFunctionDialog'
    ])
  },
  mounted () {
    // 根据 仓库id 获取 货架物品 信息
    describeShelvesAndStockByWarehouseId(this.fromWarehouseId).then((res) => {
      // this.goodList 存放的是货架仓储的级联数据
      this.goodsList = res
      console.log('------this.goodsList------', this.goodsList)
      console.log('------this.goodsNo------', this.goodsList[0].children[0].stockDTO.commodityBar)
      // 从 vuex 中 获取 allocationDetailFunctionDialog 基本信息
      let allocationDetailFunctionDialog = this.$store.getters.allocationDetailFunctionDialog
      if (allocationDetailFunctionDialog != null) {
        this.allocationDetailFunctionDialog.type = allocationDetailFunctionDialog.type
        this.allocationDetailFunctionDialog.title = allocationDetailFunctionDialog.title
        if (allocationDetailFunctionDialog.type === 'edit') {
          // this.detailId为详情表(子表Id)
          this.detailId = this.dialogRow.id
          // this.form.num 为调拨数量
          this.form.num = this.dialogRow.qty
          console.log('详情子表Id', this.detailId)
          console.log('调拨数量', this.form.num)
          describeByLocatorId(this.dialogRow.fromLocatorId).then((slavesRes) => {
            console.log('shelves', slavesRes)
            console.log('this.dialogRow', this.dialogRow)
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
      // this.goodNumMsg 为仓库货架下的仓储列表
      this.goodsNumMsg = data
    }).catch((res) => {
    })
  }
}
</script>

<style scoped>
</style>
