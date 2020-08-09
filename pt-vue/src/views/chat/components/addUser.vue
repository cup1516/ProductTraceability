<!--群聊中拉取新的成员-->
<!--chatpage-groupInfo-addUser-->
<template>
  <div class="chooseUser" v-if="showChoose">
    <el-dialog
      :visible.sync="showChoose"
      width="50%"
      :show-close="false"
      @close="handleClose"
      :distroy-on-close="true"
      :modal-append-to-body="false"
      center>
      <div class="content">
        <div class="friendlist-area">
          <div class="search-input">
            <!--    @keydown.enter.native="searchUser"        -->
            <el-input clearable v-model="friendInput" prefix-icon="el-icon-search" @clear="del" placeholder="搜索" ></el-input>
          </div>
          <div class="friendlist" :style="{height: 450 + 'px'}">
            <ul class="friendlistul">
              <li :key = index v-for="(item, index) in OtherUserList" class="frienditem">
                <!--    根据首字母分类            <div class="list_title" v-if="item.initial">{{item.initial}}</div>-->
                <div class="friend-info" :class="{ active: item.userId === selectFriendId && !item.disabled,disable: item.disabled}" @click.stop="selectFriend(item.userId)">
                  <img class="avatar" :src="item.avatar" onerror="this.src='static/images/vue.jpg'">
                  <div class="nickname">{{item.nickName}}</div>
                  <div class="friend-check">
                    <el-checkbox
                      :true-label="item.userId+':1'"
                      :false-label="item.userId+':0'"
                      @change="friendChangeChange"
                      @click.stop.native=""
                      v-model="item.check"
                      :disabled="item.disabled"
                     >
                    </el-checkbox>
                  </div>
                </div>

              </li>
            </ul>
          </div>
        </div>
        <div class="checkedlist">
          <div class="checked-title">
            <div class="create-group-title">选择成员</div>
            <div class="check-statu-title">{{checkFriendTips}}</div>
          </div>
          <div class="friendlist" :style="{height: 400 + 'px'}">
            <ul class="friendlistul">
              <li v-bind:key = index v-for="(item, index) in selectedFriends" class="frienditem">
                <div class="friend-info" >
                  <img class="avatar" :src="item.avatar" onerror="this.src='static/images/vue.jpg'">
                  <div class="nickname">{{item.nickName}}</div>
                  <div class="delete" @click="delfriend(item.userId)"></div>
                </div>
              </li>
            </ul>
          </div>
          <div class="check-operate">
            <div class="check-btns">
              <el-button class="cancel-btn" size="medium" type="info" plain round @click="cancel">取 消</el-button>
              <el-button class="confirm-btn"  size="medium" type="success" plain round
                         @click="confirm"
                         :disabled="confirmEnable"
                         v-loading.fullscreen.lock="fullscreenLoading">确 定</el-button>
            </div>

          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import store from "@/store" ;

  export default {
    name: "addUser",
    props: [
      'isShow','roomId'
    ],
    data() {
      return {
        username: store.getters.name,
        nickname: store.getters.nick_name,
        useravatar: store.getters.avatar,
        userId: store.getters.user_id,

        userList: store.getters.userList,
        groupMemberMap: store.getters.groupMemberMap,

        showChoose: this.isShow,
        friendInput: '',
        friendList: [],
        selectedFriends: [],
        checkFriendTips: '未选择成员',
        confirmEnable: true,
        selectFriendId: '',
        AllUsers: [],
        fullscreenLoading: false,
        OtherUserList:[],
        OtherUserList2:[],
        showmodal:false,

      }
    },
    computed: {

    },
    created() {
      this.LoadFriendList();
    },
    methods:{
      del(){
        this.OtherUserList=this.OtherUserList2;
        //this.searchUser()
      },
      searchUser(){
        this.OtherUserList=this.OtherUserList2.filter(user => user.nickName.includes(this.friendInput) )
      },
      friendChangeChange(event){
        if(event && event != ""){
          var friendIdAndChecked = event.split(":");
          //Logger.log("select friendid "+friendIdAndChecked[0]+" checked "+friendIdAndChecked[1]);
          var friend = this.OtherUserList.find(friend => friend.userId == friendIdAndChecked[0]);
          //Logger.log("friend change "+friend.checked);
          if(friendIdAndChecked[1] == 1){
            if(friend){
              this.selectFriendId = friend.userId;
              var existFriend = this.selectedFriends.find(friend => friend.userId == this.selectFriendId);
              if(!existFriend){
                this.selectedFriends.push(friend);
              }
            }
          } else if(friendIdAndChecked[1] == 0){
            if(friend){
              this.removeCheckedFriend(friend.userId);
            }
          }
        }
      },
      selectFriend(UserId){
        this.selectFriendId = UserId;
        let User = this.OtherUserList.find(friend => friend.userId == UserId);
        if(User.disabled){
          return
        }
        User.check = !User.check;
        if(!User.check){
          this.removeCheckedFriend(User.userId);
        }else{
          this.selectedFriends.push(User);
        }
      },
      removeCheckedFriend(id){
        var index = -1;
        for(var i=0;i<this.selectedFriends.length;i++){
          if(id == this.selectedFriends[i].userId){
            index = i;
            break;
          }
        }
        if(index != -1){
          this.selectedFriends.splice(index,1);
        }
        if(id == this.selectFriendId){
          this.selectFriendId = 0;
        }
      },

      //加载所有的成员
      LoadFriendList(){
        let _this=this;
        let groupMember = _this.groupMemberMap.get(_this.roomId);
        for(var i=0;i<groupMember.length;i++){
          if(groupMember[i].userId==_this.userId){
            continue;
          }else {
            //小组中的所有成员
            _this.friendList.push({
              avatar:groupMember[i].avatar,
              userId:groupMember[i].userId,
              nickName:groupMember[i].nickName,
              check:false,
              disabled:true //不可选
            })
          }
        }
        for(var i=0;i<this.userList.length;i++){
          if(this.userList[i].userId==this.userId){
            continue;
          }else {
            //表中所有的用户
            _this.AllUsers.push({
              avatar:_this.userList[i].avatar,
              userId:_this.userList[i].userId,
              nickName:_this.userList[i].nickName,
              check:false,
              disabled:false
            })
          }
        }

        for(let user of this.AllUsers){

          let CurrentMember = this.friendList.find(member=>member.userId == user.userId);
          if(CurrentMember){
            _this.OtherUserList.push({
              avatar: user.avatar,
              userId: user.userId,
              nickName: user.nickName,
              check: true,
              disabled: true
            })
          }else{
            _this.OtherUserList.push({
              avatar: user.avatar,
              userId: user.userId,
              nickName: user.nickName,
              check: false,
              disabled: false
            })
          }
          _this.OtherUserList2 = _this.OtherUserList
        }


      },
      delfriend(id){
        var friend = this.OtherUserList.find(friend => friend.userId == id);
        friend.check = false;
        this.removeCheckedFriend(id);
      },
      cancel(){
        this.exit();
      },
      confirm(){
        this.showChoose = false;
        if(this.selectedFriends.length > 0){

          this.$emit('groupMember',this.selectedFriends);
          this.selectedFriends=[]
        }
      },
      handleClose(){
        this.exit();
      },
      //退出
      exit(){
        this.showChoose = false;
        this.selectedFriends = [];
        for(var friend of this.OtherUserList){
          friend.check= false;
        }
        let flag=false;
        this.$emit('groupMember',flag);
      }
    },
    watch: {
      friendInput(){
        if(this.friendInput === ''){
          this.OtherUserList=this.OtherUserList2;
        }else {
          this.searchUser()
        }
      },
      selectedFriends() {
        if(this.selectedFriends.length == 0){
          this.selectFriendId = 0;
        }
        if(this.selectedFriends.length > 0) {
          if (this.selectedFriends.length > 1) {
            this.confirmEnable = false;
          } else {
            this.confirmEnable = false;//true
          }
        }else {
          this.confirmEnable = false;//true
        }

        if(this.selectedFriends.length == 0){
          this.checkFriendTips = "未选择用户";
        } else {
          this.checkFriendTips = "已选择"+this.selectedFriends.length+"位用户";
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .content{
    display: flex;
    width: 100%;
    height: 50%;
    .friendlist-area{
      width: 50%;
      padding: 2px 0px 0px 0px;
      background: #fff;
      border-right: 1px solid #e7e7e7;
      .search-input{
        margin-right: 10px
      }
      .friendlist{
        overflow-y: auto;
        .friendlistul{
          padding-left: 0;
        }
        .frienditem{
          border-top: 1px solid #dadada;
          .friend-info{
            display: flex;
            padding: 5px;
            transition: background-color .1s;
            font-size: 0;
            &:hover{
              background-color: rgb(220,220,220)
            }
            &.active{
              background-color: #c4c4c4
            }
            .avatar{
              border-radius: 2px;
              margin-right: 12px;
              width: 35px;
              height: 35px;
            }
            .nickname{
              font-size: 14px;
              line-height: 26px;
              display: flex;
              align-items: center;
            }
            .friend-check{
              margin-left : auto;
              display: flex;
              padding-right: 5px;
              align-items: center;
            }
          }
        }

      }
    }
    .checkedlist{
      display: flex;
      flex-flow: row wrap;
      padding: 2px 0px 2px 10px;
      width: 50%;
      .checked-title{
        display: flex;
        padding: 5px 0px 10px 0px;
        width: 100%;
        height: 30px;
        .create-group-title{
          font-size: 14px;
          color: black;
        }
        .check-statu-title{
          font-size: 10px;
          color: #999;
          line-height: 15px;
          margin-left : auto;
        }
      }
      .check-operate{
        width: 100%;
        align-self: flex-end;
        .check-btns{
          width: 164px;
          margin-left : auto;
          display: flex;
        }
      }
      .friendlist{
        overflow-y: auto;
        width: 100%;
        .friendlistul{
          padding-left: 0;
        }
        .frienditem{
          padding: 5px 0px 5px 0px;
          .friend-info{
            display: flex;
            padding: 5px;
            .avatar{
              border-radius: 2px;
              margin-right: 12px;
              width: 35px;
              height: 35px;
            }
            .delete{
              margin-left : auto;
              outline: none;
              width: 35px;
              height: 35px;
              background-size: 26px;
              background-position: center;
              background-repeat: no-repeat;
              background-image: url(delete.png);
              cursor: pointer;
            }
            .nickname{
              font-size: 14px;
              line-height: 26px;
              color: black;
              display: flex;
              align-items: center;
            }


          }
        }
      }
    }
  }
  .el-dialog__wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>
