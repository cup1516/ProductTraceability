<template>
  <div class="articles-area">
    <el-card style="width: 990px;margin: 35px auto 0 auto">
      <div>
        <span style="font-size: 20px;text-align: center"><strong>{{notice.title}}</strong></span>
        <br>
        <el-divider content-position="left" style="text-align: center;color: #475669"><span style="text-align: center">发布时间:{{notice.createTime}}</span></el-divider>
        <div class="markdown-body">
          <div style="text-align: left" v-html="notice.noticeHtml"></div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
  import { findById } from '../../../api/portal/notice'

  export default {
    name: 'noticeDetails',
    data () {
      return {
        notice: {}
      }
    },
    mounted () {
      this.findById()
    },
    methods: {
      findById() {
        var _this = this
        findById(this.$route.query.id,this.$store.getters.company_id).then(resp =>{
          _this.notice = resp.data;
        })
      },
    }
  }
</script>

<style scoped>
  @import "../../../assets/styles/markdown.css";
</style>
