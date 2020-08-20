<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.name" placeholder="商品名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size ="mini" icon="el-icon-search" @click='getGoodsList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
        <el-form-item class="btnRight">
          <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delGoods()' :disabled="searchBtnDisabled">删除</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addGoods()'>添加</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center' @selection-change="handleSelectionChange">
        <el-table-column v-if="idFlag" prop="id" label="id" align='center' width="180">
        </el-table-column>
        <el-table-column type="selection" align='center' width="60">
        </el-table-column>
        <el-table-column prop="goodNo" label="商品编号" align='center' width="80">
        </el-table-column>
        <el-table-column prop="goodName" label="名称" align='center' width="80">
        </el-table-column>
        <el-table-column prop="goodType" label="类型" align='center'>
        </el-table-column>

        <el-table-column prop="unit" label="单位" align='center'>
        </el-table-column>
        <el-table-column prop="length" label="长度" align='center'>
        </el-table-column>
        <el-table-column prop="width" label="宽度" align='center'>
        </el-table-column>
        <el-table-column prop="heigth" label="高度" align='center'>
        </el-table-column>
        <el-table-column prop="weigth" label="重量" align='center'>
        </el-table-column>

        <el-table-column prop="createUserName" label="创建人" align='center'>
        </el-table-column>

        <el-table-column prop="createDate" label="创建时间" align='center'>
        </el-table-column>
        <el-table-column prop="modifiedUserName" label="修改人" align='center'>
        </el-table-column>

        <el-table-column prop="modifiedDate" label="修改时间" align='center'>
        </el-table-column>

        <el-table-column prop="status" label="状态" align='center'>
        </el-table-column>

        <el-table-column prop="operation" align='center' label="操作" width="180">
          <template slot-scope='scope'>
            <el-button type="warning" icon='edit' size="mini" @click='onEditGoods(scope.row)'>
              编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <addGoodsDialog  v-if="addGoodsDialog.show" :isShow="addGoodsDialog.show" :dialogRow="addGoodsDialog.dialogRow"  @closeDialog="hideAddGoodsDialog" @refresh="getGoodsList"></addGoodsDialog>
    </div>
  </div>
</template>

<script>
import pagination from '@/views/warehouse/components/pagination/Index'
import addGoodsDialog from './AddGoodsDialog'
import { describeFuzzyGoods, deleteGoodsByPrimaryKeys } from '../../api/goods/index.js'
export default {
  name: 'mygoods',
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
      searchBtnDisabled: true,
      havaSelectedData: [],
      addGoodsDialog: {
        show: false,
        dialogRow: {}
      }
    }
  },
  methods: {
    addGoods: function () {
      // 把新增基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_FUNCTION_DIALOG_TPYE', 'add')
      this.addGoodsDialog.show = true
    },
    hideAddGoodsDialog: function () {
      this.addGoodsDialog.show = false
    },
    // 查询
    getGoodsList: function () {
      let queryData = {
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'goodsName': this.searchData.name,
        'companyId':'1'
      }
      describeFuzzyGoods(queryData).then((res) => {
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
    delGoods: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteGoodsByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getGoodsList()
      }).catch((res) => {
      })
    },
    onEditGoods: function (row) {
      this.addGoodsDialog.dialogRow = {...row}
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_FUNCTION_DIALOG_TPYE', 'edit')
      this.addGoodsDialog.show = true
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
      this.getGoodsList()
    },
    // 每页显示多少条
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getGoodsList()
    }
  },
  created: function () {
    this.getGoodsList()
  },
  components: {pagination, addGoodsDialog}
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
