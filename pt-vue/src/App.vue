<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
  export default  {
    name:  'App',
    created() {
      //根据url从数据中心获取companyid
      this.$store.commit("SET_COMPANY_ID",1)

    },
  }
</script>
