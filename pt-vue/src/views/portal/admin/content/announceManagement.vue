<template>
  <div>

    <announcement-editor @onSubmit="loadAnnouncement()" ref="edit" style="text-align: left;margin-bottom: 20px;margin-top: 20px"></announcement-editor>
    <el-row :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">

        <el-form-item>
          <el-input v-model="filters.id" placeholder="公告编号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="findById()">查询</el-button>
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      :data="tableData"
      border
      stripe
      style="width: 100%">
      <el-table-column
        prop="id"
        label="公告编号"
        sortable
        fixed>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="发布时间">
      </el-table-column>

      <el-table-column
        label="公告标题">
        <template slot-scope="scope">
          <router-link class="article-link" :to="{path:'/announcementDetail',query:{id: scope.row.id}}">
            <a :href="scope.row.announcementTitle" title="点击查看公告内容详情" target="_blank" class="article-link">{{scope.row.announcementTitle}}</a>
          </router-link>
        </template>

      </el-table-column>

      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-row>
            <el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" size="small">编辑</el-button>
            <el-button @click="deleteNews(scope.row)" type="primary" icon="el-icon-delete" size="small">删除</el-button>
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
  import AnnouncementEditor from '../../announcement/announcementEditor'

  export default {
    components: {AnnouncementEditor},
    methods: {
      edit(item){
        this.$refs.edit.dialogFormVisible = true
        this.$refs.edit.announcement = {
          id: item.id,
          announcementTitle: item.announcementTitle,
          announcementContentMd: item.announcementContentMd,
          failTime: item.failTime
        }
      },
      deleteNews(row) {
        this.$confirm('是否删除公告?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete('/portal/Announcement/delete/' + row.id).then(resp => {
                console.log(resp)
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
        this.$axios.get('/portal/Announcement/findAll/'+(currentPage-1)+'/5').then(resp => {

          console.log(resp)
          _this.tableData = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
      },
      findById(){
        let param = {filter:this.filters.id};
        this.$axios.get('/portal/Announcement/findAllById/'+qs.stringify(param)).then(resp =>{
            console.log(resp)
            this.tableData = resp;
          }
        )
      },
      addAnnouncement(){
        this.$router.push('/announcementEditor')
      },
      loadAnnouncement(){
        var _this = this
        this.$axios.get('/portal/Announcement/findAll/0/5').then(resp => {
          console.log(resp)
          _this.tableData = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
      }
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
      this.loadAnnouncement()
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
