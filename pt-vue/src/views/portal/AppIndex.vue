<template>
  <div>
    <picture-banner></picture-banner>
    <news-index></news-index>
    <comment></comment>
    <el-backtop target=".page-component__scroll .el-scrollbar__wrap" :bottom="100">
      <div
        style="{
        height: 100%;
        width: 100%;
        background-color: #f2f5f6;
        box-shadow: 0 0 6px rgba(0,0,0, .12);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      }"
      >
        回到顶端
      </div>
    </el-backtop>
  </div>
</template>

<script>
  import pictureBanner from './common/pictureBanner'
  import NewsIndex from "./news/newsIndex";
  import Comment from "./vue_comment/comment";
  import NavMenu from './common/NavMenu'

  export default {
    name: 'AppIndex',
    components: {NavMenu, Comment, NewsIndex, pictureBanner}
  }
</script>

<style scoped>
  .home {
    width: 990px;
    margin-left: auto;
    margin-right: auto;
    margin-top: -20px;

  }

  #header-div {
    padding-bottom: 20px;
    padding-left: 5px;
    background-color: white;
    /*background: url("../../assets/img/bg/bg3.jpg");*/
  }

  #side-menu {
    position: fixed;
    margin-left: 50%;
    left: -680px;
    top: 100px;
  }
</style>
