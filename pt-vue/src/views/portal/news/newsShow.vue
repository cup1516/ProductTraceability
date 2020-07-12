<template>
  <div>
    <NavMenu></NavMenu>
    <div style="margin-top: 40px">
      <div class="articles-area">
        <el-card style="text-align: left">
          <div v-for="item in news" :key="item.id">
            <div style="float:left;width:85%;height: 150px;">
              <router-link class="article-link" :to="{path:'/newsDetails',query:{id: item.id}}"><span style="font-size: 20px"><strong>{{item.newTitle}}</strong></span></router-link>
              <el-divider content-position="left">{{item.createTime}}</el-divider>
              <router-link class="article-link" :to="{path:'/newsDetails',query:{id: item.id}}"><p>{{item.newsAbstract}}</p></router-link>
            </div>
            <el-image
              style="margin:18px 0 0 30px;width:100px;height: 100px"
              :src="item.profile"
              fit="cover"></el-image>
            <el-divider></el-divider>
          </div>
        </el-card>
      </div>
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
  </div>

</template>

<script>

  import NavMenu from '../common/NavMenu'
  export default {
    name: 'newsShow',
    components: { NavMenu },
    data () {
      return {
        news: [],
        pageSize: 4,
        total: 0,
        //pageSize为每页显示的数据条数，total为总数据
        currentPage:'',
      }
    },
    mounted () {
      this.loadArticles()
    },
    methods: {
      page(currentPage) {
        const _this = this
        this.$axios.get('/portal/News/findAll/'+(currentPage-1)+'/4').then(resp => {
          console.log(resp)
          _this.news = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
      },
      loadArticles () {
        var _this = this
        this.$axios.get('/portal/News/findAll/0/4').then(resp => {
          console.log(resp)
          _this.news = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
      },
    }
  }
</script>

<style scoped>
  .articles-area {
    width: 90%;
    height: 750px;
    margin-left: auto;
    margin-right: auto;
  }

  .article-link {
    text-decoration: none;
    color: #606266;
  }

  .article-link:hover {
    color: #409EFF;
  }
</style>
