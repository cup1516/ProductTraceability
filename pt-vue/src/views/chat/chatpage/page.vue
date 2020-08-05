<template>
  <div class="chat-box" >
    <!--  消息框  -->
    <div class="msg-box" ref="msg-box">
      <header>
        <div class="groupname">{{roomName}}</div>
        <div class="group-info">
          <i title="群组信息" class="icon iconfont icon-gengduo" @click="changeShowGroupInfo"></i>
          <groupInfo @up="upClose" :showthis="showGroupInfo" :groupName="roomName" :roomId="roomId" v-if="showGroupInfo"></groupInfo>
        </div>
      </header>
      <!--   消息框内   -->
      <div class="message-wrapper" ref="list" id="list">
        <!--    加载更多  more  -->
        <div class="loading" v-if="more"  ref="loading">
          <i class="el-icon-loading" v-if="loadingMore"></i>
          <i v-else class="icon iconfont icon-jiazaigengduo" @click="loadMore"></i>
        </div>
        <span class="nomore" v-else>没有更多讨论记录了</span>
        <!--消息-->
        <div style="list-style-type: none" v-for="(i,index) in messageList" :key="index" class="msg" >
          <!--    时间    -->
          <div class="time">
            <span>{{i.sendTime | time}}</span>
          </div>
          <!--    对话    -->
          <div :class="i.senderId==userId?'right':'left'">
            <!--    头像      -->
            <div class="user-head">
              <img :src="i.avatar" height="30" width="30" :title="i.senderId" >
            </div>
            <!--    消息      -->
            <div class="user-msg">
              <!--    名字      -->
              <div class="nickname">{{i.senderId==userId? nickName : i.nickName}}</div>
              <!--    消息      -->
              <div class="msg">

                <!--    文字消息   {{i.sendContent}}   -->
                <span v-if="i.messageType === 1" v-html="replaceFace(i.sendContent)"></span>

                <!--    图片消息      -->
                <span v-if="i.messageType === 0">
                   <img :id="i.sendContent" :src="i.sendContent" height="100" width="100" @click="clickImg(i.sendContent)">
                  <div>
                    <!--   放大图片    -->
                    <big-img v-if="showImg" @clickit="viewImg" :imgSrc="imgSrc" :centerDialogVisible="showImg"></big-img>
                  </div>
                </span>

                <!--    文件消息      -->
                <span v-if="i.messageType === 2">
                  <i class="icon iconfont icon-wenjian" @click="downLoad(i.sendContent)"> {{i.sendContent.split('$')[1]}}</i>
                </span>

                <!--    语音消息   -->
                <span v-if="i.messageType === 3">
                  <div >
                    <i class="icon iconfont icon-bofangluyin2" @click="player(i.sendContent)"/>{{i.sendContent.split('.')[0].split('$')[1]}}
                  </div>
                </span>

                <!--    电话消息   -->
                <span v-if="i.messageType === 4">
                  [通话]<i class="icon iconfont icon-shipintonghua"/>
                </span>

                <span v-if="i.messageType === 5">
                  [请求通话]<i class="icon iconfont icon-shipintonghua"/>
                </span>
                <span v-if="i.messageType === 6">
                  [取消通话]<i class="icon iconfont icon-shipintonghua"/>
                </span>
                <span v-if="i.messageType === 8">
                  [结束通话]<i class="icon iconfont icon-shipintonghua"/>
                </span>

              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
    <!--  输入框  -->
    <div class="input-box">
      <!-- 图标栏 -->
      <div class="icon">
        <!-- 表情 -->
        <div class="emoji">
          <i class="icon iconfont icon-emoticon" @click="showEmoji=!showEmoji"> </i>
          <transition name="showbox">
            <div class="emojiBox" v-show="showEmoji">
              <li style="list-style-type:none;" v-for="(item, index) in emojis">
                <img :src="'/emoji/'+item.file" :data="item.code" @click="messageValue +=item.code">
              </li>
            </div>
          </transition>
        </div>
        <!-- 图片 -->
        <div class="image">
          <label for="saveImage">
            <i class="icon iconfont icon-picture"> </i>
            <input type="file" id="saveImage" name="myphoto" accept="image/*" ref="new_image" @change="addImage" style="display: none"></input>
          </label>
        </div>
        <!--文件 -->
        <div class="file">
          <label for="saveFile">
            <i class="icon iconfont icon-wenjian"> </i>
            <input type="file" id="saveFile" name="myfile"  ref="new_file" @change="addFile" style="display: none"></input>
          </label>
        </div>
        <!-- 语音 -->
        <div class="record">
          <i class="icon iconfont icon-yuyin1" @click="showRecord=true"> </i>
          <!--          <record v-if="showRecord"  @start="startRecord" :isShow="showRecord"></record>-->
          <el-dialog
            v-dialogDrag
            title="录制语音消息"
            :visible.sync="showRecord"
            width="270px"
            center
            :modal="false"
            :close-on-click-modal='false'
            :before-close="cancel">

            <span>
              <div class="timer">
                <div ref="startTimer"></div>
              </div>

              <!-- 开始录音 -->
              <div>

                <div class="start">
                  <i v-show="isStart" class="icon iconfont icon-yuyin" @click="recorder"> </i>
                  <i v-show="isPause" class="icon iconfont icon-luyinzanting" @click="pause"></i>
                </div>
                <!--        -->
                <i  class="icon iconfont icon-icon_luyinbolang-" :style="{'color':(isPause==true?'#1aad19':'#2F4F4F')}"></i>
              </div>
            </span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="cancel">取 消</el-button>
              <el-button :disabled="!yse" type="primary" @click="submit">确 定</el-button>
            </span>
          </el-dialog>
        </div>
        <!-- 语音电话 -->
        <div class="caller">
          <i class="icon iconfont icon-dianhua2" @click="showcallerChoice"> </i>
          <chooseUser v-if="showChoose" @users="startChoose" :isShow="showChoose" :roomId="roomId" :flag="callerChoice"></chooseUser>
        </div>
        <!-- 视频电话 -->
        <div class="video">
          <i class="icon iconfont icon-shipintonghua" @click="showvideoChoice"> </i>
          <chooseUser v-if="showChoose" @users="startChoose" :isShow="showChoose" :roomId="roomId" :flag="videoChoice"></chooseUser>
        </div>

        <!-- 视频电话 -->
        <div class="callContent" v-if="showChatBox">
          <div class="">
            <div class="callercontent callshow" style="">
              <div class="exchange-content">
                <!-- 对方大   -->
                <div class="playcontent left-big-content">
                  <img id="wxCallRemoteImg" class="bigavatar" :src="choiceUser[0].avatar" v-show="showCallRemoteImg"/>
                  <p id="wxCallTips" class="calltips" v-show="showCallTips"> 接通中... </p>
                  <video id="RemoteVideo" autoplay="autoplay" playsinline="" style="display: none; width: 535px;height: 400px;" v-show="showCallRemoteVideo"></video>
                </div>
                <!--  自己小-->
                <div class="playcontent right-sml-content">
                  <img id = "wxCallLocalImg" :src="userAvatar" class="bigavatar" v-show="showCallLocalImg"/>
                  <video id="LocalVideo" autoplay="autoplay" muted="muted" playsinline="" style="display: none;width: 160px;" v-show="showCallLocalVideo"></video>
                </div>
              </div>
              <div class="opera-content">
                <img class="calleravatar" :src="choiceUser[0].avatar" />
                <span class="callnick" v-text="choiceUser[0].nickName"></span>
                <span class="flexbox">
                            <span class="cancelbtn" v-show="cancelCall" @click="cancelcall"><i class="iconfont icon-guaduan"></i>取消</span>
                            <span class="rejectbtn" style="display: none;" v-show="rejectCall" @click="reject"><i class="iconfont icon-guaduandianhua"></i>拒绝</span>
                            <span class="acceptbtn" style="display: none;" v-show="acceptCall" @click="accept"><i class="iconfont icon-jieting"></i>接听</span>
                </span>
                <span class="talktime" style="display: none;" v-if="showTalkTime" v-text="talkTime">00:00</span>
                <span class="hangupbtn" style="display: none;" v-show="hangUpCall" @click="hangUp"><i class="iconfont icon-guaduan"></i>挂断 </span>
              </div>
            </div>
          </div>
        </div>

        <!-- 语音电话 -->
        <div class="audioContent" v-if="showAudioBox">
          <div class="audioBody callshow" style="">
            <!-- 头像  -->
            <div class="audioBg">
              <img class="callavatar" :src="choiceUser[0].avatar" />
              <div class="blackbg"></div>
            </div>

            <div class="audiomain">
              <!-- 小头像 -->
              <img class="audio-avatar" :src="choiceUser[0].avatar" />
              <!-- 昵称 -->
              <p class="callnick" v-text="choiceUser[0].nickName"></p>
              <!-- 时间 (可选显示)v-text="waitingMsgTips"-->
              <p class="call-time"  v-if="showTalkTime" v-text="talkTime">00:00</p>
              <p class="waiting-msg" v-show="waitingMsg" >接通中...</p>
              <!-- 按钮-->
              <div class="call-opera flexbox">
                <span class="cancleaudio hangup btnopacity" style="display: none;" v-show="hangUpCall" @click="hangUp"><i class="iconfont icon-guaduan"></i>挂断 </span>
                <div class="loadingcall flexbox">
                  <span class="cancleaudio callercanle btnopacity" style="display: none;" v-show="cancelCall" @click="cancelcall"><i class="iconfont icon-guaduan"></i>取消 </span>
                  <span class="cancleaudio btnopacity" style="display: none;" v-show="rejectCall" @click="reject"><i class="iconfont icon-guaduandianhua"></i>拒绝</span>
                  <span class="upcall btnopacity" style="display: none;" v-show="acceptCall" @click="accept"><i class="iconfont icon-jieting"></i>接听</span>
                </div>
              </div>
            </div>
            <div style="display: none;">
              <audio id="RemoteAudio" autoplay="autoplay"></audio>
            </div>
            <div style="display: none;">
              <audio id="LocalAudio" autoplay="autoplay" muted="muted"></audio>
            </div>
          </div>


        </div>

      </div>

      <!-- 输入框 -->
      <textarea ref="text" v-model="messageValue" @keyup.enter="send()" @click="showEmoji=false"></textarea>
      <div class="send" @click="send()">
        <span>发送(enter)</span>
      </div>
      <transition name="appear">
        <div class="warn" v-show="warn">
          <div class="description">不能发送空白信息</div>
        </div>
      </transition>

    </div>


  </div>

