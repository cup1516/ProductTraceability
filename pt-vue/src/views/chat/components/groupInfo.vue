<!--群聊信息-->
<!--chatpage-groupInfo-addUser-->
<template>
  <div id="group-info-id" class="group-content" :style="{height: 542 + 'px'}">
    <div class="flex-layout">
      <div class="group-info-title">
        <div class="group-name-info">
          <div class="group-name-title">群名</div>
          <p class="group-name" contenteditable="true" @blur="modifyGroupNameBlur" @keydown.enter="modifyGroupName">{{groupName}}</p>
        </div>
        <div class="group-board-info">
          <div class="group-board-title">群公告</div>
          <div class="group-board-content">暂无公告</div>
        </div>
      </div>
      <div class="friend-info-list" :style="{height: 390 + 'px'}">
        <ul class="friendul">
          <li class="addbig">
            <i title="添加成员" class="icon iconfont icon-jia" @click="addGroupMember"></i>
            <p class="add">添加成员</p>
            <addUser v-if="showAddUser" @groupMember="addUsers" :isShow="showAddUser" :roomId="roomId" ></addUser>
          </li>
          <li v-bind:key = index v-for="(item, index) in groupMembers" class="frienditem">
            <div class="friend-info">
              <img class="avatar" :src="item.avatar" onerror="this.src='static/images/vue.jpg'">
              <p class="nickName">{{item.nickName}}</p>
            </div>
          </li>
        </ul>
      </div>
      <div class="flex-bottom">
<!--  @click="quitGroup"      -->
        <p class="quit-group" >退出群聊</p>
      </div>
    </div>

  </div>
</template>

<script>
  import {ModifyGroupName,AddGroupMember} from '../../../api/chat/chatApi'
  import store from "@/store" ;
  import addUser from "./addUser";

  export default {
    name: "groupInfo",
    components:{
      'addUser':addUser,
    },
    props: ['roomId','groupName','showthis'],
    data(){
      return {
        username: store.getters.name,
        nickname: store.getters.nick_name,
        useravatar: store.getters.avatar,
        userId: store.getters.user_id,

        userList:store.getters.userList,
        groupMemberMap: store.getters.groupMemberMap,

        showAddUser: false,
        selectedUsers: [],
        groupMembers: [],
        groupInfoList:[],
        selectedUserId:""
      }
    },
    computed: {

    },
    created() {
      this.LoadFriendList();
    },
    methods:{
      //加载小组成员
      LoadFriendList(){
        let _this=this;
        _this.groupMembers = _this.groupMemberMap.get(this.roomId);
      },
      modifyGroupNameBlur(e){
        var inputName = e.target.innerText;
        if(this.groupName === inputName){
          return
        }
        //this.groupName = inputName;
        if(this.groupName && this.groupName.length < 15){
          let params={
            groupId: this.roomId,
            newName: inputName
          };
          //console.log(params)
          ModifyGroupName(params).then(r=>{
            if(r.code==200){
              console.log(r.data);
              this.$emit('up',inputName);
            }else {
              console.log("fail")
            }
          })
        } else {
          this.$message.error("群组名称过长最好不要超过15个字符");
        }
      },
      modifyGroupName(e){
        if(e.keyCode == 13){
          this.modifyGroupNameBlur(e);
          e.preventDefault();
        }
      },
      addGroupMember(){
        this.showAddUser = true;
      },
      addUsers(data){
        let _this = this;
        this.showAddUser = false;
        if(data==false){
          console.log("取消")
        }else {
          console.log(data);
          for(var i=0;i<data.length-1;i++){
            _this.selectedUserId=_this.selectedUserId+data[i].userId+","
          }
          _this.selectedUserId=_this.selectedUserId+data[i].userId;
          let params={
            groupId: this.roomId,
            userId: this.selectedUserId
          };

          AddGroupMember(params).then(r=>{
            if(r.code==200){
              console.log(r.data);
              r.data.forEach(id=>{
                let user = _this.userList.find(user=>user.userId==id);
                _this.groupMembers.push({
                  avatar:user.avatar,
                  userId:user.userId,
                  nickName:user.nickName,
                })
              })

            }
          });
          this.selectedUserId=[];
        }
      },
      // quitGroup(){
      //   this.$confirm('你将退出此群组, 是否继续?', '提示', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     type: 'warning'
      //   }).then(() => {
      //     let params={
      //       groupId: this.roomId,
      //       userId: this.userId
      //     };
      //     QuitGroup(params).then(data => {
      //       if(data.code == 200){
      //         this.$message({
      //           type: 'success',
      //           message: '退出群组成功!'
      //         });
      //         this.showthis = false;
      //         setTimeout(() => this.$store.dispatch('deleteConversation',this.roomId), 500)
      //       }
      //     })
      //
      //   }).catch(() => {
      //     this.$message({
      //       type: 'info',
      //       message: '已取消退出群组'
      //     });
      //   });
      // }
    },
    watch:{
      roomId(val){
        this.LoadFriendList();
      }
    }
  }
