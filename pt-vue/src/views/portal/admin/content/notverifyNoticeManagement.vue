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

  export default {
    components: {NoticeEditor},
    methods: {
      page(currentPage) {
        const _this = this
        this.$axios.get('/portal/notice/findAll/'+(currentPage-1)+'/5').then(resp => {
          console.log(resp)
          _this.tableData = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
      },
      backCheck(item){
        this.$axios.post('/portal/notice/backCheck3',item).then(resp => {
          console.log(resp)
            this.$message({
              type: 'info',
              message: '成功打回',
            })
            this.loadNotice()
        })
      },

      goCheck(item){
        this.$axios.post('/portal/notice/backCheck2',item).then(resp => {
          console.log(resp)
            this.$message({
              type: 'info',
              message: '审核成功',
            })
            this.loadNotice()
        })

      },
      loadNotice(){
        var _this = this
        this.$axios.get('/portal/notice/findAll/0/5').then(resp => {
          console.log(resp)
          _this.tableData = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
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
            this.$axios.delete('/portal/notice/delete/' + row.id).then(resp => {
              console.log(resp)
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
        this.$axios.get('/portal/notice/findAllById/'+qs.stringify(param)).then(resp =>{
            this.tableData = resp;
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
