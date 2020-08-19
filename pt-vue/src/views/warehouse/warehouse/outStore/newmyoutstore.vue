<template>
  <div>
    <div style="margin-top: 1%">
      <!-- 上面 -->
      <el-row>
          <!-- 左侧图标 -->
          <el-col :span="8">
            <div  style="padding-left: 20px;padding: 20px">
              <el-col>
                <span style="padding: 35px; font-size: 25px">新增出库单</span>
              </el-col>
              <el-col>
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
                    <el-form-item label="仓库:" prop='warehouse'>
                      <el-select v-model="form.warehouse" v-bind:disabled="warehouseSelectDisabled"  placeholder="请选择出库的仓库">
                        <el-option v-for="(item, index) in warehouseList" :label="item.name" :value="item.id" :key="index"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="审批人:" prop='approver'>
                      <el-select v-model="form.approver" placeholder="请选择审批人" value-key="userId" @change="changeApprover">
                        <el-option v-for="(item, index) in userList" :label="item.userName" :value="item" :key="index"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
              <el-col :span="12">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="出库负责人:">
                      <span>{{username}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-col :span="24">
                  <el-form-item label="出库描述:">
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
                <el-button type="primary" size ="mini" icon="el-icon-search" @click='getOutGoodsList()'>筛选</el-button>
                <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
              </el-form-item>
              <el-form-item class="btnRight">
                <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delOutGoods()' :disabled="delBtnDisabled">删除</el-button>
                <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addOutGoods()'>添加</el-button>
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
                <!-- 货位编码 -->
                <el-table-column prop="locatorCode" label="货位编码" align='center'></el-table-column>
                <!-- 货位名称 -->
                <el-table-column prop="locatorName" label="货位名称" align='center'></el-table-column>
                <!-- 出库数量 -->
                <el-table-column prop="qty" label="出库数量" align='center'></el-table-column>
                <!-- 添加时间 -->
                <el-table-column prop="createTime" label="添加时间" align='center'></el-table-column>

                <el-table-column prop="batch" label="批次" align='center'></el-table-column>

                <!-- 操作栏 -->
                <el-table-column prop="operation" align='center' label="操作">
                  <template slot-scope='scope'>
                    <el-button type="warning" icon='edit' size="mini" @click='onEditOutGoods(scope.row)'>编辑</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 分页 -->
              <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
              <outGoodsFunctionDialog v-bind:warehouseSelectDisabled="outGoodsDialog.warehouseSelectDisabled" v-bind:orderNo="outGoodsDialog.orderNo" v-bind:warehouseId="outGoodsDialog.warehouseId"  v-if="outGoodsDialog.show" :isShow="outGoodsDialog.show" :dialogRow="outGoodsDialog.dialogRow"  @closeDialog="hideOutGoodsDialog" @refresh="getOutGoodsList"></outGoodsFunctionDialog>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" style="text-align: center">
          <el-button type="primary" size="medium" @click='submitOutStoreForm()'>提交表单</el-button>
          <el-button type="danger" size="medium" @click='emptyOutStoreForm()'>清空数据</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeNotCommit, initOrderNo, updateOtheroutByPrimaryKey } from '../../api/otherout/otherout.js'
