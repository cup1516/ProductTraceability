<template>
  <div>
      <el-table
      :data="tableData"
      border
      stripe
      style="width: 100%">
      <el-table-column
        prop="id"
        label="评论编号"
        width="120"
        sortable
        fixed>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="评论时间"
        width="120"
        sortable>
      </el-table-column>
      <el-table-column
        prop="commentName"
        label="评论昵称"
        width="200">
      </el-table-column>
      <el-table-column
        prop="commentText"
        label="评论内容">
      </el-table-column>

      <el-table-column
        label="操作"
        fixed="right"
        width="240"
      >
        <template slot-scope="scope">
          <el-row>
            <el-button @click="deleteComment(scope.row)" type="primary" icon="el-icon-delete" size="small">删除</el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
    <br>
    <!--        total是总页数，默认是每页十条信息，可以使用page-size来进行设置-->
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
  import { deleteComment, findComment, loadComment } from '../../../../api/portal/comment'

  export default {
    methods: {
      deleteComment(row) {
        this.$confirm('是否删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteComment(row.id,this.$store.getters.company_id).then(() => {
              this.$message({
                  type: 'info',
                  message: '已删除成功',
                })
              this.loadComment()
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      page(currentPage) {
        loadComment(currentPage,this.$store.getters.company_id).then(resp => {
          this.tableData = resp.data.content;
          this.pageSize = resp.data.size;
          this.total = resp.data.totalElements
        })
      },
      loadComment()
      {
        loadComment(1,this.$store.getters.company_id).then(resp => {
          this.tableData = resp.data.content;
          this.pageSize = resp.data.size;
          this.total = resp.data.totalElements
        })
      },
      findById(){
        let param = {filter:this.filters.id};
        findComment(qs.stringify(param),this.$store.getters.company_id).then(resp =>{
          this.tableData = resp.data;
          }
        )
      },

    },
    data(){
      return {
        //pageSize为每页显示的数据条数，total为总数据
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
    this.loadComment()

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
