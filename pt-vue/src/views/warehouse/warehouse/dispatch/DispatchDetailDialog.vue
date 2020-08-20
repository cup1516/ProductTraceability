<template>
  <el-dialog
    :visible.sync="isVisible"
    :title="title"
    :close-on-click-modal='false'
    :close-on-press-escape='false'
    :modal-append-to-body="false"
    fullscreen
    @close="closeDialog">
    <div class="menu_content">
      <div class="form">
        <el-form ref="form" :model="form" :label-width="dialog.formLabelWidth" style="margin:10px;width:auto;">
          <el-row>
            <el-col :span="12">
              <el-form-item prop='dispatchNo' label="工单编号:">
                <span>{{form.otheroutNo}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop='creator' label="报损负责人:">
                <span>{{form.creator}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item prop='warehouseName' label="仓库:">
                <span>{{form.warehouseName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop='auditor' label="审批人:">
                <span>{{form.auditor}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="20">
              <el-form-item prop='remark' label="报损描述:">
                <el-input :rows="4" type="textarea" v-model="form.remark" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 横线 -->
      <hr>
      <!-- 已选物品 -->
      <el-row>
        <el-col :span="24">
          <div>
            <el-row>
              <el-col :span="12"><img style="width: 25px;padding: 2px;float: left" src="../../assets/threeLine.png">
                <h3 style="padding: 2px;padding-left: 50px">已选物品</h3></el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>
      <!--已选物品操作按钮-->
      <el-row>
        <el-col :span="24">
          <div class="searchArea">
            <el-form
              :inline="true"
              :model='searchData'
              ref="search_data"
              class="demo-form-inline search-form">
              <el-form-item label="">
                <el-input v-model="searchData.commodityName" placeholder="物品名称"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size="mini" icon="el-icon-search" @click='getDispatchGoodsList()'>筛选</el-button>
                <el-button type="success" size="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
      <!--已选物品展示-->
      <el-row>
        <el-col :span="24">
          <div class="fillcontain">
            <div class="table_container">
              <el-table v-loading="loading" :data="tableData"
                        style="width: 100%" align='center'>
                <!-- 物品编码 -->
                <el-table-column prop="commodityBar" label="物品编码" align='center'></el-table-column>
                <!-- 物品名称 -->
                <el-table-column prop="commodityName" label="物品名称" align='center'></el-table-column>
                <!-- 货位编码 -->
                <el-table-column prop="locatorCode" label="货位编码" align='center'></el-table-column>
                <!-- 货位名称 -->
                <el-table-column prop="locatorName" label="货位名称" align='center'></el-table-column>
                <!-- 出库数量 -->
                <el-table-column prop="qty" label="出库数量" align='center'></el-table-column>
                <!-- 添加时间 -->
                <el-table-column prop="createTime" label="添加时间" align='center'></el-table-column>
              </el-table>
              <!-- 分页 -->
              <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange"
                          @handleSizeChange="handleSizeChange"></pagination>
            </div>
          </div>
        </el-col>
      </el-row>

      <el-row style="text-align: center">
        <el-button style="width: 10%" type="primary" @click="isVisible = false">确 认</el-button>
      </el-row>
    </div>
  </el-dialog>
</template>

<script>
import {describeFuzzy} from '../../api/dispatch/dispatchDetail.js'
import pagination from '@/views/warehouse/components/pagination/Index'

export default {
  name: 'mydispatch',
  data: function () {
    return {
      isVisible: this.isShow,
      title: '报损单详情',
      // 详情弹框信息
      dialog: {
        width: '400px',
        formLabelWidth: '120px'
      },
      form: {
        dispatchNo: '',
        creator: '',
        warehouseName: '',
        auditor: '',
        remark: ''
      },
      searchData: {
        commodityName: ''
      },
      tableData: [],
      loading: false,
      page: {
        currentPage: 0,
        total: 0,
        pageSize: 10
      }
    }
  },
  props: {
    isShow: Boolean,
    dialogRow: Object
  },
  methods: {
    closeDialog: function () {
      this.$emit('closeDialog')
    },
    // 查询在报损子表中的物品数据
    getDispatchGoodsList: function () {
      this.loading = true
      let detailData = {
        'pid': this.form.dispatchNo,
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'commodityName': this.searchData.commodityName
      }
      describeFuzzy(detailData).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.loading = false
      })
    },
    resetSearchData: function () {
      this.searchData.commodityName = ''
    },
    handleCurrentChange: function (val) {
      this.page.currentPage = val
      this.getDispatchGoodsList()
    },
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getDispatchGoodsList()
    }
  },
  mounted () {
    this.form = this.dialogRow
    this.getDispatchGoodsList()
  },
  components: {pagination}
}
</script>

<style scoped>
  .searchArea {
    background: rgba(255, 255, 255, 1);
    border-radius: 2px;
    padding: 18px 18px 0px 30px;
  }

  .table_container {
    padding: 10px;
    background: #fff;
    border-radius: 2px;
  }

  .menu_content {
    position: relative;
    width: 100%;
    background: #f0f2f5;
  }
</style>
