<template>
  <div>
    <div style="margin-top: 1%">
      <!-- 上面 -->
      <el-row>
          <!-- 左侧图标 -->
          <el-col :span="8">
            <div  style="padding-left: 20px;padding: 20px">
              <el-col>
                <span style="padding: 35px; font-size: 25px">新增调拨单</span>
              </el-col>
              <el-col>
              <img style="padding-left: 20px; width: 150px;height: 150px" src="../../assets/add.png">
              <img style="padding-left: 20px; width: 150px;height: 150px" src="../../assets/add.png">
              </el-col>
            </div>
          </el-col>
          <!--右侧form表单-->
          <el-col :span="12">
            <!--添加form表单-->
            <el-form ref="form" :model="form" :rules="form_rules" label-width="100px">
              <el-col :span="12">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="工单编号:">
                      <span>{{orderNo}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="出库仓库:" prop='fromWarehouse'>
                      <el-select v-model="form.fromWarehouse" v-bind:disabled="fromWarehouseSelectDisabled"  placeholder="请选择出库的仓库">
                        <el-option v-for="(item, index) in fromWarehouseList" :label="item.name" :value="item.id" :key="index"></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="入库仓库:" prop='toWarehouse'>
                      <el-select v-model="form.toWarehouse" v-bind:disabled="toWarehouseSelectDisabled"  placeholder="请选择入库的仓库">
                        <el-option v-for="(item, index) in toWarehouseList" :label="item.name" :value="item.id" :key="index"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="审批人:" prop='approver'>
                      <el-select v-model="form.approver" v-bind:disabled="approverSelectDisabled" placeholder="请选择审批人" value-key="userId" @change="changeApprover">
                        <el-option v-for="(item, index) in userList" :label="item.userName" :value="item" :key="index"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
              <el-col :span="12">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="负责人:">
                      <span>{{username}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-col :span="24">
                  <el-form-item label="调拨描述:">
                    <el-input :rows="4" type="textarea" v-model="form.remark"></el-input>
                  </el-form-item>
                </el-col>
              </el-col>
            </el-form>
          </el-col>
      </el-row>
      <!-- 横线 -->
      <hr>
      <!-- 已选物品 -->
      <el-row>
        <el-col :span="24">
          <div>
            <el-row>
              <el-col :span="12"><img style="width: 25px;padding: 2px;float: left" src="../../assets/threeLine.png">
                <h3 style="padding: 2px;padding-left: 50px">已选物品</h3></el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>
      <!--已选物品操作按钮-->
      <el-row>
        <el-col :span="24">
          <div class="search_container searchArea">
            <el-form
              :inline="true"
              :model='searchData'
              ref="search_data"
              class="demo-form-inline search-form">
              <el-form-item label="">
                <el-input v-model="searchData.commodityName" placeholder="物品名称"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size ="mini" icon="el-icon-search" @click='getAllocationGoodsList()'>筛选</el-button>
                <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
              </el-form-item>
              <el-form-item class="btnRight">
                <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delAllocationGoods()' :disabled="delBtnDisabled">删除</el-button>
                <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addAllocationList()'>添加</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
      <!--已选物品展示-->
      <el-row>
        <el-col :span="24">
          <div class="fillcontain">
            <div class="table_container">
              <el-table v-loading="loading" :data="tableData"
                style="width: 100%" align='center'
                @selection-change="handleSelectionChange"
              >
                <!--id-->
                <el-table-column v-if="idFlag" prop="id" label="id" align='center'></el-table-column>
                <!--多选框-->
                <el-table-column type="selection" align='center'></el-table-column>
                <!-- 物品编码 -->
                <el-table-column prop="commodityBar" label="物品编码" align='center'></el-table-column>
                <!-- 物品名称 -->
                <el-table-column prop="commodityName" label="物品名称" align='center'></el-table-column>
                <!-- 原货物ID -->
                <el-table-column prop="locatorId" label="原货位ID" align='center'></el-table-column>
                 <!-- 目标货物ID -->
                <el-table-column prop="toLocatorId" label="目标货位ID" align='center'></el-table-column>
                <!-- 出库数量 -->
                <el-table-column prop="qty" label="出库数量" align='center'></el-table-column>
                <!-- 添加时间 -->
                <el-table-column prop="createTime" label="添加时间" align='center'></el-table-column>
                <!-- 操作栏 -->
                <el-table-column prop="operation" align='center' label="操作">
                  <template slot-scope='scope'>
                    <el-button type="warning" icon='edit' size="mini" @click='onEditAllocationGoods(scope.row)'>编辑</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 分页 -->
              <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
              <allocationGoodsFunctionDialog v-bind:fromWarehouseSelectDisabled="allocationGoodsDialog.fromWarehouseSelectDisabled" v-bind:toWarehouseSelectDisabled="allocationGoodsDialog.toWarehouseSelectDisabled" v-bind:orderNo="allocationGoodsDialog.orderNo" v-bind:fromWarehouseId="allocationGoodsDialog.fromWarehouseId" v-bind:toWarehouseId="allocationGoodsDialog.toWarehouseId"  v-if="allocationGoodsDialog.show" :isShow="allocationGoodsDialog.show" :dialogRow="allocationGoodsDialog.dialogRow"  @closeDialog="hideallocationGoodsDialog" @refresh="getAllocationGoodsList"></allocationGoodsFunctionDialog>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" style="text-align: center">
          <el-button type="primary" size="medium" @click='submitAllocationForm()'>提交表单</el-button>
          <el-button type="danger" size="medium" @click='emptyAllocationForm()'>清空数据</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import pagination from '@/views/warehouse/components/pagination/Index'
import { getAllStoreByPrincipalId } from '../../api/warehouse/index.js'
import { findAllApprovers } from '../../api/user/index.js'
import { getAllocationGoodsList, deleteAllByPid, deleteAllocationDetailByPrimaryKeys } from '../../api/allocation/allocationDetail.js'
import storage, {USER} from '../../utils/storage'
import allocationGoodsFunctionDialog from './AllocationGoodsFunctionDialog.vue'
import { describeNotCommit, updateAllocationByPrimaryKey, initOrderNo } from '../../api/allocation/index.js'
export default {
  name: 'newmyallocation',
  data () {
    return {
      form: {
        fromWarehouse: '',
        toWarehouse: '',
        approver: {
          'userId': '',
          'username': ''
        },
        remark: ''
      },
      form_rules: {
        fromWarehouse: [
          {required: true, message: '请选择仓库！', trigger: ['blur', 'change']}
        ],
        toWarehouse: [
          {required: true, message: '请选择仓库！', trigger: ['blur', 'change']}
        ],
        approver: [
          {required: true, message: '请选择审批人！', trigger: ['blur', 'change']}
        ]
      },
      orderNo: '',
      orderId: '',
      userId: '',
      username: '',
      fromWarehouseList: [],
      toWarehouseList: [],
      userList: [{"userName":"小明"}],
      tableData: [],
      idFlag: false,
      loading: false,
      page: {
        currentPage: 0,
        total: 0,
        pageSize: 10
      },
      searchData: {
        commodityName: ''
      },
      delBtnDisabled: true,
      allocationGoodsDialog: {
        show: false,
        fromWarehouseId: '',
        toWarehouseId: '',
        orderNo: '',
        fromWarehouseSelectDisabled: '',
        toWarehouseSelectDisabled: '',
        dialogRow: {}
      },
      fromWarehouseSelectDisabled: false,
      toWarehouseSelectDisabled: false,
      approverSelectDisabled: false,
      havaSelectedData: []
    }
  },
  methods: {
    changeApprover:function(item) {
      console.log(item);
      this.instore.approver.username = item.userName;
      console.log(this.instore.approver.userId);
      console.log(this.instore.approver.username);
    },
    handleSelectionChange: function (val) {
      if (val.length > 0) {
        // 只有 有选中的框 则删除 按钮可点
        this.delBtnDisabled = false
        // 将val 赋给选中的中的数据，以备删除
        this.havaSelectedData = val
      } else {
        // 没有有选中的框 则删除 按钮不可点
        this.delBtnDisabled = true
        // 将val 赋给选中的中的数据，以备删除
        this.havaSelectedData = val
      }
    },
    // 页数变化
    handleCurrentChange: function (val) {
      this.page.currentPage = val
      this.getAllocationGoodsList()
    },
    // 每页大小改变
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getAllocationGoodsList()
    },
    // 查询调拨子表中的物品数据
    getAllocationGoodsList: function () {
      let detailData = {
        'pid': this.orderNo,
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'commodityName': this.searchData.commodityName
      }
      getAllocationGoodsList(detailData).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.loading = false
      }).catch((res) => {
      })
    },
    resetSearchData: function () {
      this.searchData.commodityName = ''
    },
    addAllocationList: function () {
      if (this.form.fromWarehouse == null || undefined === this.form.fromWarehouse || this.form.fromWarehouse.length <= 0) {
        this.$alert('请先选择仓库', '参数错误', {
          confirmButtonText: '确定'
        })
        return false
      }
      if (this.form.toWarehouse == null || undefined === this.form.toWarehouse || this.form.toWarehouse.length <= 0) {
        this.$alert('请先选择仓库', '参数错误', {
          confirmButtonText: '确定'
        })
        return false
      }
      if (this.form.approver == null || undefined === this.form.approver || this.form.approver.userId.length <= 0) {
        this.$alert('请先选择审批人', '参数错误', {
          confirmButtonText: '确定'
        })
        return false
      }else {
        // 把新增基本信息 存入 vuex 中，以便后面初始化弹框title
        this.$store.commit('SET_ALLOCATION_DETAIL_FUNCTION_DIALOG_TPYE', 'add')
        this.allocationGoodsDialog.fromWarehouseId = this.form.fromWarehouse
        this.allocationGoodsDialog.toWarehouseId = this.form.toWarehouse
        this.allocationGoodsDialog.orderNo = this.orderNo
        this.allocationGoodsDialog.show = true
        this.allocationGoodsDialog.fromWarehouseSelectDisabled = this.fromWarehouseSelectDisabled
        this.allocationGoodsDialog.toWarehouseSelectDisabled = this.toWarehouseSelectDisabled
      }
    },
    hideallocationGoodsDialog: function () {
      this.allocationGoodsDialog.show = false
    },
    submitAllocationForm: function () {

      if (this.tableData == null || undefined === this.tableData || this.tableData.length <= 0) {
        this.$alert('请先选择出库物品', '参数错误', {
          confirmButtonText: '确定'
        })
        return false
      }
      // 1、从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let data = {
        'id': this.orderId,
        'state': 0,
        'remark': this.form.remark,
        'fromWarehouseId': this.form.fromWarehouse,
        'toWarehouseId': this.form.toWarehouse,
        'createId': user.userId,
        'creator': user.username,
        'updateId': user.userId,
        'updator': user.username,
        'auditId': this.form.approver.userId,
        'auditor': this.form.approver.username,
        'companyId':'1'
      }
      // 提交表单数据
      updateAllocationByPrimaryKey(data).then((res) => {
        this.$message({
          message: '新增调拨单成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后 页面跳转到我的调拨单

      }).catch((res) => {
      })
    },
    // TOdo
    emptyAllocationForm: function () {
      // 清空数据
      deleteAllByPid(this.orderNo).then((res) => {
        this.$message({
          message: '清空数据成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 将选择输入框清空
        this.form.approver = {}
        this.form.fromWarehouse = ''
        this.form.toWarehouse = ''
        this.form.remark = ''
        // 成功之后重新加载 页面
        this.getAllocationGoodsList()
      }).catch((res) => {
      })
    },
    // 删除数据
    delAllocationGoods: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteAllocationDetailByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getAllocationGoodsList()
      }).catch((res) => {
      })
    },
    // 修改
    onEditAllocationGoods: function (row) {
      this.allocationGoodsDialog.dialogRow = {...row}
      this.allocationGoodsDialog.dialogRow.fromLocatorId = this.allocationGoodsDialog.dialogRow.locatorId
      // this.allocationGoodsDialog.dialogRow.toLocatorId = this.allocationGoodsDialog.dialogRow.toLocatorId
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_ALLOCATION_DETAIL_FUNCTION_DIALOG_TPYE', 'edit')
      this.allocationGoodsDialog.fromWarehouseId = this.form.fromWarehouse
      this.allocationGoodsDialog.toWarehouseId = this.form.toWarehouse
      this.allocationGoodsDialog.orderNo = this.orderNo
      this.allocationGoodsDialog.show = true
    }
  },
  created () {
    let user = JSON.parse(storage.get(USER))
    let data ={
      principalId:user.userId,
      companyId:"1"
    }

    getAllStoreByPrincipalId(data).then((res) => {


      this.fromWarehouseList = res
      this.toWarehouseList = res
    }).catch((res) => {})
  },
  mounted () {
    var approver = ['approver'];
    findAllApprovers(approver).then((res) => {
      this.userList = res
    }).catch((res) => {
    })
    let user = JSON.parse(storage.get(USER))
    let data ={
      userId:user.userId,
      companyId:"1"
    }
    // 根据当前用户查询，是否存在未提交的工单数据
    describeNotCommit(data).then((res) => {
      if (res.length > 0){
        this.orderNo = res[0].allocationNo
        this.orderId = res[0].id
        this.form.fromWarehouse = res[0].fromWarehouseId
        this.form.toWarehouse = res[0].toWarehouseId
        this.form.approver.username = res[0].auditor
        this.form.approver.userId = res[0].auditor
        // 查询商品信息
        this.getAllocationGoodsList()
      } else {
        let data = {
          'userId': user.userId,
          'username': user.username
        }
        // 2、请求初始化工单
        initOrderNo(data).then((res) => {
          this.orderNo = res.allocationNo
          console.log(this.orderNo+"oopo")
        })
      }
    }).catch((res) => {
    })
    // 初始化工单号、用户数据
    this.userId = user.userId
    this.username = user.username
  },
  watch: {
    // 普通的tableData监听,只有tableData有数据，仓库下拉框就不可选，warehouseSelectDisabled 为 false
    tableData (val, oldVal) {
      if (val.length > 0) {
        this.fromWarehouseSelectDisabled = true
        this.toWarehouseSelectDisabled = true
        this.approverSelectDisabled = true
      } else {
        this.fromWarehouseSelectDisabled = false
        this.toWarehouseSelectDisabled = false
        this.approverSelectDisabled = false
      }
    }
  },
  components: {pagination,
    allocationGoodsFunctionDialog
  }
}
</script>

<style scoped>
  .search_container{
    margin-bottom: 20px;
  }
  .btnRight{
    float: right;
    margin-right: 0px !important;
  }
  .searchArea{
    background:rgba(255,255,255,1);
    border-radius:2px;
    padding: 18px 18px 0;
  }
  .table_container{
    padding: 10px;
    background: #fff;
    border-radius: 2px;
  }
</style>
