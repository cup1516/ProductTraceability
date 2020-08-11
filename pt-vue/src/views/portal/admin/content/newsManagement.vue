  <template>
    <div>
      <news-editor @onSubmit="loadNews()" ref="edit" style="text-align: left;margin-bottom: 20px;margin-top: 20px"></news-editor>

      <el-row :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="filters">
          <el-form-item>
            <el-input v-model="filters.id" placeholder="新闻编号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="findById()">查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>

      <el-dialog :visible.sync="isShow2">
          <el-card style="margin: 35px auto 0 auto">
            <div>
              <span style="font-size: 20px;text-align: center"><strong>{{news.newTitle}}</strong></span>
              <el-divider content-position="left" style="text-align: center;color: #475669"><span style="text-align: center">发布时间:{{news.createTime}}</span></el-divider>
              <div class="markdown-body">
                <img :src="news.profile" style="width: 350px; height: 250px">
                <!--          <video :src="news.profile" controls="controls"></video>-->
                <div style="text-align: left" v-html="news.newsHtml"></div>
              </div>
            </div>
          </el-card>
      </el-dialog>

      <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
        <el-table-column
          prop="id"
          label="新闻编号"
          sortable
          fixed>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="发布时间"
          sortable
          fixed>
        </el-table-column>

        <el-table-column label="新闻标题">
          <template slot-scope="scope">
            <p :href="scope.row.newTitle" title="点击查看新闻内容详情"  class="article-link" @click="showNews(scope.row)">{{scope.row.newTitle}}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="newsAbstract"
          label="新闻摘要"
          width="200">
        </el-table-column>
        <el-table-column
          prop="profile"
          label="新闻图片"
          width="263">
          <template width="40" slot-scope="scope">
            <el-popover placement="right" title="" trigger="hover">
            <img :src="scope.row.profile"
                 :headers="myHeaders"
                 style="width: 350px; height: 250px">
            <img slot="reference" :src="scope.row.profile" :alt="scope.row.image_url" style="max-height: 100px;max-width: 200px">
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          fixed="right"
          width="240"
        >
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
    import NewsEditor from '../../news/newsEditor'
    //设置头部，token
    var Token = "Bearer"+store.getters.access_token
    import store from "@/store" ;
    import { deleteNews, findNew, loadNews } from '../../../../api/portal/new'
    export default {
      components: {NewsEditor},
      methods: {
        showNews(item){
          this.isShow2 = true
          this.news.id = item.id
          this.news.newTitle = item.newTitle
          this.news.newsHtml = item.newsHtml
          this.news.createTime = item.createTime
          this.news.profile = item.profile
        },

        edit (item) {
          this.$refs.edit.dialogFormVisible = true
          this.$refs.edit.news = {
            id: item.id,
            newTitle: item.newTitle,
            newsContentMd:item.newsContentMd,
            newsAbstract: item.newsAbstract,
            profile: item.profile,
            failTime: item.failTime,
          }
        },
        deleteNews(row) {
          this.$confirm('是否删除新闻?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
          deleteNews(row.id,this.$store.getters.company_id).then(() => {
            this.msgSuccess("删除成功");
            this.loadNews()
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
          loadNews(currentPage,this.$store.getters.company_id).then(resp => {
            this.tableData = resp.data.content;
            this.pageSize = resp.data.size;
            this.total = resp.data.totalElements
          })
        },
        loadNews () {
          loadNews(1,this.$store.getters.company_id).then(resp => {
            this.tableData = resp.data.content;
            this.pageSize = resp.data.size;
            this.total = resp.data.totalElements
          })
        },
        findById(){
          let param = {filter:this.filters.id};
          findNew( qs.stringify(param),this.$store.getters.company_id).then(resp =>{
              this.tableData = resp.data;
            }
          )
        },
    },
      data(){
        return {
          myHeaders:{
            Authorization:Token
          },
          //pageSize为每页显示的数据条数，total为总数据
          pageSize:'2',
          total:'20',
          currentPage:'',
          filters:{
            id:''
          },
          tableData: [],
          isShow2: false,
          news:{}
        }
      },

      created() {
        this.loadNews()
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