</script>

<style lang="scss" scoped>
  .group-content{
    position: absolute;
    background: #fff;
    height: 100px;
    width: 220px;
    top: 61px;
    right: 0px;
    z-index: 2000;
    border-left: 1px solid #e7e7e7;
    .flex-layout{
      width: 100%;
      height: 100%;
      display: flex;
      flex-flow: row wrap;
      .group-info-title{
        margin-left: 10px;
        width: 100%;
        height: 95px;
        padding: 10px 5px 10px 5px;
        border-bottom: 1px solid #e7e7e7;
        .group-name-info{
          text-align: left;
          width: 100%;
          height: 50%;
          margin-bottom: 5px;
          .group-name-title{
            font-size: 13px;
            line-height: 1.6;
            color: #888;

          }
          .group-name{
            margin: 0;
            font-size: 14px;
            line-height: 1.6;
            width: 200px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
          }
        }
        .group-board-info{
          text-align: left;
          width: 100%;
          height: 50%;
          .group-board-title{
            font-size: 13px;
            line-height: 1.6;
            color: #888;
          }
          .group-board-content{
            font-size: 14px;
            line-height: 1.6;
          }
        }
      }
      .friend-info-list{
        width: 100%;
        margin-left: 10px;
        overflow-y: auto;
        padding: 0;
        .friendul{
          padding: 0;
        }
        .frienditem{
          height: 45px;
          width: 155px;
          margin: 0;
          font-size: 14px;
          margin: 0;
          padding: 5px 0px 5px 0px
        }
        .addbig{
          padding: 5px;
          display: flex;
          .icon-jia{
            margin-right: 12px;
            font-size: 32px;
            cursor: pointer;
          }
          .add{
            font-size: 14px;
            padding-bottom: 10px;
          }
        }
        .friend-info{
          display: flex;
          padding: 5px;
          height: 40px;
          width: 120px;
          .avatar{
            border-radius: 2px;
            margin-right: 12px;
            width: 32px;
            height: 32px;
          }
          .nickName{
            margin: 0;
            padding: 0px 0px 5px 0px;
            flex: 1 1 auto;
            height: 40px;
            width: 106px;
            font-size: 14px;
            line-height: 32px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            color: black;
          }
        }
      }
      .flex-bottom{
        width: 100%;
        height: 8px;
        align-self: flex-end;
        display: flex;
        align-items: center;
        padding: 15px 0px 15px 0px;
        border-top: 1px solid #e7e7e7;
        .quit-group{
          width: 100%;
          color: rgb(0,220,65);
          font-size: 16px;
          cursor: pointer;
          text-align: center;
        }
        .dismiss-group{
          width: 100%;
          color: red;
          font-size: 16px;
          cursor: pointer;
          text-align: center;
        }
      }
    }
  }
</style>
