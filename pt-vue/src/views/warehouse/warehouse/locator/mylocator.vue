<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.name" placeholder="货架名称"></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-select v-model="searchData.warehouseId" clearable placeholder="请选择所属仓库">
            <el-option v-for="item in warehouseOptions" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getLocatorListLoading" size ="mini" icon="el-icon-search" @click='getLocatorList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
        <el-form-item class="btnRight">
          <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delLocator()' :disabled="searchBtnDisabled">删除</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addLocator()'>添加</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center' @selection-change="handleSelectionChange">
        <el-table-column v-if="idFlag" prop="id" label="id" align='center'></el-table-column>
        <el-table-column type="selection" align='center'></el-table-column>
        <el-table-column prop="name" label="货位名称" align='center'></el-table-column>
        <el-table-column prop="code" label="货位编码" align='center'></el-table-column>
        <el-table-column prop="warehouseName" label="所属仓库" align='center'></el-table-column>
        <el-table-column prop="shelvesName" label="所属货架" align='center'></el-table-column>
        <el-table-column prop="length" label="货位长度(cm)" align='center'></el-table-column>
        <el-table-column prop="width" label="货位宽度(cm)" align='center'></el-table-column>
        <el-table-column prop="height" label="货位高度(cm)" align='center'></el-table-column>
        <el-table-column prop="weight" label="最大载重(kg)" align='center'></el-table-column>
        <el-table-column prop="type" label="货位信息" align='center' width="100px">
          <template slot-scope="scope">
            <p style="color: #409EFF" v-if="scope.row.type.indexOf('2') !== -1">
              可入库
            </p>
            <br/>
            <p style="color: #67C23A" v-if="scope.row.type.indexOf('7') !== -1">
              可出库
            </p>
          </template>
        </el-table-column>
        <el-table-column prop="state" label="货位状态" align='center' width="100px">
          <template slot-scope="scope">
            <p style="color: #409EFF" v-if="scope.row.state === '2'">
              已满
            </p>
            <p style="color: #67C23A" v-else-if="scope.row.state === '0'">
              可用
            </p>
            <p style="color: #67C23A" v-else-if="scope.row.state === '3'">
              预占用
            </p>
          </template>
        </el-table-column>
        <el-table-column prop="operation" align='center' label="操作">
          <template slot-scope='scope'>
            <el-button type="warning" icon='edit' size="mini" @click='onEditLocator(scope.row)'>
              编辑
            </el-button>
            <el-button type="success" icon='edit' size="mini" @click='onDetailLocator(scope.row)'>
              详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <locatorFunctionDialog  v-if="addLocatorDialog.show" :isShow="addLocatorDialog.show" :dialogRow="addLocatorDialog.dialogRow"  @closeDialog="hideAddLocatorDialog" @refresh="getLocatorList"></locatorFunctionDialog>
      <locatorDetailDialog v-if="locatorDetailDialog.show" :isShow="locatorDetailDialog.show" :dialogRow="locatorDetailDialog.dialogRow" @closeDialog="hideAddLocatorDialog"> </locatorDetailDialog>
    </div>
  </div>
</template>

<script>
import storage, {USER} from '../../utils/storage'
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeFuzzyWithWarehouseName, deleteLocatorByPrimaryKeys } from '../../api/locator/index.js'
import { getAllStoreByPrincipalId } from '../../api/warehouse/index.js'
import locatorFunctionDialog from './LocatorFunctionDialog'
import LocatorDetailDialog from "./LocatorDetailDialog";
export default {
  name: 'mylocator',
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
        name: '',
        warehouseId: ''
      },
      searchBtnDisabled: true,
      havaSelectedData: [],
      addLocatorDialog: {
        show: false,
        dialogRow: {}
      },
      locatorDetailDialog: {
        show: false,
        dialogRow: {}
      },
      getLocatorListLoading: false,
      warehouseOptions: []
    }
  },
  methods: {
    addLocator: function () {
      // 把新增基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_LOCATOR_FUNCTION_DIALOG_TPYE', 'add')
      this.addLocatorDialog.show = true
    },
    hideAddLocatorDialog: function () {
      this.addLocatorDialog.show = false
    },
    // 查询
    getLocatorList: function () {
      this.loading = true
      this.getLocatorListLoading = true
      // 从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let queryData = {
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'name': this.searchData.name,
        'warehouseId': this.searchData.warehouseId,
        'createUserId': user.userId,
        'shelvesId': '',
        'companyId':"1"
      }
      describeFuzzyWithWarehouseName(queryData).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.loading = false
        this.getLocatorListLoading = false
      }).catch((res) => {
        this.loading = false
      })
    },
    // 重置
    resetSearchData: function () {
      this.searchData.name = ''
      this.searchData.warehouseId = ''
    },
    // 删除
    delLocator: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteLocatorByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getLocatorList()
      }).catch((res) => {
      })
    },
    // 修改
    onEditLocator: function (row) {
      this.addLocatorDialog.dialogRow = {...row}
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_LOCATOR_FUNCTION_DIALOG_TPYE', 'edit')
      this.addLocatorDialog.show = true
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
      this.getLocatorList()
    },
    // 每页显示多少条
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getLocatorList()
    },
    initWarehouseOptions: function () {
      // 从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      getAllStoreByPrincipalId(user.userId).then((res) => {
        this.warehouseOptions = res
      })
    },
    onDetailLocator: function (row) {
      this.locatorDetailDialog.dialogRow = {...row}
      this.locatorDetailDialog.show=true
    }
  },
  mounted: function () {
    this.getLocatorList()
    this.initWarehouseOptions()
  },
  components: {LocatorDetailDialog, pagination, locatorFunctionDialog}
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
