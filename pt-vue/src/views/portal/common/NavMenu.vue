<template>
  <div style="position: relative;top: 0px;width:100%">
    <el-menu
      :default-active="activeIndex2"
      router
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
      <el-menu-item v-for="(item,index) in navList" :key="index" :index="item.name" >
        {{ item.navItem }}
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  export default {
    name: 'NavMenu',
    data () {
      return {
        // currentPath: this.$route.path,
        navList: [
          {name: '/AppIndex', navItem: '首页'},
          {name: '/newsShow',navItem:'新闻资讯'},
          {name: '/announcementShow', navItem: '公告通知'},
          {name:'/commentShow',navItem:'评论列表'},
          {name:'/bbsList',navItem:'公共论坛'},
          {name:'/noticeShow',navItem:'宣传栏'},
          {name: '/download',navItem:'下载专区'},
          {name:'/login',navItem:'企业内部管理'},
        ],
        keywords: '',
        activeIndex2: '1',
        name:''
      }
    },
    computed: {
      hoverBackground () {
        return '#ffd04b'
      },
      currentPath () {
        return this.$route.path
      }
    },
    mounted() {
      this.name = this.$store.state.username.data
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key)
        console.log(keyPath)
      },

    }
  }
</script>

<style scoped>
  .el-icon-switch-button {
    cursor: pointer;
    outline:0;
  }

  a{
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }

  .el-icon-switch-button {
    cursor: pointer;
    /*去除点击时的框线*/
    outline:0;
  }

</style>
