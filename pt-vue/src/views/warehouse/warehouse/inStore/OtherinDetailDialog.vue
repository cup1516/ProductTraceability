<template>
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
          <el-form-item prop='otherinNo' label="工单编号:">
            <span>{{form.otherinNo}}</span>
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item prop='otherinNo' label="出库负责人:">
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
            <el-form-item prop='remark' label="出库描述:">
              <el-input :rows="4" type="textarea" v-model="form.remark" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!--<el-form-item  class="text_right">-->
          <!--<el-button type="primary" @click="isVisible = false">确 认</el-button>-->
        <!--</el-form-item>-->
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
        <div class="search_container searchArea">
          <el-form
            :inline="true"
            :model='searchData'
            ref="search_data"
            class="demo-form-inline search-form">
            <el-form-item label="">
              <el-input v-model="searchData.commodityName" placeholder="物品名称"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size ="mini" icon="el-icon-search" @click='getInGoodsList()'>筛选</el-button>
              <el-button type="success" size ="mini" icon="el-icon-refresh-right" @click='resetSearchData()'>重置</el-button>
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
              <el-table-column prop="qty" label="入库数量" align='center'></el-table-column>
              <!-- 添加时间 -->
              <el-table-column prop="createTime" label="添加时间" align='center'></el-table-column>

              <el-table-column prop="batch" label="订单编号" align='center'></el-table-column>
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
</template>

<script>
import { describeFuzzy } from '../../api/inStore/otherinDetail.js'
import pagination from '@/views/warehouse/components/pagination/Index'
export default {
  name: 'OtherinDetailDialog',
  data: function () {
    return {
      isVisible: this.isShow,
      title: '出库单详情',
      // 详情弹框信息
      dialog: {
        width: '400px',
        formLabelWidth: '120px'
      },
      form: {
        otherinNo: '',
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
    // 查询在出库子表中的物品数据
    getInGoodsList: function () {
      this.loading = true
      let detailData = {
        'pid': this.form.otherinNo,
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
      this.getInGoodsList()
    },
    handleSizeChange: function (val) {
      this.page.pageSize = val
      this.getInGoodsList()
    }
  },
  mounted () {
    this.form = this.dialogRow
    this.getInGoodsList()
  },
  components: {pagination}
}
</script>

<style scoped>

</style>
