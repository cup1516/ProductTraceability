<template>
  <el-dialog
    :visible.sync="DialogVisible"
    width="80%"
    center
    :before-close="bigImg">
    <div class="img" @click="bigImg">
      <img :src="imgSrc" >
    </div>
  </el-dialog>


</template>

<script>
  export default {
    props: [
      'imgSrc',
      'centerDialogVisible'
    ],
    name: "bigImg",
    data(){
      return{
        DialogVisible: this.centerDialogVisible,
      }
    },
    methods: {
      bigImg() {
        // 发送事件
        this.$emit('clickit');
        //console.log("关闭")
      }
    }
  }
</script>

<style scoped>


  /*不限制图片大小，实现居中*/
  img {
    max-width: 100%;
    display: block;
    position: center;
    left: 0;
    right: 0;
    margin: auto;
    z-index: 1000;
  }
</style>
