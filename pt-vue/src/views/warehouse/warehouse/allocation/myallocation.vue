<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <!--搜索开发模块-->
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.createTime" placeholder="调拨时间"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getAllocationListLoading" size ="mini" icon="el-icon-search" @click='getAllocationList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
        <el-form-item class="btnRight">
          <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delAllocation()' :disabled="searchBtnDisabled">删除</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addAllocation()'>添加</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--调拨单展示模块-->
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='left' @selection-change="handleSelectionChange">
        <el-table-column v-if="idFlag" prop="id" label="id" align='center' width="10"></el-table-column>
        <el-table-column type="selection" align='center' width="40"></el-table-column>
        <el-table-column prop="allocationNo" label="调拨单号" align="center" width="160"></el-table-column>
        <el-table-column prop="bwarehouseName" label="原仓库名称" align="center" width="100"></el-table-column>
        <el-table-column prop="awarehouseName" label="目标仓库名称" align="center" width="120"></el-table-column>
        <el-table-column prop="state" label="审核状态" align="center" width="80">
          <template slot-scope='scope'>
            <p style="color: #409EFF" v-if="scope.row.state === '0'">
              待审核
            </p>
            <p style="color: #67C23A" v-else-if="scope.row.state === '4'">
              审核通过
            </p>
            <p style="color: #F56C6C" v-else-if="scope.row.state === '5'">
              审核失败
            </p>
            <p style="color: #F56C6C" v-else-if="scope.row.state === '3'">
              未提交
            </p>
          </template>
        </el-table-column>
        <el-table-column prop="creator" label="创建人" align="center" width="110"></el-table-column>
        <el-table-column prop="createTime" label="创 建 时 间" align="center" width="170"></el-table-column>
        <el-table-column prop="updator" label="修改者" align="center" width="110"></el-table-column>
        <el-table-column prop="updateTime" label="修 改 时 间" align="center" width="170"></el-table-column>
        <el-table-column prop="auditor" label="审核人" align="center" width="110"></el-table-column>
        <el-table-column prop="auditTime" label="审 核 时 间" align="center" width="170"></el-table-column>
        <el-table-column prop="operation" align='center' label="操作" width="180" fixed="right">
          <template slot-scope='scope'>
            <el-row>
              <el-button type="primary" icon='edit' size="mini" @click='allocationDetail(scope.row)'>
                查看
              </el-button>
              <el-button v-if="scope.row.state === '1'" type="danger" icon='edit' size="mini" @click='recallAllocation(scope.row)'>
                撤回
              </el-button>
            </el-row>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <allocationDetailFunctionDialog v-if="allocationDetailDialog.show" :isShow="allocationDetailDialog.show" :dialogRow="allocationDetailDialog.dialogRow" :closeDialog="hideDialog"></allocationDetailFunctionDialog>
    </div>
  </div>
</template>

<script>
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeFuzzy, deleteAllocationByPrimaryKeys } from '../../api/allocation/index.js'
import allocationDetailFunctionDialog from './AllocationDetailFunctionDialog.vue'
export default {
  name: 'myallocation',
  data () {
    return {
      loading: true,
      tableData: [],
      idFlag: false,
      page: {
        currentPage: 0,
        total: 0,
        pageSize: 10
      },
      searchData: {
        createTime: ''
      },
      searchBtnDisabled: true,
      havaSelectedData: [],
      allocationDetailDialog: {
        show: false,
        dialogRow: {}
      },
      getAllocationListLoading: false
    }
  },
  methods: {
    // 查询
    getAllocationList: function () {
      let queryData = {
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'companyId':"1"
      }
      describeFuzzy(queryData).then((res) => {
        this.tableData = res.list
        console('tableData', this.tableData)
        this.page.total = res.total
        this.loading = false
      }).catch((res) => {
        this.loading = false
      })
    },
    // 重置
    resetSearchData: function () {
      this.searchData.createTime = ''
    },
    // 删除
    delAllocation: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteAllocationByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getAllocationList()
      }).catch((res) => {
      })
    },
    // 点了多选框就会触发这个函数，获取选中的值
    handleSelectionChange: function (val) {
      if (val.length > 0) {
        // 只有 有选中的框 则删除 按钮可点
        this.searchBtnDisabled = false
        // 将val 赋给选中的中的数据，以备删除
        this.havaSelectedData = val
      } else {
        // 没有有选中的框 则删除 按钮不可点
        this.searchBtnDisabled = true
        // 将val 赋给选中的中的数据，以备删除
        this.havaSelectedData = val
      }
    },
    // 上下分页
    handleCurrentChange: function (val) {
      this.page.currentPage = val
      this.getAllocationList()
    },
    // 每页显示多少条
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getAllocationList()
    },
    // 查看弹框
    allocationDetail: function (row) {
      this.allocationDetailDialog.show = true
      this.allocationDetailDialog.dialogRow = {...row}
    },
    // 关闭弹框
    hideDialog: function (row) {
      this.addAllocation.show = false
    },
    // 撤回
    deleteAllocationByPrimaryKey: function (data) {
      deleteAllocationByPrimaryKeys(data).then((res) => {
        this.$message({
          message: '撤回入功',
          type: 'success',
          duration: 5 * 1000
        })
        // 更新页面数据
        this.getAllocationList()
      })
    }
  },
  created: function () {
    this.getAllocationList()
  },
  components: { pagination, allocationDetailFunctionDialog }
}
</script>

<style scoped>
  /* 这个是为了解决前面样式覆盖之后伪类带出来的竖线 */
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
