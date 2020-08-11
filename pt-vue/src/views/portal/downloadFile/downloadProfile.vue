<template>
  <div>
    <div v-for="item in item"  style="margin-top: 30px;text-align:center">
      {{item.id+item.name}}
      {{item.model}}
      <el-button style="width: 140px;" type="success">
        <!--        对a标签中的href属性进行拼接，注意要在herf前面加上：-->
        <a  :href="'http://localhost:9998/portal/download/'+item.id" >下载</a>
      </el-button>
    </div>
    <!--        total是总页数，默认是每页十条信息，可以使用page-size来进行设置-->
    <el-pagination
      background
      style="text-align: center"
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      @current-change="page">
    </el-pagination>
  </div>

</template>

<script>
    import NavMenu from '../common/NavMenu'
    import { listFile } from '../../../api/portal/downloadFile'
    export default {
        name: "downloadProfile",
      components: { NavMenu },
      data(){
          return{
            total:'2',
            pageSize:'20',
            item:[]
          }
      },
      created() {
        var _this = this
        listFile(1,this.$store.getters.company_id).then(resp => {
          _this.item = resp.data.content;
          _this.pageSize = resp.data.size;
          _this.total = resp.data.totalElements
        })

      },
      methods:{
        page(currentPage) {
          const _this = this
          listFile(currentPage,this.$store.getters.company_id).then(resp => {
            _this.item = resp.data.content;
            _this.pageSize = resp.data.size;
            _this.total = resp.data.totalElements
          })
        },
      }
    }
</script>

<style scoped>

</style>
