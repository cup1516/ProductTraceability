<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' :rules="rules" ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.name" placeholder="仓库名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size ="mini" icon="el-icon-search" @click='getStoreList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>

        <el-form-item class="btnRight">
          <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delStore()' :disabled="searchBtnDisabled">删除</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addStore()'>添加</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center' @selection-change="handleSelectionChange">
        <el-table-column v-if="idFlag" prop="id" label="id" align='center' width="180">
        </el-table-column>
        <el-table-column type="selection" align='center' width="60">
        </el-table-column>
        <el-table-column prop="name" label="仓库名称" align='center' width="80">
        </el-table-column>
        <el-table-column prop="alias" label="仓库简称" align='center'>
        </el-table-column>
        <el-table-column prop="address" label="仓库地址" align='center'>
        </el-table-column>
        <el-table-column prop="remark" label="备注" align='center'>
        </el-table-column>
        <el-table-column prop="creator" label="创建人" align='center'>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align='center'>
        </el-table-column>

        <el-table-column prop="updator" label="修改人" align='center'>
        </el-table-column>

        <el-table-column prop="updateTime" label="修改时间" align='center'>
        </el-table-column>
        <el-table-column prop="operation" align='center' label="操作" width="180">
          <template slot-scope='scope'>
            <el-button type="warning" size="mini" @click='onEditStore(scope.row)'>
              编辑
            </el-button>
            <el-button type="success" size="mini" @click='onDetailStore(scope.row)'>
              详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <StoreFunctionDialog  v-if="addStoreDialog.show" :isShow="addStoreDialog.show" :dialogRow="addStoreDialog.dialogRow"  @closeDialog="hideAddStoreDialog" @refresh="getStoreList"></StoreFunctionDialog>
    </div>
  </div>
</template>

<script>
// storage
import storage, {USER} from '../../utils/storage'
import pagination from '@/views/warehouse/components/pagination/Index'
import StoreFunctionDialog from './StoreFunctionDialog'
import { describeFuzzy, deleteStoreByPrimaryKeys } from '../../api/store/index.js'
export default {
  name: 'mystore',
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
        name: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
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
    addStore: function () {
      // 把新增基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_FUNCTION_DIALOG_TPYE', 'add')
      this.addStoreDialog.show = true
    },
    hideAddStoreDialog: function () {
      this.addStoreDialog.show = false
    },
    // 查询
    getStoreList: function () {
      // 从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let queryData = {
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'name': this.searchData.name,
        // 从vuex 中获取到 用户的Id
        // 'leaderId': this.$store.getters.id
        'leaderId': user.userId,
        'companyId':"1"
      }
      describeFuzzy(queryData).then((res) => {
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
    delStore: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteStoreByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getStoreList()
      }).catch((res) => {
      })
    },
    // 修改
    onEditStore: function (row) {
      console.log(row)
      this.addStoreDialog.dialogRow = {...row}
      console.log(this.addStoreDialog.dialogRow + '88887')
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_FUNCTION_DIALOG_TPYE', 'edit')
      this.addStoreDialog.show = true
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
      console.log('handleCurrentChange', val)
      this.page.currentPage = val
      this.getStoreList()
    },
    // 每页显示多少条
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getStoreList()
    },
    onDetailStore: function (val) {
      console.log('查看详情')
    }
  },
  created: function () {
    this.getStoreList()
  },
  components: {pagination, StoreFunctionDialog}
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
