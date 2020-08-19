<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.orderNo" placeholder="出库单编号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getOtherinListLoading" size ="mini" icon="el-icon-search" @click='getOtherinList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center'>
        <el-table-column v-if="idFlag" prop="id" label="id" align='center'></el-table-column>
        <el-table-column prop="otherinNo" label="入库单编号" align='center'></el-table-column>
        <el-table-column prop="warehouseName" label="出库仓库" align='center'></el-table-column>
        <el-table-column prop="creator" label="出库单创建人" align='center'></el-table-column>
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
            <el-button type="primary" icon='edit' size="mini" @click='otherinDetail(scope.row)'>
              查看
            </el-button>
            <el-button v-if="scope.row.state === '0'" type="danger" icon='edit' size="mini" @click='recallOtherin(scope.row)'>
              撤回
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <otherinCommossionDialog v-if="otherinDetailDialog.show" :isShow="otherinDetailDialog.show" :dialogRow="otherinDetailDialog.dialogRow" @closeDialog="hideDialog"></otherinCommossionDialog>
    </div>
  </div>
</template>

<script>
import storage, {USER} from '../../utils/storage'
import pagination from '@/views/warehouse/components/pagination/Index'
import { describeMineFuzzy, updateOtherinByPrimaryKey } from '../../api/inStore/otherin.js'
import { deleteInStoreByPrimaryKeys } from '../../api/inStore/index.js'
import otherinCommossionDialog from './OtherinDetailDialog'
export default {
  name: 'myinstore',
  data () {
    return {
      loading: true,
      tableData: [],
      idFlag: false,
      itemId: '',
      page: {
        currentPage: 0,
        total: 0,
        pageSize: 10
      },
      otherinDetailDialog: {
        show: false,
        dialogRow: {}
      },
      searchData: {
        otherinNo: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ]
      },
      searchBtnDisabled: true,
      havaSelectedData: [],
      getOtherinListLoading: false,
      addInStoreDialog: {
        show: false,
        dialogRow: {}
      }
    }
  },
  methods: {
    // 查看弹框
    otherinDetail: function (row) {
      this.otherinDetailDialog.show = true
      this.otherinDetailDialog.dialogRow = {...row}
    },
    // 关闭弹框
    hideDialog: function () {
      this.otherinDetailDialog.show = false
    },

    recallOtherin: function (row) {
      // 更新出库单状态信息
      let data = {
        'id': row.id,
        'state': '3'
      }
      updateOtherinByPrimaryKey(data).then((res) => {
        this.$message({
          message: '撤回入库单成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 更新页面数据
        this.getInStoreList()
      })
    },

    // 查询
    getInStoreList: function () {
      // 从sessionStorage 中 获取 user 对象
      this.getOtherinListLoading = true
      this.loading = true
      // 1、从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let data = {
        'userId': user.userId,
        'otherinNo': this.searchData.orderNo,
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'companyId':"1"
      }
      describeMineFuzzy(data).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.getOtherinListLoading = false
        this.loading = false
      }).catch((res) => {
        this.getOtherinListLoading = false
        this.loading = false
      })
    },
    // 重置
    resetSearchData: function () {
      this.searchData.name = ''
    },
    // 删除
    delInStore: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteInStoreByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getInStoreList()
      }).catch((res) => {
      })
    },
    // 修改
    onEditInStore: function (row) {
      console.log(row)
      this.addInStoreDialog.dialogRow = {...row}
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_FUNCTION_DIALOG_TPYE', 'edit')
      this.addInStoreDialog.show = true
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
      this.getInStoreList()
    },
    // 每页显示多少条
    handleSizeChange: function (val) {
      console.log('handleSizeChange', val)
      this.page.pageSize = val
      this.getInStoreList()
    }},

  created: function () {
    this.getInStoreList()
  },
  components: {pagination, otherinCommossionDialog}

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
