<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.orderNo" placeholder="出库单编号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getOtherinListLoading" size ="mini" icon="el-icon-search" @click='getOtherinList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center'>
        <el-table-column v-if="idFlag" prop="id" label="id" align='center'></el-table-column>
        <el-table-column prop="otherinNo" label="入库单编号" align='center'></el-table-column>
        <el-table-column prop="warehouseName" label="入库仓库" align='center'></el-table-column>
        <el-table-column prop="creator" label="入库单创建人" align='center'></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align='center'></el-table-column>
        <el-table-column prop="updateTime" label="发起时间" align='center'></el-table-column>
        <el-table-column prop="auditor" label="下一步审批人" align='center'></el-table-column>
        <el-table-column prop="operation" align='center' label="操作">
          <template slot-scope='scope'>
            <el-button type="primary" icon='edit' size="mini" @click='otherinDetail(scope.row)'>
              查看
            </el-button>
            <el-button type="success" icon='edit' size="mini" @click='agreeOtherin(scope.row)'>
              通过
            </el-button>
            <el-button type="danger" icon='edit' size="mini" @click='disagreeOtherin(scope.row)'>
              打回
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <otherinCommossionDialog v-if="otherinDetailDialog.show" :isShow="otherinDetailDialog.show" :dialogRow="otherinDetailDialog.dialogRow" @closeDialog="hideDialog"></otherinCommossionDialog>
    </div>
  </div>
</template>

<script>
// import { mapGetters } from 'vuex'
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeCommissionFuzzy, approveOtherin } from '../../api/inStore/otherin.js'
import storage, {USER} from '../../utils/storage'
import otherinCommossionDialog from './OtherinDetailDialog'
import { buildInOrder } from '../../api/inOrder/inOrder.js'
export default {
  name: 'otherincommossion',
  data () {
    return {
      idFlag: false,
      otherinDetailDialog: {
        show: false,
        dialogRow: {}
      },
      searchData: {
        orderNo: ''
      },
      getOtherinListLoading: false,
      loading: false,
      tableData: [],
      page: {
        total: 0,
        currentPage: 0,
        pageSize: 10
      }
    }
  },
  methods: {
    handleCurrentChange: function (val) {
      this.page.currentPage = val
      this.getOtherinList()
    },
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getOtherinList()
    },
    // 查询按钮
    getOtherinList: function () {
      this.getOtherinListLoading = true
      this.loading = true
      // 1、从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let data = {
        'userId': user.userId,
        'otherinNo': this.searchData.orderNo,
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'companyId':"1"
      }
      describeCommissionFuzzy(data).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.getOtherinListLoading = false
        this.loading = false
      }).catch((res) => {
        this.getOtherinListLoading = false
        this.loading = false
      })
    },
    // 重置按钮
    resetSearchData: function () {
      this.searchData.orderNo = ''
    },
    // 查看操作
    otherinDetail: function (row) {
      this.otherinDetailDialog.show = true
      this.otherinDetailDialog.dialogRow = {...row}
    },
    agreeOtherin: function (row) {
      let data = {
        'id': row.id,
        'state': '4'
      }

      // 审批通过操作
      approveOtherin(data).then((res) => {
        this.$message({
          message: '入库单审批成功',
          type: 'success',
          duration: 5 * 1000
        })
        this.getOtherinList()
      })
      // 形成订单操作
      buildInOrder(data).then((res) => {
        this.$message({
          message: '订单建立成功',
          type: 'success',
          duration: 5 * 1000
        })
      })
    },
    // 打回操作
    disagreeOtherin: function (row) {
      let data = {
        'id': row.id,
        'state': '5'
      }
      approveOtherin(data).then((res) => {
        this.$message({
          message: '入库单打回成功',
          type: 'success',
          duration: 5 * 1000
        })
        this.getOtherinList()
      })
    },
    // 关闭弹框
    hideDialog: function () {
      this.otherinDetailDialog.show = false
    }
  },
  mounted () {
    // 初始化 我的 待审批出库单 数据

    this.getOtherinList()
  },
  components: {otherinCommossionDialog, pagination}
}
</script>

<style scoped>

</style>