</template>

<script>

  import store from "@/store" ;
  import { fileDownload, getGroupLog, fileUpload, getChatListInfo } from '../../../api/chat/chatApi'
  import Recorder from 'js-audio-recorder'
  let recorder = new Recorder();

  import bigImg from '../components/bigImg'
  import record from '../components/record'
  import chooseUser from '../components/chooseUser';
  import groupInfo from '../components/groupInfo'
  export default {
    name: "page",
    components: {
      'big-img': bigImg,
      'record': record,
      'groupInfo': groupInfo,
      'chooseUser': chooseUser
    },
    data() {
      return {

        userName: store.getters.name,
        nickName: store.getters.nick_name,
        userAvatar: store.getters.avatar,
        userId: store.getters.user_id,

        emojis: store.getters.emojis,

        roomId: "",
        roomName: "",
        selectRoom: store.getters.selectedRoom,

        groupMemberMap:store.getters.groupMemberMap,
        groupMemberList:[],

        showGroupInfo: false,

        ws: null, // WebSocket对象
        socket: null,
        aisle: "", // 对方频道号
        lockReconnect: false,
        timeout:5000,
        timeoutObj: null,
        serverTimeoutObj: null,
        urlList:[],

        messageList: [], // 消息列表
        messageValue: "", // 消息内容

        warn: false,
        showEmoji: false,
        msgtype: 1,

        file: '',
        fileUrl: '',
        filename: '',

        image: '',
        showImg: false,
        imgSrc:'',
        imgUrl: '',

        count: 0,
        historyList: [],
        frequency: 0,
        more: true,
        loadingMore:false,
        page:1,
        isTop: 0 ,

        recordMsg:'',
        showRecord: false,
        recordUrl: '',

        play: false,
        msg: '',
        isStart: true,
        isPause: false,
        timer: "",
        content: "",
        hour: 0,
        minutes: 0,
        seconds: 0,
        dataArray:[],
        yse: false,
        duration: " ",

        showChoose:false,
        choiceUser:[],//选择的要进行视频/电话的用户

        callerChoice:false,
        videoChoice:false,
        showAudioBox:false,
        showChatBox: false,
        videoTextCallTips: '',
        voipClient: null,
        rejectCall: false,
        cancelCall: false,
        acceptCall: false,
        hangUpCall: false,
        showCallLocalImg: true,
        showCallLocalVideo: false,
        showCallRemoteImg: true,
        showCallRemoteVideo: false,
        showCallTips: true,
        waitingMsg: false,
        isAudioOnly: false,
        waitingMsgTips: '',
        showTalkTime: false,
        talkInterval: 0,
        talkTime: '00:00',
        talkTimerInterval: null,
        offer:"",

        callPage:'',
        callToUsernameInput:'',
        callBtn:'',
        yourConn:'',
        stream:'',
        thisvideo:'',

        localstream:'',
        pc:'',
        isToPeer:'',
        name:'',
      };
    },
    created() {
      //this.getConversationList();
      console.log(this.selectRoom);
      setTimeout(()=>{
        this.getLog();
      },500);

    },
    mounted() {
      console.log("mounted")
      this.$refs.text.focus();
      this.roomId = this.selectRoom.chatId;
      this.roomName = this.selectRoom.chatName;
      this.initWebSocket();
      setTimeout(() => {
        this.scrollBottm();
      }, 200);
      //监听滚动事件
      window.addEventListener('scroll',this.handleScroll);
    },
    destroyed() {
      // 离开页面时关闭websocket连接
      this.lockReconnect=true
      this.ws.onclose();
      this.socket=null;
      console.log("连接关闭");
    },
    methods: {
      getConversationList(){
        let params={
          userId:this.userId
        };
        getChatListInfo(params).then(r=>{
          if(r.code==200){
            this.$store.dispatch('chat/selectedRoom',r.data[0]);
          }
          console.log(r)
        })
      },
      initiateCall(data){
        console.log("initiateCall:"+data)
        var self = this;
        var iceServer = {
          "iceServers": [{
            "url": "stun:stun.l.google.com:19302"
          }]
        };
        self.pc = new RTCPeerConnection(iceServer);

        self.pc.onicecandidate = function(event) {
          console.log("send ice")
          let message={
            candidate:event.candidate,
            to:self.choiceUser[0].userId
          };
          self.msgtype=9;
          self.messageValue=JSON.stringify(message);
          //console.log(this.messageValue)
          self.send();
        };
        self.pc.onaddstream = function(e) {
          console.log("onaddstream")
          if(self.isAudioOnly){
            var video = document.getElementById('RemoteAudio');
            self.addVideoURL('RemoteAudio', e.stream);
          }else {
            var video = document.getElementById('RemoteVideo');
            self.addVideoURL('RemoteVideo', e.stream);
          }
        };

        if(self.isAudioOnly){
          console.log("初始化音频")
          navigator.mediaDevices
            .getUserMedia({ audio: true, video: false })
            .then(function(stream) {
              self.thisvideo = document.getElementById('LocalAudio');
              self.addVideoURL('LocalAudio', stream);
              self.thisvideo.muted = true;
              self.localStream = stream;
              self.pc.addStream(stream);
              if(data){
                self.sendOffer();
              }else{
                self.handleOffer(self.offer);
                self.sendAnswer();
              }
            })
            .catch(function(err) {
              console.log(err.name + ': ' + err.message);
            });
        }else{
          console.log("初始化视频")
          navigator.mediaDevices
            .getUserMedia({ audio: true, video: true })
            .then(function(stream) {
              self.thisvideo = document.getElementById('LocalVideo');
              self.addVideoURL('LocalVideo', stream);
              self.thisvideo.muted = true;
              self.localStream = stream;
              self.pc.addStream(stream);
              if(data){
                self.sendOffer();
              }else{
                self.handleOffer(self.offer);
                self.sendAnswer();
              }
            })
            .catch(function(err) {
              console.log(err.name + ': ' + err.message);
            });
        }



      },
      addVideoURL(elementId, stream) {
        var video = document.getElementById(elementId);
        // Old brower may have no srcObject
        if ('srcObject' in video) {
          video.srcObject = stream;
        } else {
          // 防止在新的浏览器里使用它，应为它已经不再支持了
          video.src = window.URL.createObjectURL(stream);
        }
      },
      sendOffer() {
        let self = this;
        self.pc.createOffer(
          offer => {
            console.log("send offer");
            let message={
              offer:offer,
              to:self.choiceUser[0].userId
            };
            self.msgtype=10;
            self.messageValue=JSON.stringify(message);
            //console.log(this.messageValue);
            self.send();
            self.pc.setLocalDescription(offer);
          },
          error => {
            alert('Error when creating an offer');
          }
        );
      },
      sendAnswer(){
        let self = this;
        self.pc.createAnswer(
          answer => {
            console.log("send answer");
            self.pc.setLocalDescription(answer);
            let message={
              answer:answer,
              to:self.choiceUser[0].userId
            };
            self.msgtype=11;
            self.messageValue=JSON.stringify(message);
            // console.log(this.messageValue);
            self.send();
          },
          error => {
            alert('Error when creating an answer');
            console.log(error)
          }
        );
      },
      handleAnswer(data) {
        let self = this;
        self.pc.setRemoteDescription(new RTCSessionDescription(data)).catch(error=>{
          console.log(error)
        });
        console.log("handleAnswer ok")
      },
      handleOffer(data) {
        let self = this;
        self.pc.setRemoteDescription(new RTCSessionDescription(data)).catch(error=>{
          console.log(error)
        });
        console.log("handleOffer ok")
      },
      handleCandidate(data) {
        let self = this;
        self.pc.addIceCandidate(new RTCIceCandidate(data));
        console.log("handleCandidate")
      },
      handleLeave() {
        let self = this;
        self.pc.close();
        self.pc.onicecandidate = null;
        self.pc.onaddstream = null;
        console.log(self.nickName+"挂断")
        self.thisvideo.srcObject.getTracks()[0].stop();
        self.thisvideo.srcObject.getTracks()[1].stop();
      },

      //准备语音之前选择成员
      showcallerChoice(){
        this.showChoose=true;
        this.callerChoice=true;
        this.isAudioOnly=true;
      },
      //准备视频之前选择成员
      showvideoChoice(){
        this.showChoose=true;
        this.videoChoice=true;
        this.isAudioOnly=false;
      },
      startChoose(data){
        let _this=this;
        _this.showChoose=false;

        if(!data){
          console.log("取消操作")
        }else {
          console.log(data)
          _this.choiceUser=[];
          data.forEach(v=>{
            _this.choiceUser.push({
              avatar:v.avatar,
              userId:v.userId,
              nickName:v.nickName,
            })
          });
          this.msgtype = 5;//请求通话
          let message= {
            to:_this.choiceUser[0].userId,//请求对象
            switch:_this.isAudioOnly //视频or语音
          };
          _this.messageValue = JSON.stringify(message);
          _this.send();
          if(_this.isAudioOnly){
            _this.sendAudio();
          }else {
            _this.sendVideo();
          }
        }
      },
      //发起视频电话
      sendVideo(){
        this.showChatBox = true;
        this.rejectCall = false;
        this.acceptCall = false;
        this.hangUpCall = false;
        this.cancelCall = true;
        this.showCallRemoteVideo = false;
        this.showCallRemoteImg = true;
        this.showCallLocalVideo = true;
        this.showCallLocalImg = false;
        this.showCallTips = true;
        this.isAudioOnly = false;
        //准备通话 初始化
        this.initiateCall(true)
      },
      //收到视频电话
      receiveVideo(){
        this.showChatBox = true;
        this.showCallTips = false;
        this.rejectCall = true;
        this.acceptCall = true;
        this.hangUpCall = false;
        this.cancelCall = false;
        this.isAudioOnly = false;
        this.showCallRemoteImg = true;
        this.showCallLocalImg = true;
      },
      //取消视频
      unVideoCall(){
        this.showChatBox = false;
        this.thisvideo.srcObject.getTracks()[0].stop();
        this.thisvideo.srcObject.getTracks()[1].stop();
      },
      //发起语音电话
      sendAudio(){
        this.showAudioBox = true;
        this.waitingMsg = false;
        this.rejectCall = false;
        this.acceptCall = false;
        this.hangUpCall = false;
        this.cancelCall = true;
        this.isAudioOnly = true;
        //准备通话 初始化
        this.initiateCall(true)
      },
      //收到语音电话
      receiveAudio(){
        this.showAudioBox = true;
        this.waitingMsg = true;
        this.rejectCall = true;
        this.acceptCall = true;
        this.hangUpCall = false;
        this.cancelCall = false;
        this.isAudioOnly = true;
      },
      //取消语音
      unAudioCall(){
        this.showAudioBox = false;
        this.thisvideo.srcObject.getTracks()[0].stop();
      },
      //收到同意通话之后开始准备语音通话
      startCall(){
        let _this = this;
        if(_this.isAudioOnly){//语音通话
          _this.waitingMsg = false;
        }else {//视频通话
          _this.showCallTips = false;
          _this.showCallRemoteImg = false;
          _this.showCallRemoteVideo = true;
        }

        _this.rejectCall = false;
        _this.acceptCall = false;
        _this.cancelCall = false;
        _this.hangUpCall = true;
        _this.showTalkTime = true;
        _this.talkTimerInterval = setInterval(() => {
          _this.talkInterval += 1;
          var min = Math.floor(_this.talkInterval / 60 % 60);
          var sec = Math.floor(_this.talkInterval % 60);
          sec = sec < 10 ? "0"+sec : sec;
          min = min < 10 ? "0"+min : min;
          _this.talkTime = min + ":"+ sec;
        },1000);
      },
      clearCallTime(){
        //  this.showAudioBox=false;
        this.showTalkTime = false;
        this.talkInterval = 0;
        this.talkTime = '00:00';
        clearInterval(this.talkTimerInterval);
      },
      hangUp(){//挂断
        let _this = this;
        if(_this.isAudioOnly){
          _this.showAudioBox=false;
        }else {
          _this.showChatBox=false;
        }

        _this.showTalkTime = false;
        let message={
          to:this.choiceUser[0].userId //请求对象
        }
        _this.msgtype = 8;//结束通话
        _this.messageValue = JSON.stringify(message);
        _this.send();
        _this.clearCallTime();
        _this.handleLeave();
      },
      reject(){//拒绝
        let _this = this;
        if(_this.isAudioOnly){
          _this.showAudioBox=false;
        }else {
          _this.showChatBox=false;
        }
        _this.showAudioBox=false;
        let message={
          to:_this.choiceUser[0].userId //请求对象
        }
        _this.msgtype = 6;//拒绝通话
        _this.messageValue = JSON.stringify(message);//请求对象
        _this.send();
      },
      accept(){//接受
        let _this = this;
        let message={
          to:_this.choiceUser[0].userId //请求对象
        }
        _this.msgtype = 7;//同意通话
        _this.messageValue = JSON.stringify(message);//请求对象
        _this.send();

        _this.rejectCall = false;
        _this.acceptCall = false;
        _this.hangUpCall = true;
        if(!_this.isAudioOnly){
          _this.showCallLocalImg = false;
          _this.showCallRemoteImg = false;
          _this.showCallLocalVideo = true;
          _this.showCallRemoteVideo = true;
        }
        _this.startCall();
        _this.initiateCall(false)
      },
      cancelcall(){//取消
        let _this = this;
        if(_this.isAudioOnly){
          _this.showAudioBox=false;
        }else {
          _this.showChatBox=false;
        }
        let message ={
          to:_this.choiceUser[0].userId
        }
        _this.msgtype = 6;//取消通话
        _this.messageValue = JSON.stringify(message);//请求对象
        _this.send();
        _this.thisvideo.srcObject.getTracks()[0].stop();
        _this.thisvideo.srcObject.getTracks()[1].stop();
      },
      //...................................................................................................................
      //..........................................................................................................................
      //显示小组信息
      changeShowGroupInfo(){
        if(this.showGroupInfo){
          this.showGroupInfo = false;
        }else{
          this.showGroupInfo = true;
        }
      },
      //小组信息框
      upClose(data){
        this.showGroupInfo = false;
        this.roomName = data;
        this.$emit('changeName',data,this.roomId);
      },


      join(data){
        console.log("pagejoin")
        this.$refs.text.focus();
        setTimeout(() => {
          this.scrollBottm();
        }, 200);
        //监听滚动事件
        window.addEventListener('scroll',this.handleScroll);
        let _this = this;
        this.roomName = data.chatName;
        this.roomId = data.chatId;
        _this.historyList = [];
        _this.messageList = [];
        _this.page = 1;
        _this.more = true;
        _this.getLog();
        setTimeout(()=>{_this.initWebSocket()},200);
      },

      // 发送聊天信息
      send: function() {
        if(this.messageValue.length <=1){
          //alert("消息为空，请输入消息")
          this.warn = true;
          this.messageValue = '';
          setTimeout(() => {
            this.warn = false;
          }, 1000)
        }else {
          if(this.msgtype == 0){//图片
            var socketMsg = {
              msg: this.imgUrl,
              type: this.msgtype
            };
          }else if(this.msgtype == 2){//文件
            var socketMsg = {
              msg: this.fileUrl,
              type: this.msgtype
            };
          }else if(this.msgtype == 3){//语音
            var socketMsg = {
              msg: this.recordUrl,
              type: this.msgtype
            };
          } else {
            var socketMsg = {
              msg: this.messageValue,
              type: this.msgtype
            };
          }
          console.log(this.userName+"发送的消息:");
          console.log(socketMsg);
          this.ws.send(JSON.stringify(socketMsg));
          this.messageValue = '';
          this.msgtype = 1;
          setTimeout(() => {
            this.scrollBottm();
          }, 200);
          this.$refs.text.focus();
        }
      },
      //  在发送信息之后，将输入的内容中属于表情的部分替换成emoji图片标签
      //  再经过v-html 渲染成真正的图片
      replaceFace (con) {
        if(con.includes('/:')) {
          var emojis=this.emojis;
          for(var i=0;i<emojis.length;i++){
            con = con.replace(emojis[i].reg, '<img src="/emoji/' + emojis[i].file +'"  alt="" style="vertical-align: middle; width: 24px; height: 24px" />');
          }
          return con;
        }
        return con;
      },
      // 进入页面创建websocket连接
      initWebSocket() {
        console.log("房间号：" + this.roomName);
        console.log("用户名：" + this.userId);

        if (window.WebSocket){
          let _this = this;
          let id = _this.userId;
          let sip = this.roomId;
          var url = "ws://localhost:9998/chat/websocket/chat/" + sip + "/" + id;
          if(_this.urlList.indexOf(url)===-1){
            _this.urlList.push(url);
            _this.createWebsocket(url);
          }
        }
      },
      //创建websocket连接
      createWebsocket(url){
        let _this = this;
        try{
          let ws = new WebSocket(url);
          _this.ws = ws;
          _this.handleEvent(url);
        }catch (e) {
          console.log(e)
        }
      },
      handleEvent(url){
        let _this = this;
        _this.ws.onopen = function(e) {
          console.log("服务器连接成功 :"+url);
        };
        _this.ws.onclose = function(e) {
          console.log("服务器连接关闭 :"+url);
          console.log(e.code+' '+e.reason+' '+e.wasClean);
          _this.reConnect(url)
        };
        _this.ws.onerror = function() {
          console.log("服务器连接出错: "+url );
          _this.reConnect(url)
        };
        _this.ws.onmessage = function(e) {
          var object = eval("(" + e.data + ")");
          _this.count = object.count;
          console.log("接收消息:");
          console.log(object);
          let user = _this.infoHandle(object.senderId)
          if(object.messageType==5||object.messageType==6||object.messageType==7||object.messageType==8||object.messageType==9||object.messageType==10||object.messageType==11){
            let message = JSON.parse(object.sendContent);
            if(message.to==_this.userId){//判断是不是给自己的
              if(object.messageType==5){
                _this.choiceUser.push({
                  avatar:user.avatar,
                  userId:object.senderId,
                  nickName:user.nickName,
                });
                _this.isAudioOnly = message.switch;
                if(_this.isAudioOnly){
                  _this.receiveAudio();
                }else {
                  _this.receiveVideo();
                }
              }else if(object.messageType==6){
                if(_this.isAudioOnly){
                  _this.unAudioCall();
                }else {
                  _this.unVideoCall();
                }
              }else if(object.messageType==7){
                _this.startCall();
              }else if(object.messageType==8){
                if(_this.isAudioOnly){
                  _this.showAudioBox=false;
                }else {
                  _this.showChatBox=false;
                }
                _this.clearCallTime();
                _this.handleLeave();
              }else if(object.messageType==9){//如果是一个ICE的候选，则将其加入到PeerConnection中，否则设定对方的session描述为传递过来的描述
                _this.handleCandidate(message.candidate);
              }else if(object.messageType==10){
                _this.offer = message.offer;
              }else if(object.messageType==11){
                _this.handleAnswer(message.answer);
              }
            }
          }
          //显示消息
          if(object.messageType==7||object.messageType==9||object.messageType==10||object.messageType==11){
          }else {
            _this.messageList.push({
              senderId: object.senderId,
              messageType: object.messageType,
              sendContent: object.sendContent,
              avatar: user.avatar,
              sendTime: new Date(),
              nickName: user.nickName,
            });
            let params = {
              messageType: object.messageType,
              sender: object.senderId,
              sendContent: object.sendContent,
              sendTime: new Date(),
            }
            _this.$emit('logInfo',params,_this.roomId)
          }
        }
      },

      //数据处理
      infoHandle(id){
        let self = this;
        self.groupMemberList = self.groupMemberMap.get(self.roomId);
        //console.log(self.roomId)
        //console.log(self.groupMemberMap)
        //console.log(self.groupMemberMap.get(self.roomId))
        let user = self.groupMemberMap.get(self.roomId).find(user => user.userId == id);
        return user;
      },
      //重连
      reConnect(url){
        if(this.lockReconnect) return;
        this.lockReconnect = true;
        setTimeout(()=>{
          console.log("尝试重连....");
          this.lockReconnect = false;
          this.createWebsocket(url)
        },5000)
      },
      //滚动事件监听处理
      // handleScroll(){
      //   if(document.getElementById('list').scrollTop){
      //     this.loadingMore = true;
      //    // console.log(document.getElementById('list').scrollTop);
      //   }
      // },
      // 滚动条到底部
      scrollBottm() {
        let el = this.$refs["list"];
        el.scrollTop = el.scrollHeight;
      },
      //显示人数
      showCount: function (people) {
        this.count=people;
      },
      //添加图片
      addImage: function () {
        console.log("add")
        let self = this;
        if (self.$refs.new_image.files.length !== 0) {
          let formData = new FormData();
          formData.append('file', self.$refs.new_image.files[0]);
          let imgFile = self.$refs.new_image.value;
          const fr = new FileReader();
          fr.addEventListener('load',() => {
            this.image = fr.result
          });
          fr.readAsDataURL(self.$refs.new_image.files[0]);
          //单个文件进行上传
          fileUpload(formData).then(response => {
            this.imgUrl = response;
            this.msgtype = 0;
            this.messageValue ='图片消息';
          })
        }
        this.$refs.text.focus();
      },
      //放大----------------------------------------------------------------------------------
      clickImg(e) {
        this.showImg = true;
        this.imgSrc = e;
      },
      viewImg() {
        this.showImg = false
      },
      //-------------------------------------------------------------------------------------
      //语音----------------------------------------------------------------------------------
      startRecord(data){
        this.showRecord = false;
        if(data===false){
          console.log("取消录音")
        }else {
          this.recordMsg = data;
          //时长
          this.duration = recorder.duration;
          let recordFile = new File([data],"record.wav",{type: "audio/wav"});
          let self = this;
          var fd = new FormData();
          fd.append('file', recordFile,this.duration+'.wav');//文件内容：blob
          fileUpload(fd).then(response => {
            self.recordUrl = response;
            self.msgtype = 3;
            self.messageValue ='语音消息';
          })
        }
      },
      submit(){//提交录音信息
        this.showRecord = false;
        this.msg = recorder.getWAVBlob();
        this.reset();
        this.startRecord(this.msg);
      },
      cancel(){//取消录音
        this.showRecord = false;
        let flag = false;
        this.startRecord(flag);
        this.reset();
      },
      recorder(){//开始录音
        this.isStart = false;
        this.isPause = true;
        this.yse = true;
        recorder.start();// 开始录音
        console.log("开始录音");
        this.start();
        //录音波形显示
        //this.dataArray = recorder.getRecordAnalyseData();
        //console.log(this.dataArray)
      },
      start(){//开始计时
        this.timer = setInterval(this.startTimer, 1000);
      },
      reset(){//重置
        let that = this;
        that.yse = false;
        that.isStart = true;
        that.isPause = false;
        clearInterval(that.timer);
        that.hour = 0;
        that.minutes = 0;
        that.seconds = 0;
        that.$refs.startTimer.innerHTML = '00:00';
      },
      destroyed(){
        let that = this;
        if(that.yse === true){
          // 销毁录音实例，置为null释放资源，fn为回调函数，
          recorder.destroy().then(function() {
            recorder.stop();
            that.reset();
          });
        }

      },
      pause(){//暂停
        this.isStart = true;
        this.isPause = false;
        recorder.pause();
        clearInterval(this.timer);
      },
      startTimer () {
        this.seconds += 1;
        if (this.seconds >= 60) {
          this.seconds = 0;
          this.minutes = this.minutes + 1;
        }
        if (this.minutes >= 60) {
          this.minutes = 0;
          this.hour = this.hour + 1;
        }
        this.$refs.startTimer.innerHTML = (this.minutes < 10 ? '0' + this.minutes : this.minutes) + ':' + (this.seconds < 10 ? '0' + this.seconds : this.seconds);
      },
      //播放录音
      player(url){
        let wav = url;
        let audio = new Audio(wav);
        if(this.play===false){
          //播放
          audio.play();
          console.log("正在播放");
          this.play = true;
        }else{
          //停止播放
          audio.pause();
          console.log("停止播放");
          this.play = false;
        }
      },
      //-------------------------------------------------------------------------------------
      //添加文件
      addFile: function () {
        let self = this;
        if (self.$refs.new_file.files.length !== 0) {
          let formData = new FormData();
          formData.append('file', self.$refs.new_file.files[0]);
          let File = self.$refs.new_file.value;
          const fr = new FileReader();
          fr.addEventListener('load', () => {
            this.file = fr.result;
          });
          fr.readAsDataURL(self.$refs.new_file.files[0]);
          let size = self.$refs.new_file.files[0].size;
          size = size/(1024*1024);
          if(size > 10){
            this.$message.error("文件大小不能超过10M！");
            return false
          }else{
            //单个文件进行上传
            fileUpload(formData).then(response => {
              this.fileUrl = response;
              this.msgtype = 2;
              this.messageValue = self.$refs.new_file.files[0].name;
            })
          }
        }
        this.$refs.text.focus();
      },
      //下载文件
      downLoad(data){
        console.log(data)
        let words = data.split("/");
        let params={
          fileName: words[words.length-1]
        }
        this.$axios({
          method: 'get',
          url: 'chat/api/fileDownload',
          params: params,
          responseType: 'arraybuffer'
        }).then(response => {
          let url = window.URL.createObjectURL(new Blob([response]))
          var btn = document.createElement("a");
          btn .setAttribute('download', data.split('$')[1]);// download属性
          btn .setAttribute('href', url);// href链接
          btn.click();// 自执行点击事件
        })
      },
      //加载历史记录
      loadMore(){
        let that = this;
        this.loadingMore = true;
        setTimeout(()=>{
          this.loadingMore = false;
          this.getLog();
        },500);
      },
      //聊天记录
      getLog() {
        console.log("get")
        let params={
          chatId: this.roomId,
          page: this.page,
        }
        getGroupLog(params).then(r => {
          let _this=this;
          if (r.code === 200) {
            _this.page = _this.page+1;

            console.log("GroupLog");
            let list = r.data;
            for(let i=0;i<list.length;i++){
              let user = _this.infoHandle(list[i].senderId)
              _this.historyList.push({
                sendContent:list[i].sendContent,
                messageType:list[i].messageType,
                sendTime:list[i].sendTime,
                senderId:list[i].senderId,
                nickName:user.nickName,
                avatar:user.avatar
              });
            }
            this.messageList = this.historyList.reverse().concat(this.messageList);
            console.log(this.messageList)
            this.historyList=[];
          } else {
            console.log(r);
            this.more = false;
          }
        });
      },
    },

    computed: {

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
            if(date.getMinutes()<10){
              return '昨天 ' + date.getHours() + ':0' +date.getMinutes();
            }else{
              return '昨天 ' + date.getHours() + ':' + date.getMinutes();
            }
          } else if(nowDate-datte===2){
            if(date.getMinutes()<10){
              return '前天 ' + date.getHours() + ':0' +date.getMinutes();
            }else{
              return '前天 ' + date.getHours() + ':' + date.getMinutes();
            }
          } else if(nowDate-datte>3 && nowDate-datte<6){
            if(date.getMinutes()<10){
              return str + ' ' + date.getHours() + ':0' +date.getMinutes();
            }else{
              return str + ' ' + date.getHours() + ':' + date.getMinutes();
            }
          }else {
            if(date.getMinutes()<10){
              return year + '年' + month + '月' + datte + '日 ' + date.getHours() + ':0' +date.getMinutes();
            }else{
              return year + '年' + month + '月' + datte + '日 ' + date.getHours() + ':' + date.getMinutes();
            }
          }
        }else {
          if(date.getMinutes()<10){
            return year + '年' + month + '月' + datte + '日 ' + date.getHours() + ':0' +date.getMinutes();
          }else{
            return year + '年' + month + '月' + datte + '日 ' + date.getHours() + ':' + date.getMinutes();
          }
        }

      }
    },
    watch:{
      //发送信息后,让信息滚动到最下面
      messageList() {
        setTimeout(() => this.$refs.list.scrollTop = this.$refs.list.scrollHeight, 0)
      },
      // 当输入框中的值为空时 弹出提示  并在一秒后消失
      messageValue() {
        if(this.messageValue === ''){
          if( this.frequency === 0){
            this.warn = true;
            this.frequency++;
            setTimeout(() => {
              this.warn = false;
            }, 1000)
          }
        }
      }
    },
  }
