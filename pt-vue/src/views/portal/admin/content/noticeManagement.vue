<template>
  <div>
    <notice-editor @onSubmit="loadNotice()" ref="edit" style="text-align: left;margin-bottom: 20px;margin-top: 20px"></notice-editor>
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
            <div v-if="scope.row.isCheck == 0" style="color:#800000" > 待送审</div>
            <div v-if="scope.row.isCheck == 1" style="color:#0000FF"> 待审核</div>
            <div v-if="scope.row.isCheck == 2" style="color:#008800"> 审核通过</div>
            <div v-if="scope.row.isCheck == 3" style="color:#FF0000"> 审核未通过</div>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="300" >
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.isCheck == 0||scope.row.isCheck == 3"
              type="primary"
              size="mini"
              style="font-size: 9px;"
              icon="el-icon-upload2"
              @click="toCheck(scope.row)"
            >送审</el-button>
            <el-button
              v-if="scope.row.isCheck == 0"
              type="primary"
              size="mini"
              style="font-size: 9px;"
              icon="el-icon-upload2"
              @click="edit(scope.row)"
            >编辑</el-button>
            <el-button
              v-if="scope.row.isCheck == 3"
              type="primary"
              size="mini"
              style="font-size: 9px;"
              icon="el-icon-upload2"
              @click="edit(scope.row)"
            >编辑</el-button>
            <el-button
              type="danger"
              size="mini"
              @click="deleteNotice(scope.row)"
              style="font-size: 9px;"
              v-if="scope.row.isCheck == 0"
              icon="el-icon-delete"
            >删除</el-button>
            <el-button
              v-if="scope.row.isCheck == 1"
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

  </div>

</template>

<script>
  import qs from 'qs'
  import NoticeEditor from '../../notice/noticeEditor'
  import store from "@/store" ;
  export default {
    components: {NoticeEditor},
    methods: {

      backCheck(item){
        this.$axios.post('/portal/notice/backCheck',item).then(resp => {
          console.log(resp)
            this.$message({
              type: 'info',
              message: '成功撤回',
            })
            this.loadNotice()
        })
      },

      toCheck(item){
        this.$axios.post('/portal/notice/tocheck',item).then(resp => {
            console.log(resp)

              this.$message({
                type: 'info',
                message: '送审成功',
              })
              this.loadNotice()
        })

      },
      loadNotice(){
          this.userName= store.getters.name
          this.$axios.get('/portal/notice/findAll/' + this.userName+'/'+this.$store.getters.company_id).then(resp => {
            console.log(resp)
            this.tableData = resp;
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
            this.$axios.delete('/portal/notice/delete/' + row.id+'/'+this.$store.getters.company_id).then(resp => {
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
        this.$axios.get('/portal/notice/findAllById/'+qs.stringify(param)+'/'+this.$store.getters.company_id).then(resp =>{
            this.tableData = resp;
          }
        )
      },
    },
    data(){
      return {
        //pageSize为每页显示的数据条数，total为总数据
        id:'',
        filters:{
          id:''
        },
        pageSize:'2',
        total:'20',
        currentPage:'',
        tableData: [],
        userName:'',
      }
    },
    created() {
      this.loadNotice()
    }
  }
</script>


<style scoped>
  .article-link {
    text-decoration: none;
    color: #606266;
  }

  .article-link:hover {
    color: #409EFF;
  }
</style>
