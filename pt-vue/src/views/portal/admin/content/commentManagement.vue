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
<!--            <el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" size="small">编辑</el-button>-->
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

  export default {
    methods: {
      edit(row){
        this.$router.push({
          path:'/updateNews',
          query:{
            id:row.id
          }
        })
      },
      deleteComment(row) {
        this.$confirm('是否删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios.delete('/portal/comment/delete/' + row.id).then(resp => {
              this.$message({
                  type: 'info',
                  message: '已删除成功',
                  callback: action => {
                    window.location.reload();
                  }
                })

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
        const _this = this
        this.$axios.get('/portal/comment/findAll/'+(currentPage-1)+'/5').then(resp => {
          console.log(resp)
          _this.tableData = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
      },
      findById(){
        let param = {filter:this.filters.id};
        this.$axios.get('/portal/comment/findAllById/'+qs.stringify(param)).then(resp =>{
            console.log(resp)
            this.tableData = resp;
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
      var _this = this
      this.$axios.get('/portal/comment/findAll/0/5').then(resp => {
        console.log(resp)
        _this.tableData = resp.content;
        _this.pageSize = resp.size;
        _this.total = resp.totalElements
      })
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
