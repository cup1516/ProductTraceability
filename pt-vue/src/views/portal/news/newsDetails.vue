<template>
  <div class="articles-area">
    <el-card style="width: 990px;margin: 35px auto 0 auto">
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
  </div>
</template>

<script>
  export default {
    name: 'newsDetails',
    data () {
      return {
        news: {}
      }
    },
    mounted () {
      this.findById()
    },
    methods: {
      findById() {
        var _this = this
        this.$axios.get('/portal/News/findById/' + this.$route.query.id).then(resp => {
            _this.news = resp;
            console.log(_this.news)
        })
      },
    }
  }
</script>

<style scoped>
  @import "../../../assets/styles/markdown.css";
</style>
