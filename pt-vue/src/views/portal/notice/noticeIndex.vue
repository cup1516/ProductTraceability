<template>

  <div style="margin-right: 20px;margin-top: 220px" >

    <el-card>
      <p style="font-size: 40px;color: crimson;text-align: center">宣传栏</p>
      <div class="section-one">
        <div class="list-one-box" v-for="item in notice">
          <img :src="item.img" alt="">
          <div class="div-mask-one">
            <div>
              <p>{{item.name}}</p>
              <router-link class="mask-one-p" :to="{name:'noticeDetails',query:{id: item.id}}"><span style="font-size: 30px"><strong>{{item.title}}</strong></span></router-link>
            </div>
          </div>
        </div>
      </div>
    </el-card>

  </div>

</template>

<script>
    import { listNotice } from '../../../api/portal/notice'
    export default {
        name: 'noticeIndex',
      data(){
          return{
            img:[],
            notice: [],
            BannerImg:[
              require('@/assets/image/fruit1.png'),
              require('@/assets/image/fruit2.png'),
              require('@/assets/image/fruit3.png'),
              require('@/assets/image/fruit4.png'),
            ],
          }
      },
      mounted () {
          this.loadNotice()
      },
      methods:{
        loadNotice () {
          listNotice(1,this.$store.getters.company_id).then(resp => {
            this.notice = resp.data.content;
            let i;
            for ( i=0;i<=3;i++){
              this.notice[i].img=this.BannerImg[i]
            }
          })
        },
      }
    }
</script>

<style scoped>
  .section-one {
    position: relative;
    width: 1000px;
    margin: 0 auto;
    text-align: center;
    /*中心内容居中*/
    /* border: 1px solid red; */
  }

  .section-one>* {
    border: 1px solid red;
  }

  .list-one-box {
    position: relative;
    display: inline-block;
    width: 220px;
    height: 200px;
    border: 1px solid;
    margin: 10px 10px;
  }

  .list-one-box img {
    width: 100%;
    height: 100%;
  }

  .div-mask-one {
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 100%;
    height: 50px;
    background-color: rgba(0, 0, 0, 0.7);
    /* 垂直居中展示 注意点 如果使用绝对居中，其内容内容要用一个div包起来 */
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    font-size: 16px;
    color: #ffffff;
    transition: height 300ms
  }

  .list-one-box:hover .div-mask-one {
    height: 100%;
    transition: height 500ms
  }

  .mask-one-p{
    display: none;
  }

  .div-mask-one:hover .mask-one-p{
    display: block;
  }
</style>
