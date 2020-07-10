<template>
  <div>
    <div  v-for="item in notice" :key="item.id" class="articles-area">
      <el-card style="text-align: left">
        <div >
          <div style="float:left;width:85%;height: 150px;">
            <el-divider content-position="left">{{item.createTime}}</el-divider>
            <div style="font-size: 30px;font-family: '微软雅黑', Arial, Helvetica, sans-serif;color: crimson">
              {{item.name}}:
              <router-link class="article-link" :to="{path:'/noticeDetails',query:{id: item.id}}"><span style="font-size: 30px"><strong>{{item.title}}</strong></span></router-link>

            </div>
            <div style="color: #1F1F1F;font-family: '微软雅黑', Arial, Helvetica, sans-serif;font-size: 20px">
              <div style="text-align: left" v-html="item.noticeHtml"></div>

            </div>
          </div>
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

</template>

<script>

  export default {
    name: 'notice',

    data () {
      return {
        notice: [],
        pageSize: 4,
        total: 0,
        //pageSize为每页显示的数据条数，total为总数据
        currentPage:'',
      }
    },
    mounted () {
      this.loadNotice()
    },
    methods: {

      page(currentPage) {
        const _this = this
        this.$axios.get('/portal/notice/findAllDesc/'+(currentPage-1)+'/6').then(resp => {
          console.log(resp)
          _this.notice = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
      },
      loadNotice () {
        var _this = this
        this.$axios.get('/portal/notice/findAllDesc/0/6').then(resp => {
          console.log(resp)
          this.notice.name = this.$store.getters.name
          _this.notice = resp.content;
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

    margin-left: auto;
    margin-right: auto;
    padding-bottom: 20px;
    padding-top: 10px;
  }

  .article-link {
    text-decoration: none;
    color: #ff271f;
    font-size: 40px
  }

  .article-link:hover {
    color: #409EFF;
  }
</style>
