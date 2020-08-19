<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="form" class="demo-form-inline search-form">
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
        <el-table-column prop="state" label="状态" align='center'>
          <template slot-scope="scope">
            <p style="color: #409EFF" v-if="scope.row.state === '0'">
              待审核
            </p>
            <p style="color: #67C23A" v-else-if="scope.row.state === '4'">
              审核通过
            </p>
            <p style="color: #F56C6C" v-else-if="scope.row.state === '5'">
              审核不通过
            </p>
            <p v-else-if="scope.row.state === '3'">
              待提交
            </p>
          </template>
        </el-table-column>
        <el-table-column prop="operation" align='center' label="操作">
          <template slot-scope='scope'>
            <el-button type="primary" icon='edit' size="mini" @click='dispatchDetail(scope.row)'>
              查看
            </el-button>
            <el-button v-if="scope.row.state === '0'" type="danger" icon='edit' size="mini" @click='recallDispatch(scope.row)'>
              撤回
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <!-- 详情弹框 -->

      <dispatchDetailDialog v-if="dispatchDetailDialog.show" :isShow="dispatchDetailDialog.show" :dialogRow="dispatchDetailDialog.dialogRow" @closeDialog="hideDialog"></dispatchDetailDialog>

    </div>
  </div>
</template>

<script>
import pagination from '@/views/warehouse/components/pagination/Index'
import dispatchDetailDialog from './DispatchDetailDialog'
import { describeMineFuzzy, updateDispatchByPrimaryKey } from '../../api/dispatch/dispatch.js'
import storage, {USER} from '../../utils/storage'
export default {
  name: 'MyDispatch',
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
    // 查询数据
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
      describeMineFuzzy(data).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.getDispatchListLoading = false
        this.loading = false
      }).catch((res) => {
        this.getDispatchListLoading = false
        this.loading = false
      })
    },
    // 页数改变
    handleCurrentChange: function (val) {
      this.page.currentPage = val
      this.getDispatchList()
    },
    // pageSize改变
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getDispatchList()
    },
    // 重置查询输入框
    resetSearchData: function () {
      this.searchData.orderNo = ''
    },
    // 撤回
    recallDispatch: function (row) {
      // 更新报损单状态信息
      let data = {
        'id': row.id,
        'state': '3'
      }
      updateDispatchByPrimaryKey(data).then((res) => {
        this.$message({
          message: '撤回报损单成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 更新页面数据
        this.getDispatchList()
      })
    },
    // 详情弹框
    dispatchDetail: function (row) {
      this.dispatchDetailDialog.show = true
      this.dispatchDetailDialog.dialogRow = {...row}
    },
    // 关闭详情弹框
    hideDialog: function () {
      this.dispatchDetailDialog.show = false
    }
  },
  mounted () {
    // 初始化 我的 提交的报损单 数据
    this.getDispatchList()
  },
  components: { pagination, dispatchDetailDialog }
}
</script>

<style scoped>

</style>
