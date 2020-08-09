<!--chatlist-chatpage-->
<template>
  <div class="hello">
    <div class="usermsg" >
      <div class="chatlist">
        <ul class="list-ul">
          <li v-for="item in searchedConversationList" class="sessionlist" :class="{active: item.chatId===selectedRoom.chatId}" @click="joinRoom(item)" >
            <div class="list-left">
              <img class="chat-avatar"  width="42" height="42" :alt="item.chatName" :src="item.chatAvatar">
            </div>
            <div class="list-right">
              <p class="name">{{item.chatName}}</p>
              <div v-if="item.groupLogList.length!=0">
                <span class="time">{{item.groupLogList[item.groupLogList.length-1].sendTime | time}}</span>
                <div v-if="item.groupLogList[item.groupLogList.length-1].messageType===1">
                  <p class="lastmsg">{{item.groupLogList[item.groupLogList.length-1].sendContent}}</p>
                </div>
                <div v-else-if="item.groupLogList[item.groupLogList.length-1].messageType===0">
                  <p class="lastmsg">[图片]</p>
                </div>
                <div v-else-if="item.groupLogList[item.groupLogList.length-1].messageType===2">
                  <p class="lastmsg">[文件]</p>
                </div>
                <div v-else>
                  <p class="lastmsg">[语音]</p>
                </div>
              </div>
              <div v-else>
                <!--                <span class="time">{{new Date() | time}}</span>-->
                <p class="lastmsg">无聊天记录</p>
              </div>
              <!--              <p class="lastmsg">{{item.grouplogList.length-1}}</p>-->
            </div>
          </li>
        </ul>

      </div>

    </div>

  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import store from "@/store" ;
  import {getChatListInfo, ShowGroupMemberInfo } from '../../../api/chat/chatApi'
  export default {
    name: 'list',
    data () {
      return {
        timer: '',
        currChat: '',

        groupAvatar:'/images/group.jpg',
        userList:[],
        conversationsList: store.getters.conversationsList,
        groupMemberMap:store.getters.groupMemberMap,
        username: store.getters.name,
        nickname: store.getters.nick_name,
        useravatar: store.getters.avatar,
        userId: store.getters.user_id,
        selectedRooms: store.getters.selectedRoom,
      }
    },
    computed:{
      ...mapGetters([
        'searchedConversationList',
        'selectedRoom'
      ])
    },
    methods:{
      changeName(params){
        let chatId = params.id;
        let chatName = params.name;
        this.conversationsList.find(group=>group.chatId==chatId).chatName=chatName;
      },
      joinRoom(room){
        this.$emit("join",room);
        this.selectedRooms = room;
        this.$store.dispatch("chat/selectedRoom",room)
      },
      updateLog(params,roomId){
        this.conversationsList.find(room=>room.chatId==roomId).groupLogList.push({
          messageType:params.messageType,
          sendContent:params.sendContent,
          sendTime:params.sendTime,
          sender:params.sender
        });
      },

      getConversationList(){
        let params={
          userId:this.userId
        };
        getChatListInfo(params).then(r=>{
          if(r.code==200){
            this.conversationsList = r.data;
            this.$store.dispatch('chat/setConversationsList',r.data);
            this.$store.dispatch('chat/selectedRoom',r.data[0]);
          }
          console.log(r)
        })
      },
      //按照时间顺序排列
      sort(){
        for(let i=0;i<this.conversationsList.length;i++){
          this.conversationsList[i].groupLogList.sort((a,b)=>  new Date(a.sendTime).getTime() - new Date(b.sendTime).getTime());
        }
      }
    },
    mounted() {
      // if(this.timer){
      //   clearInterval(this.timer)
      // }else{
      //   this.timer = setInterval(()=>{
      //     this.getConversationList()
      //   },30000)
      // }
    },
    created() {
      this.getConversationList();
      this.sort();
    },
    destroyed() {
      clearInterval(this.timer)
    },
    filters: {
      // 将日期过滤为 hour:minutes
      time (date) {
        if (typeof date === 'string') {
          date = new Date(date);
        }
        let now = new Date();
        let nowDate = now.getDate();
        let nowMonth = now.getMonth();
        let nowYear = now.getFullYear();
        let day = date.getDay();//周几
        let datte = date.getDate();
        let month = date.getMonth();
        let year = date.getFullYear();
        let str = '';
        if(day===1){
          str = '星期一';
        }else if(day === 2){
          str = '星期二';
        }else if(day === 3){
          str = '星期三';
        }else if(day === 4){
          str = '星期四';
        }else if(day === 5){
          str = '星期五';
        }else if(day === 6){
          str = '周六';
        }else if(day === 7){
          str = '周日';
        }
        if(nowYear === year && nowMonth === month){
          if(nowDate === datte){
            if(date.getMinutes()<10){
              return date.getHours() + ':0' +date.getMinutes();
            }else{
              return date.getHours() + ':' + date.getMinutes();
            }
          } else if(nowDate-datte===1){
            return '昨天 ';
          } else if(nowDate-datte===2){
            return '前天 ';
          } else if(nowDate-datte>3 && nowDate-datte<6){
            return str;
          }else {
            return date.getFullYear() + '/' +date.getMonth() + '/' +date.getDate();
          }
        }else {
          if(date.getMinutes()<10){
            return  date.getHours() + ':0' +date.getMinutes();
          }else{
            return  date.getHours() + ':' + date.getMinutes();
          }
        }

      }
    },

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .hello{
    position: absolute;
    display: flex;
    width: 250px;
    height: 554px;

  }
  header{
    text-align: center;
    font-weight: normal;
    height: 20px;
    width: auto;
  }
  .usermsg{
    position: absolute;
    width: 100%;
    height: 100%;
  }
  .chatlist{
    height: 554px;
    overflow-y: auto;
  }
  .list-ul{
    margin: 0;
    padding: 0;
    height: 66px;
    width: 230px;
  }
  .sessionlist{
    height: 66px;
    display: flex;
    padding: 12px;
    transition: background-color .1s;
    font-size: 0;
  }
  .sessionlist:hover{
    background-color: rgb(220,220,220);
  }
  .sessionlist.active{
    background-color: #c4c4c4;
  }
  .chat-avatar{
    border-radius: 2px;
    margin-right: 12px;
  }
  .list-right{
    position: relative;
    flex: 1;
    margin-top: 0px;
  }
  .list-right .name{
    display: inline-block;
    vertical-align: top;
    font-size: 14px;
    line-height: 15px;
    float: left;
    margin-top: 0;
  }
  .list-right .time{
    float: right;
    color: #999;
    font-size: 10px;
    vertical-align: top;
  }
  .list-right .lastmsg{
    float: left;
    position: absolute;
    font-size: 12px;

    margin-bottom: 0px;

    height: 15px;
    line-height: 20px;
    color: #999;
    bottom: 4px;
    overflow: hidden;
    white-space:nowrap;
    text-overflow:ellipsis;
  }

</style>
