<template>
  <div>
    <div  v-for="item in notice" :key="item.id" class="articles-area">
      <el-card style="text-align: left">
        <div >
          <div style="float:left;width:85%;height: 150px;">
            <el-divider content-position="left">{{item.createTime}}</el-divider>
            <div style="font-size: 30px;font-family: '微软雅黑', Arial, Helvetica, sans-serif;color: crimson">
              {{item.name}}:
              <router-link class="article-link" :to="{name:'noticeDetails',query:{id: item.id}}"><span style="font-size: 30px"><strong>{{item.title}}</strong></span></router-link>

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

  import NavMenu from '../common/NavMenu'
  import { listNotice } from '../../../api/portal/notice'
  export default {
    name: 'notice',
    components: { NavMenu },
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
        listNotice(currentPage,this.$store.getters.company_id).then(resp => {
          this.notice = resp.data.content;
          this.pageSize = resp.data.size;
          this.total = resp.data.totalElements
        })
      },
      loadNotice () {
        listNotice(1,this.$store.getters.company_id).then(resp => {
          this.notice = resp.data.content;
          this.pageSize = resp.data.size;
          this.total = resp.data.totalElements
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
