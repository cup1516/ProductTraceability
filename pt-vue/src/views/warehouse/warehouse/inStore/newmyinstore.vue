<template>
  <div>
    <div style="margin-top: 1%">
      <!-- 上面 -->
      <el-row>
        <!-- 左侧图标 -->
        <el-col :span="8">
          <div  style="padding-left: 20px;padding: 20px">
            <el-col>
              <span style="padding: 35px; font-size: 25px">新增入库单</span>
            </el-col>
            <el-col>
              <img style="padding-left: 20px; width: 150px;height: 150px" src="../../assets/add.png">
            </el-col>
          </div>
        </el-col>
        <!--右侧form表单-->
        <el-col :span="12">
          <!--添加form表单-->
          <el-form ref="instore" :model="instore" label-width="100px">
            <el-col :span="12">
              <el-row>
                <el-col :span="24">
                  <el-form-item label="工单编号:">
                    <span>{{orderNo}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24"  >
                  <el-form-item label="仓库:" >
                    <el-select v-model="instore.warehouse" v-bind:disabled="warehouseSelectDisabled"  placeholder="请选择入库的仓库">
                      <el-option v-for="(item, index) in warehouseList"  :label="item.name" :value="item.id" :key="index" ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="审批人:">
                    <el-select v-model="instore.approver"  v-bind:disabled="approveSelectDisabled" placeholder="请选择审批人" value-key="userId" @change="changeApprover">
                      <el-option v-for="(item, index) in userList"  :label="item.userName" :value="item" :key="index"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="12">
              <el-row>
                <el-col :span="24">
                  <el-form-item label="入库负责人:">
                    <span>{{username}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-col :span="24">
                <el-form-item label="入库描述:">
                  <el-input :rows="4" type="textarea" v-model="instore.remark"></el-input>
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
                <el-button type="primary" size ="mini" icon="el-icon-search" >筛选</el-button>
                <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
              </el-form-item>
              <el-form-item class="btnRight">
                <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delInGoods()' :disabled="delBtnDisabled">删除</el-button>
                <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addInGoods()'>添加</el-button>
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
              <el-table
                v-loading="loading"
                :data="tableData"
                style="width: 100%"
                align='center'
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
                <!-- 货位编码 -->
                <el-table-column prop="locatorCode" label="货位编码" align='center'></el-table-column>
                <!-- 货位名称 -->
                <el-table-column prop="locatorName" label="货位名称" align='center'></el-table-column>
                <!-- 出库数量 -->
                <el-table-column prop="qty" label="入库数量" align='center'></el-table-column>
                <!-- 添加时间 -->
                <el-table-column prop="createTime" label="添加时间" align='center'></el-table-column>
                <!-- 操作栏 -->
                <el-table-column prop="operation" align='center' label="操作">
                  <template slot-scope='scope'>
                    <el-button type="warning" icon='edit' size="mini" @click='onEditInGoods(scope.row)'>编辑</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 分页 -->
              <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
              <InGoodsFunctionDialog v-bind:orderNo="inGoodsDialog.orderNo" v-if="inGoodsDialog.show"  :warehouseId="this.instore.warehouse" :isShow="inGoodsDialog.show" :dialogRow="inGoodsDialog.dialogRow" :approver="instore.approver" @closeDialog="hideInGoodsDialog" @refresh="getInGoodsList"></InGoodsFunctionDialog>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" style="text-align: center">
          <el-button type="primary" size="medium" @click='submitInStoreForm()' >提交表单</el-button>
          <el-button type="danger" size="medium" @click='emptyInStoreForm()'>清空数据</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeNotCommit, initOrderNo, getAllUser, updateOtherinByPrimaryKey } from '../../api/inStore/otherin.js'
import { getAllStoreByPrincipalId } from '../../api/store/index.js'
import { describeFuzzy, clearNotCommitDetail } from '../../api/inStore/otherinDetail.js'
import storage, {USER} from '../../utils/storage'
import InGoodsFunctionDialog from './InGoodsFunctionDialog'
import { findAllApprovers } from '../../api/user/index.js'

export default {
  name: 'newmyinstore',
  data () {
    return {
      instoreGoods: [],
      principalId: '',
      havaSelectedData: [],
      instore: {
        warehouse: '',
        approver: {
          'userId': '',
          'username': ''
        },
        remark: ''
      },
      orderNo: '',
      userId: '',
      username: '',
      warehouseList: [{}],
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
      inGoodsDialog: {
        warehouseId: '',
        orderNo: '',
        show: false,
        dialogRow: {}
      },
      warehouseSelectDisabled: false,
      approveSelectDisabled: false,
      orderId: ''
    }
  },
  methods: {
    changeApprover:function(item) {
      console.log(item);
      this.instore.approver.username = item.userName;
      console.log(this.instore.approver.userId);
      console.log(this.instore.approver.username);
    },
    getAllStore: function () {
      let user = JSON.parse(storage.get(USER))
      let data = {
        'principalId': user.userId,
        'companyId':"1"
      }
      getAllStoreByPrincipalId(data).then((res) => {
        this.warehouseList = res
      })
    },
    getAllUser: function () {
      getAllUser().then((res) => {
        this.userList = res
      })
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
    onEditInGoods: function (row) {
      this.inGoodsDialog.dialogRow = {...row}
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_IN_STORE_FUNCTION_DIALOG_TPYE', 'edit')
      this.inGoodsDialog.warehouseId = this.instore.warehouse
      this.inGoodsDialog.orderNo = this.orderNo
      this.inGoodsDialog.show = true
    },
    handleCurrentChange: function (val) {

    },
    handleSizeChange: function () {
    },
    // 查询在入库子表中的物品数据
    getInGoodsList: function () {
      let detailData = {
        'pid': this.orderNo,
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'commodityName': this.searchData.commodityName
      }
      describeFuzzy(detailData).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.loading = false
      }).catch((res) => {
      })
    },
    addInGoods: function () {
      // 把新增基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_IN_STORE_FUNCTION_DIALOG_TPYE', 'add')
      if (this.instore.warehouse === null || this.instore.warehouse === 'undefined'|| this.instore.warehouse.length === 0) {
        this.inGoodsDialog.show = false
        this.$alert('请先选择仓库', '参数错误', {
          confirmButtonText: '确定'
        })
      } else if (this.instore.approver.userId === null || this.instore.approver.userId === 'undefined' || this.instore.approver.userId.length === 0) {
        this.inGoodsDialog.show = false
        this.$alert('请先选择审批人', '参数错误', {
          confirmButtonText: '确定'
        })
      } else {
        this.$store.commit('SET_IN_STORE_FUNCTION_DIALOG_TPYE', 'add')
        this.inGoodsDialog.warehouseId = this.instore.warehouse
        this.inGoodsDialog.orderNo = this.orderNo
        this.inGoodsDialog.show = true
      }
    },
    hideInGoodsDialog: function () {
      this.inGoodsDialog.show = false
    },
    submitInStoreForm: function () {
      let user = JSON.parse(storage.get(USER))
      let data = {
        'id': this.orderId,
        'state': '0',
        'remark': this.instore.remark,
        'warehouseId': this.instore.warehouse,
        'createId': user.userId,
        'creator': user.username,
        'updateId': user.userId,
        'updator': user.username,
        'auditId': this.instore.approver.userId,
        'auditor': this.instore.approver.username,
        'companyId':'1'
      }
      updateOtherinByPrimaryKey(data).then((res) => {
        this.$message({
          message: '新增入库单成功',
          type: 'success',
          duration: 5 * 1000

        })
        this.$router.push({
          path: '/warehouse/88/warehouse/warehouse/inStore/MyInStore'
        })
        // 成功之后 页面跳转到我的出库单

      })
    },
    emptyInStoreForm: function () {
      let list = []
      for (let i = 0; i < this.tableData.length; i++) {
        list.push(this.tableData[i].id)
      }
      clearNotCommitDetail(list).then((res) => {
        this.getInGoodsList()
      }).catch((res) => {
      })
    },

    delInGoods: function () {
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      clearNotCommitDetail(list).then((res) => {
        this.getInGoodsList()
      }).catch((res) => {
      })
    }
  },

  created () {
    this.getAllStore()
  },
  mounted () {
    //todo 这函数换成刚刚的接口，参数为： c
    var approver = ['list'];
    findAllApprovers(approver).then((res) => {
      this.userList = res
    }).catch((res) => {
    })
    // 1、从sessionStorage 中 获取 user 对象
    let user = JSON.parse(storage.get(USER))
    // 先根据当前用户查询，是否存在未提交的工单数据
    let data ={
      userId:user.userId,
      companyId:"1"
    }
    describeNotCommit(data).then((res) => {
      if (res.length > 0) {
        console.log("没有初始化工单")
        this.orderNo = res[0].otherinNo
        this.orderId = res[0].id
        this.instore.warehouse = res[0].warehouseId
        this.instore.approver.username = res[0].auditor
        this.instore.approver.userId = res[0].auditId
        // 查询商品信息
        this.getInGoodsList()
      } else {
        let data = {
          'userId': user.userId,
          'username': user.username
        }
        // 2、请求初始化工单
        initOrderNo(data).then((res) => {
          console.log("初始化工单")
          this.orderNo = res.otherinNo
          this.orderId = res.id
        }).catch((res) => {
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
        this.warehouseSelectDisabled = true
        this.approveSelectDisabled = true
      } else {
        this.warehouseSelectDisabled = false
        this.approveSelectDisabled = false
      }
    }
  },
  components: {pagination, InGoodsFunctionDialog}
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