</script>

<style lang="scss" scoped>
  .chat-box {
    padding: 0;
    background: #fafafa;
    position: absolute;
    height: 600px;
    width: 550px;

    .msg-box {
      header {
        height: 60px;
        padding: 0px 0 0 30px;
        box-sizing: border-box;
        border-bottom: 1px solid #e7e7e7;
        display: flex;
        .groupname{
          display: flex;
          align-items: center;
          font-size: 18px;
        }
        .group-info {
          padding: 0px 20px 0px 0px;
          display: flex;
          align-items: center;
          margin-left: auto;
          .icon-gengduo{
            font-size: 15px;
            cursor: pointer;
          }
        }
      }
      width: 100%;
      height: 450px;
      .message-wrapper{
        min-height: 390px;
        max-height: 390px;
        padding: 10px 15px;
        box-sizing: border-box;
        overflow-y: scroll;
        border-bottom: 1px solid #e7e7e7;
        .loading{
          margin:0 0 0 242px;
        }
        .nomore{
          display: inline-block;
          padding: 4px 6px;
          color: #fff;
          border-radius: 3px;
          background-color: #dcdcdc;
          font-size: 13px;
          margin-left: 200px;
        }
        .msg {
          margin-bottom: 15px;
          .time{
            width: 100%;
            height: 20px;
            font-size: 12px;
            margin: 7px auto;
            text-align: center;
            span{
              display: inline-block;
              padding: 4px 6px;
              color: #fff;
              border-radius: 3px;
              background-color: #dcdcdc;
            }
          }
          .left{
            text-align: left;
            .user-head{
              float: left;
              margin-left: 15px;
              border-radius: 3px;
            }
            .user-msg{
              display: inline-block;
              margin-left: 10px;
              .nickname{
                position: relative;
                font-size: 10px;
                font-family: 微软雅黑;
                margin-block: 44px;
                color: #999999;
              }
              .msg{
                position: relative;
                padding: 6px 10px;
                max-width: 330px;
                min-height: 36px;
                line-height: 24px;
                box-sizing: border-box;
                font-size: 14px;
                word-break: break-all;
                background-color: #c6c6c6;
                border-radius: 4px;
                &:before{
                  content: " ";
                  position: absolute;
                  top: 12px;
                  right: 100%;
                  border: 6px solid transparent;
                  border-right-color: #c6c6c6;
                }
                .icon-bofangluyin2{
                  cursor: pointer;
                  &:hover{
                    color: #1aad19
                  }
                }
                .icon-wenjian{
                  cursor: pointer;
                  &:hover{
                    color: #1aad19
                  }
                }
              }
            }
          }
          .right{
            text-align: right;
            .user-head{
              float: right;
              margin: 0 15px;
              border-radius: 100px;
            }
            .user-msg{
              right: -300px;
              display: inline-block;
              margin-left: 10px;
              .nickname{
                text-align: right;
                position: relative;
                font-size: 10px;
                font-family: 微软雅黑;
                margin-block: 44px;
              }
              .msg{
                position: relative;
                padding: 6px 10px;
                width: auto;
                max-width: 330px;
                min-height: 36px;
                line-height: 24px;
                box-sizing: border-box;
                font-size: 14px;
                word-break: break-all;
                background-color: #b2e281;
                border-radius: 4px;
                text-align: left;
                &:before{
                  content: " ";
                  position: absolute;
                  top: 12px;
                  right: -12px;
                  border: 6px solid transparent;
                  border-left-color: #b2e281;
                }
                .img{

                }
                .icon-bofangluyin2{
                  cursor: pointer;
                  &:hover{
                    color: #1aad19
                  }
                }
              }
            }
          }
        }
      }
    }
    .input-box {
      position: relative;
      height: 150px;
      background: #fff;
      .icon{
        position: relative;
        width: 100%;
        height: 40px;
        line-height: 40px;
        font-size: 15px;
        padding: 0 5px;
        box-sizing: border-box;
        color: #7c7c7c;
        .emoji{
          float: left;
          margin-left: 1%;
          .icon-emoticon{
            cursor: pointer;
            &:hover{
              color: #1aad19
            }
          }
          .emojiBox{
            position: absolute;
            display: flex;
            flex-wrap: wrap;
            top: -210px;
            left: -100px;
            width: 300px;
            height: 200px;
            padding: 5px;
            background-color: #fff;
            border: 1px solid #d1d1d1;
            border-radius: 2px;
            box-shadow: 0 1px 2px 1px #d1d1d1;
            &.showbox-enter-active, &.showbox-leave-active{
              transition: all .5s
            }
            &.showbox-enter, &.showbox-leave-active{
              opacity: 0
            }
          }
        }
        .image{
          float: left;
          margin-left: 1%;
          .icon-picture{
            cursor: pointer;
            &:hover{
              color: #1aad19
            }
          }
        }
        .record{
          float: left;
          margin-left: 1%;
          .icon-yuyin1{
            cursor: pointer;
            &:hover{
              color: #1aad19
            }
          }
        }
        .file{
          float: left;
          margin-left: 1%;
          .icon-wenjian{
            font-size: 13px;
            cursor: pointer;
            &:hover{
              color: #1aad19
            }
          }
        }
        .record{
          .start{
            .icon-yuyin{
              margin-left: 80px;
              cursor: pointer;
              &:hover{
                color: #1aad19
              }
            }
            .icon-luyinzanting{
              margin-left: 76px;
              cursor: pointer;
              font-size: 30px;
            }
            &:hover{
              color: #1aad19
            }
          }

          .icon-icon_luyinbolang-{

            margin-left: 46px;
          }

          .timer{
            margin-left: 80px;
          }
        }
        .caller{
          float: left;
          margin-left: 1%;
          .icon-dianhua2{
            font-size: 13px;
            cursor: pointer;
            &:hover{
              color: #1aad19
            }
          }
        }
        .video{
          float: left;
          margin-left: 1%;
          .icon-shipintonghua{
            font-size: 13px;
            cursor: pointer;
            &:hover{
              color: #1aad19
            }
          }
        }
        .chat-modal{
          position: fixed;
          left: 0;
          top: 0;
          right: 0;
          bottom: 0;
          z-index: 1001;
          -webkit-overflow-scrolling: touch;
          outline: 0;
          overflow: hidden;
          // margin: 30/@rate auto;
          background-color: rgba(0,0,0,.3);
          .chat-bbox{
            position: relative;
            left: 50%;
            top: 5%;
            transform: translate(-50%,0);
            // padding: 50/@rate 40/@rate;
            background: #fff;
            height: 800px;
            width: 480px;
            .video-local{
              width: 50px;
              height: 100px;
              vertical-align: middle;
            }
            .video-remote{
              width: 480px;
              height: 800px;
              vertical-align: middle;
            }
          }
        }
        .btnopacity:hover{
          opacity: .8
        }
        .callContent{
          .callercontent{
            width: 664px;
            height: 414px;
            position: absolute;
            left: -100px;
            right: 0;
            margin: auto;
            bottom: 0;
            z-index: 2000;
            /*.video{*/
            /*  width: 100%;*/
            /*  background: #000;*/
            /*}*/
            .callshow{
              display: block
            }
            .exchange-content{
              width: 545px;
              height: 420px;
              .left-big-content{
                width: 545px;
                height: 360px;
                position: absolute;
                left: 0;
                top: 0;
                .bigavatar{
                  width: 100%;
                  height: 100%;
                  filter: blur(6px)
                }
                .calltips{
                  position: absolute;
                  margin: auto;
                  text-align: center;
                  color: #fff;
                  font-size: 16px;
                  z-index: 10;
                  left: 0;
                  right: 0;
                  top: 0;
                  line-height: 360px
                }
                .video{
                  width: 545px;
                  height: 360px;
                  background: #000
                }
              }
              .right-sml-content{
                width: 160px;
                height: 120px;
                box-shadow: 0 6px 20px 0 rgba(48,52,58,0.5);
                border-radius: 4px;
                position: absolute;
                right: -41px;
                bottom: 0;
                .bigavatar{
                  width: 100%;
                  height: 100%;
                  border-radius: 4px
                }
                .video{
                  object-fit: fill;
                  background: #000;
                  width: 160px;
                }
              }
            }

            .opera-content{
              padding: 0px 0px;
              box-shadow: 0 6px 20px 0 rgba(48,52,58,0.5);
              height: 40px;
              width: 545px;
              background: #fff;
              position: absolute;
              bottom: 15px;
              left: 0;
              .calleravatar{
                width: 36px;
                height: 36px;
                margin-right: 16px;
                flex-shrink: 0;
                border-radius: 100%
              }
              .callnick{
                flex: 1;
                color: #30343a
              }
              .flexbox{
                //display: flex;
                align-items: center;
                //display: table;
                display: inline-block;
                .cancelbtn{
                  /*display:table-cell;*/
                  /*vertical-align:middle;*/
                  padding: 5px 5px 5px 5px;
                  margin: 0px 0px 0px 360px;
                  width: 72px;
                  height: 32px;
                  border-radius: 6px;
                  color: #fff;
                  text-align: center;
                  line-height: 0px;
                  cursor: pointer;
                  background: #ff6161;

                  //line-height: 0px;

                  .icon-guaduan{
                    color: #fff;
                    font-size: 16px;
                    margin-right: 8px
                  }
                }
                .rejectbtn{
                  padding: 5px 5px 5px 5px;
                  width: 72px;
                  height: 32px;
                  border-radius: 6px;
                  color: #fff;
                  text-align: center;
                  font-size: 18px;
                  cursor: pointer;
                  background: #ff6161;
                  .icon-guaduandianhua{
                    color: #fff;
                    font-size: 16px;
                    margin-right: 8px
                  }
                }
                .acceptbtn{
                  padding: 5px 5px 5px 5px;
                  width: 72px;
                  height: 32px;
                  border-radius: 6px;
                  color: #fff;
                  text-align: center;
                  font-size: 18px;
                  cursor: pointer;
                  margin-left: 16px;
                  background: #39ba70;
                  .icon-jieting{
                    color: #fff;
                    font-size: 16px;
                    margin-right: 8px
                  }
                }
              }
              .talktime{
                .span{
                  font-size: 12px;
                  color: #30343a;
                  margin-left: 8px;
                  margin-right: 16px
                }
              }
              .hangupbtn{
                margin: 0px 0px 0px 360px;
                padding: 5px 5px 5px 5px;
                width: 72px;
                height: 32px;
                border-radius: 6px;
                color: #fff;
                text-align: center;
                font-size: 18px;
                //  line-height: 0px;
                cursor: pointer;
                background: #ff6161;
                //line-height: 0px;
                .icon-guaduandianhua{
                  color: #fff;
                  font-size: 16px;
                  margin-right: 8px
                }
              }
            }
          }
        }
        .audioContent{
          .flexbox{
            display: flex;
            align-items: center
          }
          .callshow{
            display: block
          }
          .audioBody{
            width: 280px;
            height: 344px;
            position: absolute;
            left: 0px;
            right: 0;
            margin: auto;
            top: -200px;
            bottom: 0;
            z-index: 2000;
            border-radius: 3px
          }
          .audioBody{
            .audioBg{
              position: absolute;
              width: 100%;
              height: 100%;
              left: 0;
              top: 0;
              border-radius: 3px;
              .callavatar{
                width: 100%;
                height: 100%;
                filter: blur(4px)
              }
              .blackbg{
                width: 100%;
                height: 100%;
                position: absolute;
                z-index: 5;
                top: 0;
                left: 0;
                background: #000;
                opacity: .5
              }
            }
            .audiomain{
              position: relative;
              z-index: 6;
              text-align: center;
              color: #fff;
              .audio-avatar{
                width: 73px;
                height: 73px;
                position: relative;
                border-radius: 50%;
                overflow: hidden;
                margin-top: 64px;
                margin-bottom: 12px;
              }
              .callnick{
                font-size: 16px;
                line-height: 22px
              }
              .call-time{
                height: 20px;
                line-height: 20px;
                margin-top: 4px
              }
              .waiting-msg{
                height: 20px;
                line-height: 20px;
                margin-top: 4px
              }
              .call-opera{
                justify-content: center;
                .call-opera span{
                  width: 96px;
                  height: 32px;
                  margin: 66px 12px 0;
                  line-height: 32px;
                  border-radius: 16px;
                  cursor: pointer;
                  .icon-guaduan{
                    font-size: 16px;
                    margin-right: 8px;
                    &:hover{
                      pointer-events: none
                    }
                  }
                }
                .hangup{
                  width: 128px
                }
                .nomuted{
                  border: 1px solid #fff
                }
                .muted{
                  background: #fff;
                  color: #30343a
                }
                .cancleaudio{
                  background: #ff6161
                }
                .callercanle{
                  width: 128px
                }
                .btnopacity:hover{
                  opacity: .8
                }
              }
            }
            .loadingcall{
              justify-content: center;
              .upcall{
                background: #39ba70
              }
            }

          }
        }
      }
      textarea{
        box-sizing: border-box;
        padding: 0 30px;
        height: 110px;
        width: 100%;
        border: none;
        outline: none;
        font-family: "Micrsofot Yahei";
        resize: none
      }
      .send{
        position: absolute;
        bottom: 10px;
        right: 30px;
        width: 75px;
        height: 28px;
        line-height: 28px;
        box-sizing: border-box;
        text-align: center;
        border: 1px solid #e5e5e5;
        border-radius: 3px;
        background: #f5f5f5;
        font-size: 14px;
        color: #7c7c7c;
        &:hover{
          background: rgb(18, 150, 17);
          color: #fff
        }
      }
      .warn{
        position: absolute;
        bottom: 50px;
        right: 10px;
        width: 110px;
        height: 30px;
        line-height: 30px;
        font-size: 12px;
        text-align: center;
        border: 1px solid #bdbdbd;
        border-radius: 4px;
        box-shadow: 0 1px 5px 1px #bdbdbd;
        &.appear-enter-active, &.appear-leave-active{
          transition: all 1s
        }
        &.appear-enter, &.appear-leave-active{
          opacity: 0
        }
        &:before{
          content: " ";
          position: absolute;
          top: 100%;
          right: 20px;
          border: 7px solid transparent;
          border-top-color: #fff;
          filter: drop-shadow(1px 3px 2px #bdbdbd)
        }
      }
    }
  }
</style>
