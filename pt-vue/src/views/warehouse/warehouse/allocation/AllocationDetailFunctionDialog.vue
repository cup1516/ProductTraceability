<template>
  <div>
    <el-dialog
    :visible.sync="isVisible"
    :title="title"
    :close-on-click-modal='false'
    :close-on-press-escape='false'
    :modal-append-to-body="false"
    fullscreen
    @close="closeDialog">
    <div class="form">
      <el-form ref="form" :model="form" :label-width="dialog.formLabelWidth" style="margin:10px;width:auto;">
        <el-row>
          <el-col :span="12">
          <el-form-item prop='allocationNo' label="调拨编号:">
            <span>{{form.allocationNo}}</span>
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item prop='creator' label="调拨负责人:">
            <span>{{form.creator}}</span>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop='bwarehouseName' label="原仓库名称">
            <span>{{form.bwarehouseName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop='awarehouseName' label="原仓库名称">
            <span>{{form.awarehouseName}}</span>
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
    <!--已选物品展示-->
    <el-row>
      <el-col :span="24">
        <div class="fillcontain">
          <div class="table_container">
            <el-table v-loading="loading" :data="tableData"
                      style="width: 100%" align='center'>
              <!--原货位Id-->
              <el-table-column prop="locatorId" label="原货位Id" align="center"></el-table-column>
              <!--目标货位Id-->
              <el-table-column prop="toLocatorId" label="目标货位Id" align="center"></el-table-column>
              <!-- 物品编码 -->
              <el-table-column prop="commodityBar" label="物品编码" align='center'></el-table-column>
              <!-- 物品名称 -->
              <el-table-column prop="commodityName" label="物品名称" align='center'></el-table-column>
              <!-- 出库数量 -->
              <el-table-column prop="qty" label="出库数量" align='center'></el-table-column>
              <!-- 添加时间 -->
              <el-table-column prop="createTime" label="添加时间" align='center'></el-table-column>
            </el-table>
            <!-- 分页 -->
            <pagination :pageTotal="page.total" @handleCurrentChange="handleCurrentChange" @handleSizeChange="handleSizeChange"></pagination>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row style="text-align: center">
      <el-button style="width: 10%" type="primary" @click="isVisible = false">确   认</el-button>
    </el-row>
  </el-dialog>
  </div>
</template>

<script>
import { findAllocationDetailListByPage } from '../../api/allocation/allocationDetail.js'
import pagination from '@/views/warehouse/components/pagination/Index'
export default {
  name: 'allocationDetailFunctionDialog',
  data () {
    return {
      isVisible: this.isShow,
      title: '调拨单详情',
      // 详情弹框信息
      dialog: {
        width: '400px',
        formLabelWidth: '120px'
      },
      form: {
        id: '',
        locatorId: '',
        toLocatorId: '',
        commodityBar: '',
        commodityName: '',
        qty: '',
        createTime: ''
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
    // 查询出调拨单详情数据
    getAllocationDetailList: function () {
      this.loading = true
      let queryPage = {
        'pageSize': this.page.pageSize,
        'currentPage': this.page.currentPage,
        'allocationId': this.form.id,
        'companyId':"1"
      }
      findAllocationDetailListByPage(queryPage).then((res) => {
        this.tableData = res.list
        this.page.total = res.total
        this.loading = false
      })
    },
    handleCurrentChange: function (val) {
      this.page.currentPage = val
      this.getAllocationDetailList()
    },
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getAllocationDetailList()
    }
  },
  mounted () {
    this.form = this.dialogRow
    this.getAllocationDetailList()
  },
  components: {pagination}
}
</script>
