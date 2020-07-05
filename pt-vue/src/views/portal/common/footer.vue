<template>
  <div class="footer">
    <div style="border-top:1px #1F1F1F solid;">
      <p class="alt" style="line-height:20px">&copy; 版权所有：1516实验室 &nbsp;
        <span>技术支持：1516实验室</span>
        <a href="###" target="_blank" style="color:#999;">Evan & MMK</a>
      </p>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Footer'
  }
</script>

<style scoped>

  .footer {
    background: #2b2e33;
    /*background: black;*/
    height: 60px;
    margin-top: -60px;
    color: #FFF;
    position: fixed;
    bottom: 0 ;
    width: 100%
  }

</style>
