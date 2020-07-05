<template>
    <div style="width: 100%;">
      <el-carousel :height="bannerHeight + 'px' ">
        <el-carousel-item v-for="(item,index) in BannerImg" :key="item">
          <img :src="item" class="bannerImg" alt=""/>
        </el-carousel-item>
      </el-carousel>
    </div>


</template>

<script>
    import NavMenu from "./NavMenu";
    export default {
        name: "pictureBanner",
      components: {NavMenu},
      data(){
          return{
            BannerImg:[
              require('@/assets/image/banner1.png'),
              require('@/assets/image/banner2.png'),
              require('@/assets/image/banner3.png'),
            ],
            bannerHeight :1000,
            // 浏览器宽度
            screenWidth :0,
          }
        },
      methods:{
        setSize() {
          // 通过浏览器宽度(图片宽度)计算高度
          this.bannerHeight = 550 / 2000 * this.screenWidth;
          this.screenWidth =  window.innerWidth;
        },
      },
      mounted() {
        // 首次加载时,需要调用一次
        this.screenWidth =  window.innerWidth;
        this.setSize();
        // 窗口大小发生改变时,调用一次
        window.onresize = () =>{
          this.screenWidth =  window.innerWidth;
          this.setSize();
        }
      }

    }

</script>

<style scoped>
  .bannerImg{
    width: 100%;
    /*规定应该从父元素继承 height 属性的值。*/
    height: inherit;
    min-height: 360px;
    min-width: 1400px;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

</style>
