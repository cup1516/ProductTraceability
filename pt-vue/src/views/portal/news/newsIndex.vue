<template>
    <div class="con">
      <div class="main" >
        <div class="text">
          <div class="text-item">
            news
            <router-link class="article-link" :to="{name:'newsShow'}">
              <i class="el-icon-circle-plus"> 查看更多</i>
            </router-link>
          </div>
          <div class="text-item">
            announcement
            <router-link class="article-link" :to="{name:'announcementShow'}">
              <i class="el-icon-circle-plus"> 查看更多</i>
            </router-link>
          </div>
        </div>

        <div class="content">
          <div class="content-item">
            <el-card :body-style="{ padding: '0px' }" class="content-item-1" shadow="hover">
              <img src="@/assets/image/news.jpg" class="image">
              <div style="padding: 5px;">
                <div class="bottom clearfix">
                  <router-link class="article-link" :to="{name:'newsDetails',query:{id:1}}">
                    <span style="font-size: 20px;text-align: center">
                      <strong>新闻头条<br></strong>
                    </span>
                  </router-link>
                </div>
              </div>
            </el-card>
            <el-card style="text-align: left;height: 400px; line-height: 50px" shadow="hover">
              <div v-for="item in news" :key="item.id">
                <span style="color: crimson;font-size: 20px">{{item.createTime}} </span>
                <el-divider direction="vertical"></el-divider>
                <router-link class="article-link" :to="{name:'newsDetails',query:{id: item.id}}">
                  <span style="font-size: 20px;">
                    <strong>{{item.newTitle}}<br></strong>
                  </span>
                </router-link>
              </div>
            </el-card>
          </div>
          <div class="content-item-2">
            <el-card style="text-align: left;height: 400px;line-height: 50px" shadow="hover">
              <div v-for="item in announcement" :key="item.id">
                <span style="color: crimson;font-size: 20px">{{item.createTime}} </span>
                <el-divider direction="vertical"></el-divider>
                <router-link class="article-link" :to="{name:'announcementDetail',query:{id: item.id}}">
                  <span style="font-size: 20px">
                    <strong>{{item.announcementTitle}}</strong>
                  </span>
                </router-link>
              </div>
            </el-card>
          </div>
        </div>
      </div>
      <div  class="footer" style="border-top:1px #1F1F1F solid;text-align: center">
       <p class="alt" style="line-height:20px">&copy; 版权所有：1516实验室 &nbsp;
         <span>技术支持：1516实验室</span>
         <br>
         <a href="###" target="_blank" style="color:#999;">中国石油大学</a>
       </p>
     </div>
    </div>



</template>

<script>

  import noticeIndex from '../notice/noticeIndex'
  import { listAnnouncement } from '../../../api/portal/announcement'
  import { listNews } from '../../../api/portal/new'
  export default {
    name: 'newsIndex',
    components: {noticeIndex},
    data () {
      return {
        currentDate: new Date(),
        news: [],
        announcement:[],
        pageSize: 4,
        total: 0,
        //pageSize为每页显示的数据条数，total为总数据
        currentPage:'',
      }
    },
    mounted () {

      this.loadNews();
      this.loadAnnouncement();
    },
    methods: {
      loadNews () {
        listNews(1,this.$store.getters.company_id).then(resp =>{
          this.news = resp.data.content;
          this.pageSize = resp.data.size;
          this.total = resp.data.totalElements
        })
      },
      loadAnnouncement () {
        listAnnouncement(1,this.$store.getters.company_id).then(resp => {
          this.announcement = resp.data.content;
          this.pageSize = resp.data.size;
          this.total = resp.data.totalElements
        })
      },

    }
  }
</script>

<style scoped>
  .con{
    display: flex;
    flex-direction: column;
  }
  .main{
    flex: 1;
  }

  .footer {
    background: #2b2e33;
    width: 100%;
    height: 60px;
    margin-top: -60px;
    color: #FFF;
    position: fixed;
    bottom: 0 ;
    flex: 0 0 20%!important;
  }

  .bottom {
    margin-top: 13px;
    line-height: 10px;
  }

  .image {
    width: 100%;
    height: 300px;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
  .text-item{
    font-family:Times New Roman;
    font-size:24px;
    padding: 20px;
    left: 20px;
    color: crimson;
    text-align: left

  }
  .text-item:first-letter{
    font-size:48px;
  }
  .text-item:after {
    content:'';
    display:block;
    width:100%;
    margin-top:10px;
    border-bottom:2px solid grey;
  }

  .text{
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
  }

  .content{
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    height: 200px;
    margin-top:30px;
    padding-bottom: 60px;
  }
  .content-item
  {
    flex: 1;
    display: flex;
    align-items: flex-start;
    height: 200px;
    width: 50%;
    padding-right: 30px;
  }
  .content-item-1{
    height: 400px;
    width: 50%;
    padding-left: 20px;
  }
  .content-item-2{
    height: 200px;
    width: 40%;
    padding-right: 20px;
  }
  .article-link {
    text-decoration: none;
  }

  .article-link:hover {
    color: #324eff;
    font-size: 30px;
  }
</style>
