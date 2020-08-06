<template>
  <div>
    <NavMenu></NavMenu>
    <router-view></router-view>
  </div>

</template>

<script>
  import NavMenu from './common/NavMenu'
  export default {
    name: 'portalIndex',
    components: { NavMenu }
  }
</script>

<style scoped>

</style>