import { describeFuzzy, deleteOtheroutDetailByPrimaryKeys, deleteAllByPid } from '../../api/otherout/otheroutDetail.js'
import { getAllStoreByPrincipalId } from '../../api/warehouse/index.js'
import { findAllApprovers } from '../../api/user/index.js'
import storage, {USER} from '../../utils/storage'
import outGoodsFunctionDialog from './OutGoodsFunctionDialog'
export default {
  name: 'newmyoutstore',
  data () {
    return {
      outstoreGoods: [],
      form: {
        warehouse: '',
        approver: {
          'userId': '',
          'username': ''
        },
        remark: ''
      },
      form_rules: {
        warehouse: [
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
      warehouseList: [],
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
      outGoodsDialog: {
        show: false,
        warehouseId: '',
        orderNo: '',
        warehouseSelectDisabled: '',
        dialogRow: {}
      },
      warehouseSelectDisabled: false,
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
      this.getOutGoodsList()
    },
    // 每页大小改变
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getOutGoodsList()
    },
    // 查询在出库子表中的物品数据
    getOutGoodsList: function () {
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
    resetSearchData: function () {
      this.searchData.commodityName = ''
    },
    addOutGoods: function () {
      if (this.form.warehouse != null && this.form.warehouse !== undefined && this.form.warehouse.length > 0) {
        // 把新增基本信息 存入 vuex 中，以便后面初始化弹框title
        this.$store.commit('SET_OUT_STORE_FUNCTION_DIALOG_TPYE', 'add')
        this.outGoodsDialog.warehouseId = this.form.warehouse
        this.outGoodsDialog.orderNo = this.orderNo
        this.outGoodsDialog.show = true
        this.outGoodsDialog.warehouseSelectDisabled = this.warehouseSelectDisabled
      } else {
        this.$alert('请先选择仓库', '参数错误', {
          confirmButtonText: '确定'
        })
      }
    },
    hideOutGoodsDialog: function () {
      this.outGoodsDialog.show = false
    },
    submitOutStoreForm: function () {
      if (this.form.warehouse == null || undefined === this.form.warehouse || this.form.warehouse.length <= 0) {
        this.$alert('请先选择仓库', '参数错误', {
          confirmButtonText: '确定'
        })
        return false
      }
      if (this.form.approver == null || undefined === this.form.approver || this.form.approver.userId.length === 0) {
        this.$alert('请先选择审批人', '参数错误', {
          confirmButtonText: '确定'
        })
        return false
      }
      if (this.tableData == null || undefined === this.tableData ||this.tableData.length <= 0 ) {
        this.$alert('请先选择出库物品', '参数错误', {
          confirmButtonText: '确定'
        })
        return false
      }
      // 1、从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let data = {
        'id': this.orderId,
        'state': '0',
        'remark': this.form.remark,
        'warehouseId': this.form.warehouse,
        'createId': user.userId,
        'creator': user.username,
        'updateId': user.userId,
        'updator': user.username,
        'auditId': this.form.approver.userId,
        'auditor': this.form.approver.username,
        'companyId':'1'
      }
      // 提交表单数据
      updateOtheroutByPrimaryKey(data).then((res) => {
        this.$message({
          message: '新增入库单成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后 页面跳转到我的出库单
      }).catch((res) => {
      })
    },
    emptyOutStoreForm: function () {
      // 清空数据
      deleteAllByPid(this.orderNo).then((res) => {
        this.$message({
          message: '清空数据成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 将选择输入框清空
        this.form.approver = {}
        this.form.warehouse = ''
        this.form.remark = ''
        // 成功之后重新加载 页面
        this.getOutGoodsList()
      }).catch((res) => {
      })
    },
    // 删除数据
    delOutGoods: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteOtheroutDetailByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getOutGoodsList()
      }).catch((res) => {
      })
    },
    // 修改
    onEditOutGoods: function (row) {
      this.outGoodsDialog.dialogRow = {...row}
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_OUT_STORE_FUNCTION_DIALOG_TPYE', 'edit')
      this.outGoodsDialog.warehouseId = this.form.warehouse
      this.outGoodsDialog.orderNo = this.orderNo
      this.outGoodsDialog.show = true
    }
  },
  mounted () {
    // 1、从sessionStorage 中 获取 user 对象
    let user = JSON.parse(storage.get(USER))
    console.log(user.userId)
    // 2、初始化仓库下拉框
    let date = {
      'userId': user.userId,
      'companyId':"4"
    }
    getAllStoreByPrincipalId(date).then((res) => {
      this.warehouseList = res
    }).catch((res) => {
    })
    // 3、初始化审批人下拉框
    var approver = ['approver'];
    findAllApprovers(approver).then((res) => {
      this.userList = res
    }).catch((res) => {
    })
    // 3、根据当前用户查询，是否存在未提交的工单数据
    let data ={
      userId:user.userId,
      companyId:"1"
    }
    describeNotCommit(data).then((res) => {
      if (res.length > 0) {
        this.orderNo = res[0].otheroutNo
        this.orderId = res[0].id
        if (res[0].warehouseId != null && undefined !== res[0].warehouseId && res[0].warehouseId.length > 0) {
          // 选中仓库
          this.form.warehouse = res[0].warehouseId
        }
        // 查询商品信息
        this.getOutGoodsList()
      } else {
        let data = {
          'userId': user.userId,
          'username': user.username
        }
        // 2、请求初始化工单
        initOrderNo(data).then((res) => {
          this.orderNo = res.otheroutNo
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
      } else {
        this.warehouseSelectDisabled = false
      }
    }
  },
  components: {pagination, outGoodsFunctionDialog}
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
