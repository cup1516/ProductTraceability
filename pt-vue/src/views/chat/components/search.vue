<!-- 搜索框 -->
<template>
  <div class="wrapper">
    <div class="search-wrapper" style="float: left;">
      <input type="text" class="searchInput" v-model="search" @keyup="change" placeholder="搜索">
      <i class="icon iconfont icon-ios-search" v-show="noText"></i>
      <div class="searchInput-delete" v-show="haveText" @click="del"></div>
    </div>
    <div class="add" style="float: left;">
      <i class="icon iconfont icon-ios-add-circle" @click="setTable"></i>

      <el-dialog  title="新建群组" :visible.sync="dialogTableVisible" :width="dialogWidth+''">
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
              :props="{key: 'userId',label: 'nickName'}"
              :titles="['全部好友','已选择好友']"
              filterable
              filter-placeholder="搜索"
              :filter-method="filterMethod"
              :format="{
                  noChecked: '${total}',
                  hasChecked: '${checked}/${total}'
                }"
              @change="handleChange"
              :left-default-checked="hasCheckedWHLeftData"
              @left-check-change="handleWHLeftChange"
              :right-default-checked="hasCheckedWHRightData"
              @right-check-change="handleWHRightChange"
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
  </div>
</template>

<script>
  import { CreateGroup, fileUpload, ShowGroupMemberInfo } from '../../../api/chat/chatApi'
  import store from "@/store" ;
  import { getUserList } from '../../../api/system/user'
  export default {
    name: 'search',
    data () {
      return {
        timer: '',
        search: '',
        active: false,
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
          return item.nickName.indexOf(query)>-1
        },



        shiftKey: false,
        firstWHLeftLocation: -1, //数据左边起始值
        lastWHLeftLocation: -1, //数据左边终止值
        hasCheckedWHLeftData: [], //数据左边选中的数据
        firstWHRightLocation: -1, //数据右边起始值
        lastWHRightLocation: -1, //数据右边终止值
        hasCheckedWHRightData: [] //数据右边选中的数据
      }
    },
    computed: {
      noText () {
        if(this.search  === '') return true
        return false
      },
      haveText () {
        if(this.search  === '') return false
        return true
      }
    },
    created() {
      this.setDialogWidth();
    },
    mounted(){
      this.getUser();
      window.onresize=()=>{
        return (()=>{
          this.setDialogWidth()
        })()
      };
      window.addEventListener("keydown",e=>{
        if(e.keyCode ===16 && e.shiftKey){
          this.shiftKey = true;
        }
      })
      window.addEventListener("keyup",e=>{
        this.shiftKey = false;
      })
      if(this.timer){
        clearInterval(this.timer)
      }else{
        this.timer = setInterval(()=>{
          this.getUser();
        },30000)
      }
    },
    destroyed() {
      clearInterval(this.timer)
    },
    methods: {
      // 数据左侧列表元素被用户选中 / 取消选中时触发
      handleWHLeftChange(key, key1){
        let _this = this;
        console.log(_this.hasCheckedWHLeftData);
        _this.hasCheckedWHLeftData = _this.commonChangeFuc(
          key,
          key1,
          _this.hasCheckedWHLeftData,
          _this.firstWHLeftLocation,
          _this.lastWHLeftLocation,
          _this.userList,
          "userId"
        );
        console.log(_this.hasCheckedWHLeftData);
      },
      // 数据右侧列表元素被用户选中 / 取消选中时触发
      handleWHRightChange(key, key1){
        let _this = this;
        console.log(_this.hasCheckedWHRightData);
        _this.hasCheckedWHRightData = _this.commonChangeFuc(
          key,
          key1,
          _this.hasCheckedWHRightData,
          _this.firstWHRightLocation,
          _this.lastWHRightLocation,
          _this.form.userIds,
          false
        );
        console.log(_this.hasCheckedWHRightData);
      },
      // 公共按住shift 多选
      commonChangeFuc(key, key1, hasCheckedData, firstLocation, lastLocation, arrList, value) {
        let _this = this;
        let cFlag = false; //取消勾选
        // debugger
        for (let i = 0; i < key.length; i++) {
          if (key[i] == key1[0]) {
            cFlag = true; //选中
          }
        }
        if (cFlag) {
          if (key.length == 1) {
            firstLocation = 0;
            hasCheckedData.push(key[0]);
          } else if (key.length > 1) {
            let arr = [];
            // 当前有选中数据 并且 按住shift
            if (_this.shiftKey) {
              // if (isRight) {
              for (let i = 0; i < arrList.length; i++) {
                let item = value ? arrList[i][value] : arrList[i];
                if (item == key[key.length - 2]) {
                  firstLocation = i;
                }
                if (item == key1[0]) {
                  lastLocation = i;
                }
              }
              if (firstLocation != -1 && lastLocation != -1) {
                if (firstLocation < lastLocation) {
                  for (let k = 0; k < arrList.length; k++) {
                    let item = value ? arrList[k][value] : arrList[k];

                    if (k >= firstLocation && k <= lastLocation) {
                      hasCheckedData.push(item);
                    }
                  }
                } else if (firstLocation > lastLocation) {
                  for (let k = 0; k < arrList.length; k++) {
                    if (k >= lastLocation && k <= firstLocation) {
                      hasCheckedData.push(item);
                    }
                  }
                }
              }
            } else {
              //不再按shift
              hasCheckedData.push(key1[0]);
            }
          }
        } else {
          //取消选中的
          hasCheckedData = [];
          for (let i = 0; i < key.length; i++) {
            if (key[i] != key1[0]) {
              hasCheckedData.push(key[i]);
            }
          }
        }
        // 去重
        hasCheckedData = new Set(hasCheckedData);
        hasCheckedData = Array.from(hasCheckedData);
        return hasCheckedData;
      },
      change () {
        this.$store.dispatch('chat/search', this.search)
      },
      del () {
        this.search= ''
        this.change()
      },
      setTable(){
        this.dialogTableVisible=true;
        this.form.userIds[0]=this.userId;
      },
      submitGroup(){
        this.dialogTableVisible = false;
        this.form.avatar = this.groupAvatar;
        //console.log(this.form);
        let params={
          chatId: JSON.stringify(this.form.userIds),//,//+','+this.user.username,
          chatAvatar: this.groupAvatar,
          chatName: this.form.name,
        };
        CreateGroup(params).then(r=>{
          if(r.code==200){
            this.form.delivery = true;
            //console.log(r.data);
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
            //console.log(this.groupMemberMap)
            this.$store.dispatch('chat/setGroupMemberMap',this.groupMemberMap);
            this.form.name='';
            this.form.avatar='';
            this.form.delivery = false;
            this.form.userIds=[];
          }
        })
      },

      handleChange(value,direction,movedkeys){
        if(this.form.name===""){
          let l=3;
          if(value.length<=2){
            l=value.length;
          }
          let chatName = this.userList.find(user=>user.userId === value[1]).nickName;
          for(let i=2;i<l;i++){
            chatName = chatName + "、" + this.userList.find(user=>user.userId === value[i]).nickName;
          }
          if(value.length>2){
            chatName += "等"
          }
          this.form.name = chatName
        }
        if(direction === "right"){
          console.log(this.form.userIds)
        }
        if(direction === "left"){
          console.log(this.form.userIds)
        }
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
    },
  }
