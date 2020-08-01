<!--chatlist-chatpage-->
<template>
  <div class="hello">
    <div class="usermsg" >
      <div class="add" style="float: right">
        <i class="icon iconfont icon-ios-add-circle" @click="setTable"></i>

        <el-dialog  title="新建群组" :visible.sync="dialogTableVisible" :width="dialogWidth">
          <el-form :model="{form}">
            <el-form-item label="群组名称" label-width="120px">
              <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="群组头像" label-width="120px">
              <el-avatar :src="groupAvatar"  autocomplete="off"></el-avatar>
              <input type="file" id="saveImage" name="myphoto" accept="image/*" ref="new_image" @change="addImage"></input>
            </el-form-item>
            <el-form-item label="选择好友" label-width="120px">
              <el-transfer
                style="text-align: left;display: inline-block"
                v-model="form.userIds"
                :data="userList"
                :props="{key: 'userId',label: 'nickname'}"
                :titles="['全部好友','已选择好友']"
                filterable
                filter-placeholder="搜索"
                :format="{
                  noChecked: '${total}',
                  hasChecked: '${checked}/${total}'
                }"
                @change="handleChange"
              >
                <div slot-scope="{ option }" style="margin-bottom:10px">
                  <el-avatar :size="30" :src=option.avatar style="vertical-align:middle;"></el-avatar>
                  <span> ({{ option.userName }}){{ option.nickName }}</span>
                </div>
              </el-transfer>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogTableVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitGroup">确 定</el-button>
          </div>
        </el-dialog>
      </div>
      <div class="chatlist">
        <ul class="list-ul">
          <li v-for="item in conversationsList" class="sessionlist" @click="joinRoom(item)" >
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
  import store from "@/store" ;
  import {getChatListInfo, CreateGroup, fileUpload, ShowGroupMemberInfo } from '../../../api/chat/chatApi'
  import {getUserList} from '../../../api/system/user'
  export default {
    name: 'list',
    data () {
      return {
        timer: '',
        currChat: '',
        dialogTableVisible: false,
        dialogFormVisible: false,
        groupAvatar:'/images/group.jpg',
        userList:[],
        conversationsList: store.getters.conversationsList,
        groupMemberMap:store.getters.groupMemberMap,
        dialogWidth: 0,
        username: store.getters.name,
        nickname: store.getters.nick_name,
        useravatar: store.getters.avatar,
        userId: store.getters.user_id,
        selectedRoom: store.getters.selectedRoom,
        form: {
          name: '',
          avatar:'',
          userIds:[],//this.username"111111"
          delivery: false,
        },
        filterMethod(query,item){
          //return item.username
        }
      }
    },
    computed:{

    },
    methods:{
      changeName(params){
        //console.log(params)
        let chatId = params.id;
        let chatName = params.name;
        this.conversationsList.find(group=>group.chatId==chatId).chatName=chatName;

      },
      joinRoom(room){
        this.$emit("join",room);
        //console.log(room)
        this.$store.dispatch("chat/selectedRoom",room)
      },
      updateLog(params,roomId){
        this.conversationsList.find(room=>room.chatId=roomId).groupLogList.push({
          messageType:params.messageType,
          sendContent:params.sendContent,
          sendTime:params.sendTime,
          sender:params.sender
        });
      },

      //小组成员
      getGroupMember(){
        let _this=this;
        for(let i=0;i<_this.conversationsList.length;i++){
          let params={
            chatId: _this.conversationsList[i].chatId,
          };
          let groupMembers = [];
          ShowGroupMemberInfo(params).then(r=>{
            if(r.code==200){
              r.data.forEach(v=>{
                let user = _this.userList.find(user=>user.userId==v)
                //console.log(_this.userList)
                groupMembers.push({
                  avatar:user.avatar,
                  userId:v,
                  nickName:user.nickName,
                })
              })
              _this.groupMemberMap.set(_this.conversationsList[i].chatId,groupMembers);
            }
          })
        }
        _this.$store.dispatch('chat/setGroupMemberMap',_this.groupMemberMap);
        console.log(store.getters.groupMemberMap)
      },
      setTable(){
        this.dialogTableVisible=true;
        this.form.userIds[0]=this.userId;
      },
      submitGroup(){
        this.dialogTableVisible = false;
        this.form.avatar = this.groupAvatar;
        console.log(this.form);
        let params={
          chatId: JSON.stringify(this.form.userIds),//,//+','+this.user.username,
          chatAvatar: this.groupAvatar,
          chatName: this.form.name,
        };
        console.log(params);
        CreateGroup(params).then(r=>{
          if(r.code==200){
            this.form.delivery = true;
            console.log(r.data);
            r.data.groupLogList = [];
            this.conversationsList.push(r.data);
            this.$store.dispatch('chat/setConversationsList',this.conversationsList);
            let userList=[];
            for(let i=0;i<this.form.userIds.length;i++){
              let user = this.userList.find(user=>user.userId==this.form.userIds[i]);
              userList.push({
                userId:user.userId,
                nickName:user.nickName,
                avatar:user.avatar
              })
            }
            this.groupMemberMap.set(r.data.chatId,userList)
            console.log(this.groupMemberMap)
            this.$store.dispatch('chat/setGroupMemberMap',this.groupMemberMap);
            this.form=[];
            this.groupAvatar = "";
          }
        })
      },

      handleChange(value,direction,movedkeys){
        if(direction === "right"){
          console.log(this.form.userIds)
        }
        if(direction === "left"){
          console.log(this.form.userIds)
        }
      },
      //添加图片
      addImage: function () {
        let self = this;
        if (self.$refs.new_image.files.length !== 0) {
          let formData = new FormData();
          formData.append('file', self.$refs.new_image.files[0]);
          let imgFile = self.$refs.new_image.value;
          const fr = new FileReader();
          fr.addEventListener('load',() => {

          });
          fr.readAsDataURL(self.$refs.new_image.files[0]);
          //单个文件进行上传
          fileUpload(formData).then(response => {
            this.groupAvatar = response;
          })
        }
      },
      getUser(){
        getUserList().then(r=>{
          if(r.code==0){
            this.userList = r.data;
            this.$store.dispatch('chat/setUserList',r.data)
            this.getGroupMember()
          }
          console.log(r)
        })
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
      //设置弹出框宽度
      setDialogWidth() {
        //console.log(document.body.clientWidth);
        var val = document.body.clientWidth;
        const def = 800; // 默认宽度
        if (val < def) {
          this.dialogWidth = '100%'
        } else {
          this.dialogWidth = def + 'px'
        }
      },
      //按照时间顺序排列
      sort(){
        for(let i=0;i<this.conversationsList.length;i++){
          this.conversationsList[i].groupLogList.sort((a,b)=>  new Date(a.sendTime).getTime() - new Date(b.sendTime).getTime());
        }
      }
    },
    mounted() {
      this.getUser();
      window.onresize=()=>{
        return (()=>{
          this.setDialogWidth()
        })()
      };
      if(this.timer){
        clearInterval(this.timer)
      }else{
        this.timer = setInterval(()=>{
          this.getUser();
          this.getConversationList()
        },30000)
      }
      // setTimeout(()=>{
      //   this.getGroupMember();
      // },200)
    },
    created() {
      this.getConversationList();
      this.setDialogWidth();
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
    height: 600px;

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
    height: 580px;
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
  .sessionlist:active{
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
