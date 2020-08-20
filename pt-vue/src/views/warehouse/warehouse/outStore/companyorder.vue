<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.otheroutNo" placeholder="订单编号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getOutOrderLoading" size ="mini" icon="el-icon-search" @click='getOrderList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center'>
        <el-table-column v-if="idFlag" prop="id" label="id" align='center'></el-table-column>
        <el-table-column prop="otheroutNo" label="出库订单编号" align='center'></el-table-column>
        <el-table-column prop="warehouseName" label="出库仓库" align='center'></el-table-column>
        <el-table-column prop="creator" label="订单创建人" align='center'></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align='center'></el-table-column>
        <el-table-column prop="goodNo" label="商品编号" align='center'></el-table-column>
        <el-table-column prop="batch" label="批次" align='center'></el-table-column>
        <el-table-column prop="goodNum" label="商品数量" align='center'></el-table-column>
        <el-table-column prop="companyName" label="仓库公司名称" align='center'></el-table-column>
        <el-table-column prop="cusCompany" label="供应公司名称" align='center'></el-table-column>
        <el-table-column prop="customerId" label="客户id" align='center'></el-table-column>
        <el-table-column prop="customerName" label="客户名称" align='center'></el-table-column>
        <el-table-column prop="operation" align='center' label="操作">
          <template slot-scope='scope'>
            <el-button type="primary" icon='edit' size="mini" @click='orderDetail(scope.row)'>
              查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <otheroutCommossionDialog v-if="otheroutDetailDialog.show" :isShow="otheroutDetailDialog.show" :dialogRow="otheroutDetailDialog.dialogRow" @closeDialog="hideDialog"></otheroutCommossionDialog>
    </div>
  </div>
</template>
<script>
  import { describeFuzzy } from '../../api/outOrder/outOrder.js'
  import pagination from '@/views/warehouse/components/pagination/Index'
  import otheroutCommossionDialog from './OtheroutDetailDialog'
  import {getCompanyOrderList1} from "../../api/outOrder/outOrder";
  export default {
    name: 'companyorder',
    data () {
      return {
        idFlag: false,
        page: {
          currentPage: 0,
          total: 0,
          pageSize: 10
        },
        tableData: [],
        loading: false,
        searchData: {
          otheroutNo: ''
        },
        getOutOrderLoading: false,
        otheroutDetailDialog: {
          show: false,
          dialogRow: {}
        }
      }
    },
    methods: {
      getCompanyOrder: function () {
        let data = {
          'otherinNo': this.searchData.otherinNo,
          'pageSize': this.page.pageSize,
          'currentPage': this.page.currentPage,
          'commodityName': this.searchData.commodityName
        }
        getCompanyOrderList1(data).then((res) => {
          this.tableData = res.list
          this.page.total = res.total
          this.loading = false
        })
      },



      handleCurrentChange: function (val) {
        console.log('handleCurrentChange', val)
        this.page.currentPage = val
        this.getOrderList()
      },
      resetSearchData: function () {
        this.searchData.otheroutNo = ''
      },
      // 每页显示多少条
      handleSizeChange: function (val) {
        console.log('handleSizeChange', val)
        this.page.pageSize = val
        this.getOrderList()
      },
      hideDialog: function () {
        this.otheroutDetailDialog.show = false
      },
      getOrderList: function () {
        let data = {
          'otheroutNo': this.searchData.otheroutNo,
          'pageSize': this.page.pageSize,
          'currentPage': this.page.currentPage,
          'commodityName': this.searchData.commodityName
        }
        describeFuzzy(data).then((res) => {
          this.tableData = res.list
          this.page.total = res.total
          this.loading = false
        })
      },
      orderDetail: function (row) {
        this.otheroutDetailDialog.show = true
        this.otheroutDetailDialog.dialogRow = {...row}
      }
    },
    created () {
      this.getCompanyOrder()
    },
    components: {pagination, otheroutCommossionDialog}
  }
</script>

<style scoped>

</style>
