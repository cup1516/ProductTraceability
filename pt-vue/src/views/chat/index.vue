<template>
  <div class="content">
    <el-container>
      <el-aside width="250px">
        <div class="msglist">
          <search></search>
          <list @join="joinRoom" ref="chatlist"></list>
        </div>
      </el-aside>
      <el-main>
        <div class="chatbox">
          <page @changeName="changeName" @logInfo="log" ref="chatbox"></page>
        </div>
      </el-main>
    </el-container>


  </div>
</template>

<script>
  import store from "@/store" ;
  import list from './chatlist/list'
  import page from './chatpage/page'
  import search from './components/search'
  import { getChatListInfo } from '../../api/chat/chatApi'
  export default {
    name: 'index',
    components: { search, page,list},
    methods:{
      joinRoom(room){
        console.log("1")
        this.$refs.chatbox.join(room)
      },
      changeName(data1,data2){
        let params={
          name:data1,
          id:data2
        }
        console.log(params)
        this.$refs.chatlist.changeName(params)
      },
      log(params,roomId){
        this.$refs.chatlist.updateLog(params,roomId)
      }
    }
  }
</script>

<style scoped>

  .content{
    box-shadow: 0 0 10px #888888;
    position: relative;
    display: flex;
    width: 800px;
    height: 620px;
    margin: 100px;
    padding: 0;
  }
  aside{
    padding: 10px 0 10px 0;
    margin: 0;
  }
  main{
    padding: 10px 0 10px 0;
  }
  .msglist{
    height: 600px;
    width: 250px;
    margin: 0;
  }
  .chatbox{
    flex: 1;
  }
</style>
