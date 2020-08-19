<template>
  <div class="fillcontain">
    <div class="search_container searchArea">
      <el-form :inline="true" :model='searchData' ref="search_data" class="demo-form-inline search-form">
        <el-form-item label="">
          <el-input v-model="searchData.name" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="getEmployeeListLoading" size ="mini" icon="el-icon-search" @click='getEmployeeList()'>筛选</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
        </el-form-item>
        <el-form-item class="btnRight">
          <el-button type="primary" size ="mini" icon="el-icon-delete" @click='delEmployee()' :disabled="searchBtnDisabled">删除</el-button>
          <el-button type="primary" size ="mini" icon="el-icon-plus" @click='addEmployee()'>添加</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container">
      <el-table v-loading="loading" :data="tableData" style="width: 100%" align='center' @selection-change="handleSelectionChange">
        <el-table-column v-if="idFlag" prop="id" label="id" align='center' width="180"></el-table-column>
        <el-table-column type="selection" align='center' width="60"></el-table-column>
        <el-table-column prop="employeeName" label="职工姓名" align='center' width="80"></el-table-column>
        <el-table-column prop="sex" label="性别" align='center'>
          <template slot-scope="scope">
            {{ scope.row.sex === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="nation" label="民族" align='center'></el-table-column>
        <el-table-column prop="telephone" label="电话号码" align='center'></el-table-column>
        <el-table-column prop="birthday" label="出生日期" align='center'></el-table-column>
        <el-table-column prop="isMerried" label="是否已婚" align='center'>
          <template slot-scope="scope">
            {{ scope.row.isMerried === 1 ? '已婚' : '未婚' }}
          </template>
        </el-table-column>

        <el-table-column prop="addr" label="家庭住址" align='center'></el-table-column>
        <el-table-column prop="department" label="所属部门" align='center'></el-table-column>

        <el-table-column prop="inTime" label="入职日期" align='center'></el-table-column>
        <el-table-column prop="operation" align='center' label="操作" width="180">
          <template slot-scope='scope'>
            <el-button type="warning" icon='edit' size="mini" @click='onEditEmployee(scope.row)'>
              编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
      <employeeFunctionDialog  v-if="addEmployeeDialog.show" :isShow="addEmployeeDialog.show" :dialogRow="addEmployeeDialog.dialogRow"  @closeDialog="hideAddEmployeeDialog" @refresh="getEmployeeList"></employeeFunctionDialog>
    </div>
  </div>
</template>

<script>
import storage, {USER} from '../../utils/storage'
import pagination from '@/views/warehouse/components/pagination/Index'
import employeeFunctionDialog from './EmployeeFunctionDialog'
import { describeFuzzy, deleteEmployeeByPrimaryKeys } from '../../api/employee/index.js'
export default {
  name: 'myemployee',
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
      addEmployeeDialog: {
        show: false,
        dialogRow: {}
      },
      getEmployeeListLoading: false
    }
  },
  methods: {
    addEmployee: function () {
      // 把新增基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_FUNCTION_DIALOG_TPYE', 'add')
      this.addEmployeeDialog.show = true
    },
    hideAddEmployeeDialog: function () {
      this.addEmployeeDialog.show = false
    },
    // 查询
    getEmployeeList: function () {
      this.loading = true
      this.getEmployeeListLoading = true
      // 从sessionStorage 中 获取 user 对象
      let user = JSON.parse(storage.get(USER))
      let queryData = {
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'employeeName': this.searchData.name,
        // 从vuex 中获取到 用户的Id
        // 'leaderId': this.$store.getters.id
        'leaderId': user.userId,
        'companyId':"1"
      }
      describeFuzzy(queryData).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.loading = false
        this.getEmployeeListLoading = false
      }).catch((res) => {
        this.loading = false
      })
    },
    // 重置
    resetSearchData: function () {
      this.searchData.name = ''
    },
    // 删除
    delEmployee: function () {
      // 获取选中的数据id
      let list = []
      for (let i = 0; i < this.havaSelectedData.length; i++) {
        list.push(this.havaSelectedData[i].id)
      }
      // 删除
      deleteEmployeeByPrimaryKeys(list).then((res) => {
        this.$message({
          message: '删除成功',
          type: 'success',
          duration: 5 * 1000
        })
        // 成功之后重新加载 页面
        this.getEmployeeList()
      }).catch((res) => {
      })
    },
    // 修改
    onEditEmployee: function (row) {
      this.addEmployeeDialog.dialogRow = {...row}
      // 把修改基本信息 存入 vuex 中，以便后面初始化弹框title
      this.$store.commit('SET_FUNCTION_DIALOG_TPYE', 'edit')
      this.addEmployeeDialog.show = true
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
      this.getEmployeeList()
    },
    // 每页显示多少条
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getEmployeeList()
    }
  },
  created: function () {
    this.getEmployeeList()
  },
  components: {pagination, employeeFunctionDialog}
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