</script>

<style lang="scss" scoped>
  .wrapper{
    height: 46px;
    width: 250px;
    padding: 10px 10px 10px 10px;
    background-color:rgb(238,241,246);
    margin-right: 0;
  }
  .add{
    display: inline-block;
    margin-left: 3%;
  }
  .search-wrapper{

   // display: inline-block;
    position: relative;
    display: flex;
    box-sizing: border-box;
    height: 26px;
    width: 90%;
    background-color: #e5e3e2;//rgb(238,241,246)
    border: 1px solid #d9d7d6;
    border-radius: 2px;
    .searchInput{
      flex: 1;
      font-size: 12px;
      padding: 6px;
      background-color: #e5e3e2;
      outline: none;
      border: none;
      &:focus{
        background-color: #f2efee
      }
    }
    .icon-ios-search{
      display: inline-block;
      width: 24px;
      height: 24px;
      font-size: 14px;
      line-height: 24px;
      text-align: center;
    }
    .searchInput-delete{
      display: block;
      position: absolute;
      outline: none;
      top: 0;
      right: 1px;
      width: 24px;
      height: 100%;
      background-image: url("delete.png");
      background-size: 26px;
      background-position: center;
      background-repeat: no-repeat;
      cursor: pointer;
    }

  }
</style>
