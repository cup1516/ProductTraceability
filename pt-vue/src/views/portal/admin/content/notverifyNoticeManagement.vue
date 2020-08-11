<template>
  <div>
    <el-dialog :visible.sync="isShow2">
      <el-card style="margin: 35px auto 0 auto">
        <div>
          <span style="font-size: 20px;text-align: center"><strong>{{notice.title}}</strong></span>
          <br>
          <el-divider content-position="left" style="text-align: center;color: #475669"><span style="text-align: center">发布时间:{{notice.createTime}}</span></el-divider>
          <div class="markdown-body">

            <div style="text-align: left" v-html="notice.noticeHtml"></div>
          </div>
        </div>
      </el-card>
    </el-dialog>
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
        <el-table-column label="通告内容">
          <template slot-scope="scope">
            <p :href="scope.row.title" title="点击查看内容详情"  class="article-link" @click="showNotice(scope.row)">{{scope.row.title}}</p>
          </template>
        </el-table-column>
        <el-table-column align="center"  label="状态" width="100">
          <template slot-scope="scope">
            <div  style="color:#0000FF"> 待审核</div>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="300" >
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-d-arrow-right"
              @click="goCheck(scope.row)"
              style="font-size: 9px;"
              size="mini"
            >通过</el-button>
            <el-button
              type="warning"
              icon="el-icon-d-arrow-right"
              @click="backCheck(scope.row)"
              style="font-size: 9px;"
              size="mini"
            >打回</el-button>
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
  import {
    backCheck2,
    backCheck3,
    deleteNotice,
    findNotice,
    listNoticeAll,
    loadNotice
  } from '../../../../api/portal/notice'

  export default {
    components: {NoticeEditor},
    methods: {
      showNotice(item){
        this.isShow2 = true
        this.notice.title = item.title
        this.notice.noticeHtml = item.noticeHtml
        this.notice.createTime = item.createTime
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

      goCheck(item){
        backCheck2(item).then(() => {
            this.$message({
              type: 'info',
              message: '审核成功',
            })
            this.loadNotice()
        })

      },
      page(currentPage) {
        listNoticeAll(currentPage-1,this.$store.getters.company_id).then(resp => {
          this.tableData = resp.data.content;
          this.pageSize = resp.size;
          this.total = resp.totalElements
        })
      },
      loadNotice(){
        listNoticeAll(1,this.$store.getters.company_id).then(resp => {
          this.tableData = resp.data.content;
          this.pageSize = resp.size;
          this.total = resp.totalElements
        })
      },
      edit(item){
        this.$refs.edit.isShow = true
        this.$refs.edit.notice = {
          title:item.title,
          id: item.id,
          name: item.name,
          noticeContentMd: item.noticeContentMd,
          failTime: item.failTime
        }
      },
      deleteNotice(row) {
        this.$confirm('是否删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            deleteNotice(row.id,this.$store.getters.company_id).then(resp => {
              this.$message({
                type: 'info',
                message: '已删除成功',
              })
              this.loadNotice()
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      findById(){
        let param = {filter:this.filters.id};
        findNotice(qs.stringify(param),this.$store.getters.company_id).then(resp =>{
          this.tableData = [resp.data];
          }
        )
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
        tableData: [],
        isShow2:false,
        notice:{},
      }
    },
    created() {
      this.loadNotice()
    }
  }
</script>


<style scoped>
</style>
