<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.orderNo" placeholder="报损单编号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getDispatchListLoading" size ="mini" icon="el-icon-search" @click='getDispatchList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center'>
        <el-table-column v-if="idFlag" prop="id" label="id" align='center'></el-table-column>
        <el-table-column prop="dispatchNo" label="报损单编号" align='center'></el-table-column>
        <el-table-column prop="warehouseName" label="报损仓库" align='center'></el-table-column>
        <el-table-column prop="creator" label="报损单创建人" align='center'></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align='center'></el-table-column>
        <el-table-column prop="updateTime" label="发起时间" align='center'></el-table-column>
        <el-table-column prop="auditor" label="下一步审批人" align='center'></el-table-column>
        <el-table-column prop="operation" align='center' label="操作">
          <template slot-scope='scope'>
            <el-button type="primary" icon='edit' size="mini" @click='dispatchDetail(scope.row)'>
              查看
            </el-button>
            <el-button type="success" icon='edit' size="mini" @click='agreeDispatch(scope.row)'>
              通过
            </el-button>
            <el-button type="danger" icon='edit' size="mini" @click='disagreeDispatch(scope.row)'>
              打回
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <dispatchCommossionDialog v-if="dispatchDetailDialog.show" :isShow="dispatchDetailDialog.show" :dialogRow="dispatchDetailDialog.dialogRow" @closeDialog="hideDialog"></dispatchCommossionDialog>
    </div>
  </div>
</template>

<script>
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeCommissionFuzzy, approveDispatch } from '../../api/dispatch/dispatch.js'
import storage, {USER} from '../../utils/storage'
import dispatchCommossionDialog from './DispatchDetailDialog'
export default {
  name: 'dispatchcommossion',
  data () {
    return {
      idFlag: false,
      dispatchDetailDialog: {
        show: false,
        dialogRow: {}
      },
      searchData: {
        orderNo: ''
      },
      getDispatchListLoading: false,
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
      this.getDispatchList()
    },
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getDispatchList()
    },
    // 查询按钮
    getDispatchList: function () {
      this.getDispatchListLoading = true
      this.loading = true
      // 1、从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let data = {
        'userId': user.userId,
        'dispatchNo': this.searchData.orderNo,
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'companyId':"1"
      }
      describeCommissionFuzzy(data).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.getDispatchListLoading = false
        this.loading = false
      }).catch((res) => {
        this.getDispatchListLoading = false
        this.loading = false
      })
    },
    // 重置按钮
    resetSearchData: function () {
      this.searchData.orderNo = ''
    },
    // 查看操作
    dispatchDetail: function (row) {
      this.dispatchDetailDialog.show = true
      this.dispatchDetailDialog.dialogRow = {...row}
    },
    // 审批通过操作
    agreeDispatch: function (row) {
      let data = {
        'id': row.id,
        'state': '4'
      }
      approveDispatch(data).then((res) => {
        this.$message({
          message: '报损单审批成功',
          type: 'success',
          duration: 5 * 1000
        })
        this.getDispatchList()
      })
    },
    // 打回操作
    disagreeDispatch: function (row) {
      let data = {
        'id': row.id,
        'state': '5'
      }
      approveDispatch(data).then((res) => {
        this.$message({
          message: '报损单打回成功',
          type: 'success',
          duration: 5 * 1000
        })
        this.getDispatchList()
      })
    },
    // 关闭弹框
    hideDialog: function () {
      this.dispatchDetailDialog.show = false
    }
  },
  mounted () {
    // 初始化 我的 待审批出库单 数据
    this.getDispatchList()
  },
  components: {dispatchCommossionDialog, pagination}
}
</script>

<style scoped>

</style>
