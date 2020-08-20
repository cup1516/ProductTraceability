<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.orderNo" placeholder="调拨单编号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getAllocationListLoading" size ="mini" icon="el-icon-search" @click='getAllocationList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center'>
        <el-table-column v-if="idFlag" prop="id" label="id" align='center'></el-table-column>
        <el-table-column prop="allocationNo" label="调拨单单编号" align='center'></el-table-column>
        <el-table-column prop="bwarehouseName" label="出库仓库" align='center'></el-table-column>
        <el-table-column prop="awarehouseName" label="入库仓库" align="center"></el-table-column>
        <el-table-column prop="creator" label="调拨单创建人" align='center'></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align='center'></el-table-column>
        <el-table-column prop="updateTime" label="发起时间" align='center'></el-table-column>
        <el-table-column prop="auditor" label="下一步审批人" align='center'></el-table-column>
        <el-table-column prop="operation" align='center' label="操作">
          <template slot-scope='scope'>
            <el-button type="primary" icon='edit' size="mini" @click='allocationDetail(scope.row)'>
              查看
            </el-button>
            <el-button type="success" icon='edit' size="mini" @click='agreeAllocation(scope.row)'>
              通过
            </el-button>
            <el-button type="danger" icon='edit' size="mini" @click='disagreeAllocation(scope.row)'>
              打回
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <!--
        <otheroutCommossionDialog v-if="allocationDetailDialog.show" :isShow="allocationDetailDialog.show" :dialogRow="allocationDetailDialog.dialogRow" @closeDialog="hideDialog"></otheroutCommossionDialog>
      -->
    </div>
  </div>
</template>

<script>
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeCommissionFuzzy, approveAllocation } from '../../api/allocation/index.js'
import storage, {USER} from '../../utils/storage'
export default {
  name: 'allocationcommossion',
  data () {
    return {
      idFlag: false,
      allocationDetailDialog: {
        show: false,
        dialogRow: {}
      },
      searchData: {
        orderNo: ''
      },
      getAllocationListLoading: false,
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
      this.getAllocationList()
    },
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getAllocationList()
    },
    // 查询按钮
    getAllocationList: function () {
      this.getAllocationListLoading = true
      this.loading = true
      // 1、从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let data = {
        'userId': user.userId,
        'state': 0,
        'allocationNo': this.searchData.orderNo,
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'companyId':"1"
      }
      describeCommissionFuzzy(data).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.getAllocationListLoading = false
        this.loading = false
      }).catch((res) => {
        this.getAllocationListLoading = false
        this.loading = false
      })
    },
    // 重置按钮
    resetSearchData: function () {
      this.searchData.orderNo = ''
    },
    // 查看操作
    allocationDetail: function (row) {
      this.allocationDetailDialog.show = true
      this.allocationDetailDialog.dialogRow = {...row}
    },
    // 审批通过操作
    agreeAllocation: function (row) {
      let data = {
        'id': row.id,
        'state': 4
      }
      approveAllocation(data).then((res) => {
        this.$message({
          message: '调拨单审批成功',
          type: 'success',
          duration: 5 * 1000
        })
        this.getAllocationList()
      })
    },
    // 打回操作
    disagreeAllocation: function (row) {
      let data = {
        'id': row.id,
        'state': 5
      }
      approveAllocation(data).then((res) => {
        this.$message({
          message: '调拨单打回成功',
          type: 'success',
          duration: 5 * 1000
        })
        this.getAllocationList()
      })
    },
    // 关闭弹框
    hideDialog: function () {
      this.allocationDetailDialog.show = false
    }
  },
  mounted () {
    // 初始化 我的 待审批出库单 数据
    this.getAllocationList()
  },
  components: {pagination}
}
</script>

<style scoped>

</style>
