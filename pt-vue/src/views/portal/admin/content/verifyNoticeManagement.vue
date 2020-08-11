<template>
  <div>

    <el-card style="margin: 18px 2%;width: 95%">
      <el-table
        :data="tableData"
        border
        stripe
        :default-sort = "{prop: 'createTime', order: 'descending'}"
        style="width: 100%">
        <el-table-column
          prop="createTime"
          label="通告时间"
          sortable
        >
        </el-table-column>

        <el-table-column
          prop="name"
          label="用户名">
        </el-table-column>
        <el-table-column
          prop="title"
          label="通告标题">
        </el-table-column>

        <el-table-column
          prop="noticeContentMd"
          label="通告内容">
        </el-table-column>
        <el-table-column align="center"  label="状态" width="100">
          <template slot-scope="scope">
            <div  style="color:#008800"> 审核通过</div >
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="300" >
          <template slot-scope="scope">
            <el-button
              type="warning"
              icon="el-icon-d-arrow-right"
              @click="backCheck(scope.row)"
              style="font-size: 9px;"
              size="mini"
            >撤回</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-card>
    <el-pagination
      background
      style="text-align: center"
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      @current-change="page">
    </el-pagination>


  </div>

</template>

<script>
  import qs from 'qs'
  import NoticeEditor from '../../notice/noticeEditor'
  import { backCheck3, listNotice, listNoticeAll } from '../../../../api/portal/notice'

  export default {
    components: {NoticeEditor},
    methods: {
      page(currentPage) {
        listNotice(currentPage,this.$store.getters.company_id).then(resp => {
          this.notice = resp.data.content;
          this.pageSize = resp.data.size;
          this.total = resp.data.totalElements
        })
      },
      backCheck(item){
        backCheck3(item).then(() => {
          this.$message({
            type: 'info',
            message: '成功打回',
          })
          this.loadNotice()
        })
      },
      loadNotice(){
        listNotice(1,this.$store.getters.company_id).then(resp => {
          this.tableData = resp.data.content;
          this.pageSize = resp.size;
          this.total = resp.totalElements
        })
      },
    },
    data(){
      return {
        //pageSize为每页显示的数据条数，total为总数据
        id:'',
        pageSize:'2',
        total:'20',
        currentPage:'',
        filters:{
          id:''
        },
        tableData: []
      }
    },
    created() {
      this.loadNotice()
    }
  }
</script>


<style scoped>
</style>
