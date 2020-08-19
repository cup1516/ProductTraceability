<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' :rules="rules" ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.name" placeholder="输入操作人名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size ="mini" icon="el-icon-search" @click='getLogList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center' @selection-change="handleSelectionChange">
        <el-table-column v-if="id" prop="id" label="id" align='center' width="180">
        </el-table-column>
        <el-table-column prop="project" label="服务名" align='center' width="180">
        </el-table-column>
        <el-table-column prop="executeMethod" label="执行方法" align='center'>
        </el-table-column>
        <el-table-column prop="beginTime" label="开始时间" align='center'>
        </el-table-column>
        <el-table-column prop="endTime" label="结束时间" align='center'>
        </el-table-column>
        <el-table-column prop="consumingTime" label="花费时间" align='center'>
        </el-table-column>
        <el-table-column prop="userName" label="访问人" align='center'>
        </el-table-column>
        <el-table-column prop="ipAddress" label="访问地址" align='center'>
        </el-table-column>


      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>

    </div>
  </div>
</template>

<script>
  // storage
  import storage, {USER} from '../../utils/storage'
  import pagination from '@/views/warehouse/components/pagination/Index'
  import { getLogList } from '../../api/store/index.js'
  export default {
    name: 'storeLog',
    data () {
      return {
        loading: true,
        tableData: [],
        id: false,
        page: {
          currentPage: 0,
          total: 0,
          pageSize: 10
        },
        searchData: {
          name: ''
        },
        rules: {
          name: [
            { required: true, message: '请输入日志', trigger: 'blur' }
          ]
        },
        searchBtnDisabled: true,
        havaSelectedData: [],
        addStoreDialog: {
          show: false,
          dialogRow: {}
        }
      }
    },
    methods: {
      // 查询
      getLogList: function () {
        // 从sessionStorage 中 获取 user 对象
        let queryData = {
          'pageSize': this.page.pageSize,
          'currentPage': this.page.currentPage,
          'userName': this.searchData.name,
          // 从vuex 中获取到 用户的Id
          // 'leaderId': this.$store.getters.id
          'companyId':"1"
        }
        getLogList(queryData).then((res) => {
          this.tableData = res.list
          this.page.total = res.total
          this.loading = false
        }).catch((res) => {
          this.loading = false
        })
      },
      // 重置
      resetSearchData: function () {
        this.searchData.name = ''
      },
      // 删除
      // 修改
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
        console.log('handleCurrentChange', val)
        this.page.currentPage = val
        this.getLogList()
      },
      // 每页显示多少条
      handleSizeChange: function (val) {
        this.page.pageSize = val
        this.getLogList()
      },
      onDetailStore: function (val) {
        console.log('查看详情')
      }
    },
    created: function () {
      this.getLogList()
    },
    components: {pagination}
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
