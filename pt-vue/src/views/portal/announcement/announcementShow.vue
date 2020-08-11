<template>
  <div>
    <div style="margin-top: 40px">
      <div class="articles-area">
        <el-card style="text-align: left">
          <div v-for="item in announcement" :key="item.id">
            <div style="float:left;width:85%;height: 100px;">
              <router-link class="article-link" :to="{name:'announcementDetail',query:{id: item.id}}"><span style="font-size: 20px"><strong>{{item.announcementTitle}}</strong></span></router-link>
              <el-divider content-position="left">{{item.createTime}}</el-divider>
            </div>
          </div>
        </el-card>
      </div>
      <!--        total是总页数，默认是每页十条信息，可以使用page-size来进行设置-->
      <el-pagination
        background
        layout="prev, pager, next"
        style="text-align: center"
        :total="total"
        :page-size="pageSize"
        @current-change="page">
      </el-pagination>
    </div>
  </div>

</template>

<script>

  import NavMenu from '../common/NavMenu'
  import { listAnnouncement } from '../../../api/portal/announcement'
  export default {
    name: 'announcementShow',
    components: { NavMenu },
    data () {
      return {
        announcement: [],
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
        listAnnouncement(currentPage,this.$store.getters.company_id).then(resp => {
          _this.announcement = resp.data.content;
          _this.pageSize = resp.data.size;
          _this.total = resp.data.totalElements
        })
      },
      loadArticles () {
        var _this = this
        listAnnouncement(1,this.$store.getters.company_id).then(resp => {
          _this.announcement = resp.data.content;
          _this.pageSize = resp.data.size;
          _this.total = resp.data.totalElements
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
