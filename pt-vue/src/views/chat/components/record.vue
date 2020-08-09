<!--录音-->
<template>
  <div >
    <el-dialog
      v-dialogDrag
      title="录制语音消息"
      :visible.sync="centerDialogVisible"
      width="250px"
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
          <i  class="icon iconfont icon-tingzhiluyin" ></i>
        </div>

      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button :disabled="!yse" type="primary" @click="recordMsg">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import Recorder from 'js-audio-recorder'
  let recorder = new Recorder();
  export default {
    props: [
      'isShow'
    ],
    name: "record",
    data(){
      return{
        msg: '',
        centerDialogVisible: this.isShow,
        isStart: true,
        isPause: false,
        timer: "",
        content: "",
        hour: 0,
        minutes: 0,
        seconds: 0,
        duration:0,
        dataArray:[],
        yse: false,
      }
    },
    methods:{
      recordMsg(){//提交录音信息
        this.centerDialogVisible = false;
        this.msg = recorder.getWAVBlob();
        this.duration = recorder.duration;
        let params = {
          record: this.msg,
          time: this.duration
        }
        this.reset();
        this.$emit('start',params);
      },
      cancel(){//取消录音
        this.centerDialogVisible = false;
        let flag = false;
        this.$emit('start',flag);
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
          recorder = null;
          that.reset();
          // // 销毁录音实例，置为null释放资源，fn为回调函数，
          // recorder.destroy().then(function() {
          //   recorder = null;
          //   that.reset();
          // });
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
      }
    }
  }
</script>

<style scoped>


  .start .icon-yuyin{
    float: left;
    cursor: pointer
  }
  .start .icon-yuyin:hover{
    color: #1aad19
  }
  .start .icon-luyinzanting{
    float: left;
    cursor: pointer;
    font-size: 30px;
  }
  .start .icon-luyinzanting:hover{
    color: #1aad19
  }
  .icon-icon_luyinbolang-{
    float: left;
    position: fixed;
    margin-left: 23px;
  }
  .icon-tingzhiluyin{
    float: left;
    position: fixed;
    margin-left: 150px;
    font-size: 20px;
  }
  .icon-tingzhiluyin:hover{
    color: #ff0000
  }
  .timer{
    margin-left: 80px;

  }



</style>
