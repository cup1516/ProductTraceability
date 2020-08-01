<template>
  <div class="articles-area">
    <el-card style="width: 990px;margin: 35px auto 0 auto">
      <div>
        <span style="font-size: 20px;text-align: center"><strong>{{announcement.announcementTitle}}</strong></span>
        <el-divider content-position="left">
          <div style="font-size: 20px;color: #76838f;text-align: center">
            发布时间:{{announcement.createTime}}
          </div>
        </el-divider>
        <div class="markdown-body" style="text-align: left">
          <div v-html="announcement.announcementHtml"></div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
    export default {
      name: "announcementDetail",
      data () {
        return {
          announcement: {}
        }
      },
      mounted () {
        this.findById()
      },
      methods: {
        findById() {
          var _this = this
          this.$axios.get('/portal/Announcement/findById/' + this.$route.query.id+'/'+this.$store.getters.company_id).then(resp => {
            _this.announcement = resp;
            console.log(_this.announcement)
          })
        },
      }
    }
</script>

<style scoped>

  @import "../../../assets/styles/markdown.css";
</style>

