<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
  import store from '@/store/modules/portal'
  export default  {
    name:  'App',
    created() {
      //根据url从数据中心获取company_id
      this.$axios.get('company_id/company_id/findCompanyIdByUrl/' +store.state.url ).then(resp => {
        this.$store.commit("SET_COMPANY_ID",resp)
      })


    },
  }
</script>
