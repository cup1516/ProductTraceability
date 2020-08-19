<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.shelvesName" placeholder="货架名称"></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-select v-model="searchData.warehouseId" clearable placeholder="请选择所属仓库">
            <el-option v-for="item in warehouseOptions" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getShelvesListLoading" size ="mini" icon="el-icon-search" @click='getShelvesList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
        <el-form-item class="btnRight">
          <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delShelves()' :disabled="searchBtnDisabled">删除</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addShelves()'>添加</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center' @selection-change="handleSelectionChange">
        <el-table-column v-if="idFlag" prop="id" label="id" align='center'></el-table-column>
        <el-table-column type="selection" align='center'></el-table-column>
        <el-table-column prop="shelvesName" label="货架名称" align='center'></el-table-column>
        <el-table-column prop="warehouseName" label="所属仓库" align='center'></el-table-column>
        <el-table-column prop="createUserName" label="创建人" align='center'></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align='center'></el-table-column>
        <el-table-column prop="updateUserName" label="修改人" align='center'></el-table-column>
        <el-table-column prop="updateTime" label="修改时间" align='center'></el-table-column>
        <el-table-column prop="status" label="货架状态" align='center'>
          <template slot-scope="scope">
            <p style="color: #409EFF" v-if="scope.row.status === 1">
              货架未满
            </p>
            <p style="color: #67C23A" v-else-if="scope.row.status === 0">
              货架为空
            </p>
            <p style="color: #F56C6C" v-else-if="scope.row.status === 2">
              货架已满
            </p>
          </template>
        </el-table-column>
        <el-table-column prop="operation" align='center' label="操作">
          <template slot-scope='scope'>
            <el-button type="warning" icon='edit' size="mini" @click='onEditShelves(scope.row)'>
              编辑
            </el-button>
            <el-button type="success" icon='edit' size="mini" @click='onDetailShelves(scope.row)'>
              详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <shelvesFunctionDialog  v-if="addShelvesDialog.show" :isShow="addShelvesDialog.show" :dialogRow="addShelvesDialog.dialogRow"  @closeDialog="hideAddShelvesDialog" @refresh="getShelvesList"></shelvesFunctionDialog>
      <shelfDetailDialog v-if="shelfDetailDialog.show" :isShow="shelfDetailDialog.show" :dialogRow="shelfDetailDialog.dialogRow" @closeDialog="hideAddShelvesDialog"> </shelfDetailDialog>

    </div>
  </div>
</template>

<script>
import storage, {USER} from '../../utils/storage'
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeFuzzy, deleteShelvesByPrimaryKeys } from '../../api/shelves/index.js'
import { getAllStoreByPrincipalId } from '../../api/warehouse/index.js'
import shelvesFunctionDialog from './ShelvesFunctionDialog'
import shelfDetailDialog from './shelfDetailDiglog'
export default {
  name: 'myshelves',
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
        shelvesName: '',
        warehouseId: ''
      },
      searchBtnDisabled: true,
      havaSelectedData: [],
      addShelvesDialog: {
        show: false,
        dialogRow: {}
      },
      shelfDetailDialog: {
        show: false,
        dialogRow: {}
      },
      getShelvesListLoading: false,
      warehouseOptions: []
    }
  },
  methods: {
    addShelves: function () {
      // 把新增基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_SHELVES_FUNCTION_DIALOG_TPYE', 'add')
      this.addShelvesDialog.show = true
    },
    hideAddShelvesDialog: function () {
      this.addShelvesDialog.show = false
    },
    // 查询
    getShelvesList: function () {
      this.loading = true
      this.getShelvesListLoading = true
      // 从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let queryData = {
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'shelvesName': this.searchData.shelvesName,
        'warehouseId': this.searchData.warehouseId,
        'createUserId': user.userId,
        'companyId':"1"
      }
      describeFuzzy(queryData).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.loading = false
        this.getShelvesListLoading = false
      }).catch((res) => {
        this.loading = false
      })
    },
    // 重置
    resetSearchData: function () {
      this.searchData.shelvesName = ''
      this.searchData.warehouseId = ''
    },
    // 删除
    delShelves: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteShelvesByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getShelvesList()
      }).catch((res) => {
      })
    },
    // 修改
    onEditShelves: function (row) {
      this.addShelvesDialog.dialogRow = {...row}
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_SHELVES_FUNCTION_DIALOG_TPYE', 'edit')
      this.addShelvesDialog.show = true
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
      this.getShelvesList()
    },
    // 每页显示多少条
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getShelvesList()
    },
    initWarehouseOptions: function () {
      // 从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let data ={
        'userId':user.userId,
        'companyId':"4"
      }
      getAllStoreByPrincipalId(data).then((res) => {
        this.warehouseOptions = res
      })
    },
    onDetailShelves: function (row) {
      this.shelfDetailDialog.dialogRow = {...row}
      this.shelfDetailDialog.show=true
      console.log('查看详情')
    }
  },
  mounted: function () {
    this.getShelvesList()
    this.initWarehouseOptions()
  },
  components: {pagination, shelvesFunctionDialog,shelfDetailDialog}
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
