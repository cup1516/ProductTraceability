<!--评论模块-->
<template>
  <div class="container">
    <NavMenu></NavMenu>
    <div v-clickoutside="hideReplyBtn" @click="inputFocus" class="my-reply">
      <div class="reply-info" >
        <div
          tabindex="0"
          contenteditable="true"
          id="replyInput"
          spellcheck="false"
          placeholder="发表帖子..."
          class="reply-input"
          @focus="showReplyBtn"
          @input="onDivInput($event)"
        >
        </div>
      </div>
      <div class="reply-btn-box" v-show="btnShow">
        <el-button class="reply-btn" size="medium" @click="sendComment" type="primary">发表帖子</el-button>
      </div>
    </div>

    <div class="comment" v-for="(item,i) in blog" :key="i">
      <div class="info">
        <div class="right">
          <div class="name">{{item.userName}}</div>
          <div class="date">{{item.createTime}}</div>
        </div>
        <div class="icon-btn"  >
          <el-button
            v-if="userId==item.userId"
            type="danger"
            size="mini"
            icon="el-icon-delete"
            circle
            @click="deleteBlog(item)"></el-button>
        </div>
      </div>
      <div class="content">{{item.content}}</div>
      <div class="control">
        <span class="like" :class="{active: item.isLike}" @click="likeClick(item)">
          <i class="el-icon-thumb"></i>
          <span class="like-num">{{item.likeNum > 0 ? item.likeNum + '人赞' : '赞'}}</span>
        </span>
        <span class="comment-reply" @click="showCommentInput(item)">
          <i class="el-icon-chat-square"></i>
          <span>回复</span>
        </span>
      </div>
      <div class="reply">
        <div class="item" v-for="reply in item.reply" >
          <div class="reply-content">
            <span class="from-name">{{reply.userName}}:</span>
            <span class="to-name">@{{reply.parentName}}</span>
            <span>{{reply.content}}</span>
          </div>
          <div class="reply-bottom">
            <span>{{reply.createTime}}</span>
            <span class="reply-text" @click="showCommentInput(item, reply)">
              <i class="el-icon-chat-square"></i>
              <span>回复</span>
            </span>
          </div>

        </div>
        <div class="write-reply" v-if="item.reply.length > 0" @click="showCommentInput(item)">
          <i class="el-icon-edit"></i>
          <span class="add-comment">添加新评论</span>
        </div>
        <transition name="fade">
          <div class="input-wrapper" v-if="showItemId === item.blogId">
            <el-input class="gray-bg-input"
                      v-model="inputComment"
                      type="textarea"
                      :rows="3"
                      autofocus
                      placeholder="写下你的评论">
            </el-input>
            <div class="btn-control">
              <span class="cancel" @click="cancel">取消</span>
              <el-button class="btn" type="success" round @click="commitComment(item)">确定</el-button>
            </div>
          </div>
        </transition>

      </div>
    </div>
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

  import Vue from 'vue'
  import NavMenu from '../common/NavMenu'
  const clickoutside = {
    // 初始化指令
    bind(el, binding, vnode) {
      function documentHandler(e) {
        // 这里判断点击的元素是否是本身，是本身，则返回
        if (el.contains(e.target)) {
          return false;
        }
        // 判断指令中是否绑定了函数
        if (binding.expression) {
          // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
          binding.value(e);
        }
      }
      // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
      el.vueClickOutside = documentHandler;
      document.addEventListener('click', documentHandler);
    },
    update() {},
    unbind(el, binding) {
      // 解除事件监听
      document.removeEventListener('click', el.vueClickOutside);
      delete el.vueClickOutside;
    },
  };
  export default {
    // props: {
    //   comments: {
    //     type: Array,
    //     required: true
    //   }
    // },
    components: { NavMenu },
    data() {
      return {
        pageSize:'2',
        total:'20',
        currentPage:'',
        btnShow: false,
        userName:'',
        userId:'',
        parentId: '',  //被评论者id
        parentName: '',
        inputComment: '',
        showItemId: '',
        replyComment:'',
        reply:[
          {
              commentId: '',  //主键id
              blogId: '',  //父评论id，即父亲的id
              userId: '',  //评论者id
              userName: '',  //评论者昵称
              parentId: '',  //被评论者id
              parentName: '',  //被评论者昵称
              content: '',  //评论内容
              createTime: ''   //评论时间
            },
        ],
        blog:[
                 {
                  blogId: '', //主键id
                  createTime: '',  //评论时间
                  userId: '',  //评论者id
                  userName: '',   //评论者昵称
                  likeNum: '', //点赞人数
                  content: '',  //评论内容
                  reply: [  //回复，或子评论
                    {
                      commentId: '',  //主键id
                      blogId: '',  //父评论id，即父亲的id
                      userId: '',  //评论者id
                      userName: '',  //评论者昵称
                      parentId: '',  //被评论者id
                      parentName: '',  //被评论者昵称
                      content: '',  //评论内容
                      createTime: ''   //评论时间
                    },
                  ],
                },
        ],
      }
    },
    created() {
      this.findUsernameAndUserId();
      this.loadBlogAndComment();
    },
    methods: {
      //获取用户的名称与Id
      findUsernameAndUserId()
      {
        this.userName=this.$store.getters.name
        this.userId = this.$store.getters.user_id
      },
      //初始化帖子评论与回复
      loadBlogAndComment(){

        this.$axios.get('/portal/blog/findAllDesc/0/5').then(resp => {
            // this.$axios.get('/blog/get/available').then(resp => {
            this.blog = resp.content;
            this.pageSize = resp.size;
            this.total = resp.totalElements
            this.blog.forEach(item=>{
            item.reply=[]
          })
          this.$axios.get('/portal/Blogcomment/getAll').then(resp => {
            let data = resp;
            let k, i;
            var _this =this
            for (k = 0; k < data.length; k++) {
              for (i = 0; i < this.blog.length; i++) {
                //如果帖子的主键与评论的父评论相同就添加,意思就是这个评论是本个帖子下面的评论
                if (data[k].blogId === this.blog[i].blogId) {
                  _this.blog[i].reply.push(data[k])
                }
              }
            }
          })
        })
      },
      //切换页数码
      page(currentPage) {
        const _this = this
        this.$axios.get('/portal/blog/findAllDesc/'+(currentPage-1)+'/5').then(resp => {
          console.log(resp)
          _this.blog = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
          this.blog.forEach(item=>{
            item.reply=[]
          })
          this.$axios.get('/portal/Blogcomment/getAll').then(resp => {
            let data = resp;
            let k, i;
            var _this =this
            for (k = 0; k < data.length; k++) {
              for (i = 0; i < this.blog.length; i++) {
                //如果帖子的主键与评论的父评论相同就添加,意思就是这个评论是本个帖子下面的评论
                if (data[k].blogId === this.blog[i].blogId) {
                  _this.blog[i].reply.push(data[k])
                }
              }
            }
          })
        })
      },
      //更新帖子的点赞数
      /**
       * 点赞
       */
      likeClick(item) {
        if (item.isLike === null) {
          Vue.$set(item, "isLike", true);
          item.likeNum++
          this.$axios.post('/portal/blog/addOrUpdate',{
            blogId: item.blogId,
            userName : item.userName,
            content : item.content,
            likeNum : item.likeNum,
            userId : item.userId
          })
          item.likeNum++
        } else {
            if (item.isLike) {
              item.likeNum--
              this.$axios.post('/portal/blog/addOrUpdate',{
                blogId: item.blogId,
                userName : item.userName,
                content : item.content,
                likeNum : item.likeNum,
                userId : item.userId
              })
            }
            else {
              item.likeNum++
              this.$axios.post('/portal/blog/addOrUpdate',{
                blogId: item.blogId,
                userName : item.userName,
                content : item.content,
                likeNum : item.likeNum,
                userId : item.userId
              })
            }
          item.isLike = !item.isLike;

        }
      },

      /**
       * 点击取消按钮
       */
      cancel() {
        this.showItemId = ''
      },
      deleteBlog(item){
        this.$confirm('是否删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete('/portal/blog/delete/'+item.blogId).then(resp => {
              console.log(resp)
                this.$message({
                  type: 'info',
                  message: '已删除成功',
                })
                this.loadBlogAndComment()
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })

      },

      /**
       * 提交评论
       */

      commitComment(item) {
        if(!this.inputComment){
          this.$message({
            showClose: true,
            type:'warning',
            message:'评论不能为空'
          })
        }else{
          this.$axios.post('/portal/Blogcomment/addOrUpdate',{
            userId:this.userId,
            userName:this.userName,
            content:this.inputComment,
            blogId:item.blogId,
            parentId:this.parentId,
            parentName:this.parentName,
          })
          this.inputComment=''
          this.loadBlogAndComment();
        }
      },

      /**
       * 点击评论按钮显示输入框
       * item: 当前
       * reply: 当前回复的评论
       */
      showCommentInput(item, reply) {
        if (reply) {
          this.inputComment = " "
          this.parentId = reply.userId
          this.parentName = reply.userName
        } else {
          this.inputComment = ''
        }
        this.showItemId = item.blogId
      },
      inputFocus(){
        var replyInput = document.getElementById('replyInput');
        replyInput.style.padding= "8px 8px"
        replyInput.style.border ="2px solid blue"
        replyInput.focus()
      },
      showReplyBtn(){
        this.btnShow = true
      },
      hideReplyBtn(){
        this.btnShow = false
        replyInput.style.padding= "10px"
        replyInput.style.border ="none"
      },

      sendComment(){
        if(!this.replyComment){
          this.$message({
            showClose: true,
            type:'warning',
            message:'评论不能为空'
          })
        }else{

          let input =  document.getElementById('replyInput')
          this.$axios.post('/portal/blog/addOrUpdate',{
              userName : this.userName,
              content : this.replyComment,
              likeNum : "0",
              userId : this.userId
            }).then(resp => {
              this.$message({
                type: 'info',
                message: '已发布成功'
              })
              this.loadBlogAndComment()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消发布'
            })
          })
          this.replyComment = ''
          input.innerHTML = '';

        }
      },
      onDivInput: function(e) {
        //当前发表的帖子的内容
        this.replyComment = e.target.innerHTML;

      },
    },

  }
</script>

<style scoped lang="scss">
  @import "_index";

  .container {
    padding: 0 10px;
    box-sizing: border-box;

    .my-reply{
      padding:10px;
      background-color:#fafbfc ;
      display:inline-block;
      vertical-align:top;
      width: 90%;
      .reply-info{
        display:inline-block;
        margin-left:5px;
        width:90%;
        .reply-input{
          min-height:20px;
          line-height:22px;
          padding:10px 10px;
          color:#ccc;
          background-color:#fff;
          border-radius:5px;
          &:empty:before{
            content:attr(placeholder);
          }
          &:focus:before{
            content: none;
          }
          &:focus{
            padding:8px 8px;
            border :2px solid blue;
            box-shadow :none;
            outline :none;
          }
      }
    }

    }
    .reply-btn-box{
      height:25px;
      margin:10px 0;
      .reply-btn{
        position:relative;
        float:right;
        margin-right:15px;
      }
    }


    .comment {
      display: flex;
      flex-direction: column;
      padding: 10px;
      border-bottom: 1px solid $border-fourth;
      .info {
        display: flex;
        align-items: center;
        .avatar {
          border-radius: 50%;
        }
        .right {
          display: flex;
          flex-direction: column;
          margin-left: 10px;
          .name {
            font-size: 16px;
            color: $text-main;
            margin-bottom: 5px;
            font-weight: 500;
          }
          .date {
            font-size: 12px;
            color: $text-minor;
          }
        }
        .icon-btn{
          width:30%;
          padding:0 !important;
          float:right;
          @media screen and (max-width : 1200px){
          width:20%;
          padding:7px
        }
        }
      }
      .content {
        font-size: 16px;
        color: $text-main;
        line-height: 20px;
        padding: 10px 0;
        text-align: left;
        margin-left: 10px;
      }
      .control {
        display: flex;
        align-items: center;
        font-size: 14px;
        color: $text-minor;
        .like {
          display: flex;
          align-items: center;
          margin-right: 20px;
          cursor: pointer;
          &.active, &:hover {
            color: $color-main;
          }
          .iconfont {
            font-size: 14px;
            margin-right: 5px;
          }
        }
        .comment-reply {
          display: flex;
          align-items: center;
          cursor: pointer;
          &:hover {
            color: $text-333;
          }
          .iconfont {
            font-size: 16px;
            margin-right: 5px;
          }
        }

      }
      .reply {
        margin: 10px 0;
        border-left: 2px solid $border-first;
        .item {
          margin: 0 10px;
          padding: 10px 0;
          border-bottom: 1px dashed $border-third;
          .reply-content {
            display: flex;
            align-items: center;
            font-size: 14px;
            color: $text-main;
            .from-name {
              color: $color-main;
            }
            .to-name {
              color: $color-main;
              margin-left: 5px;
              margin-right: 5px;
            }
          }
          .reply-bottom {
            display: flex;
            align-items: center;
            margin-top: 6px;
            font-size: 12px;
            color: $text-minor;
            .reply-text {
              display: flex;
              align-items: center;
              margin-left: 10px;
              cursor: pointer;
              &:hover {
                color: $text-333;
              }
              .icon-comment {
                margin-right: 5px;
              }
            }
          }
        }
        .write-reply {
          display: flex;
          align-items: center;
          font-size: 14px;
          color: $text-minor;
          padding: 10px;
          cursor: pointer;
          &:hover {
            color: $text-main;
          }
          .el-icon-edit {
            margin-right: 5px;
          }
        }
        .fade-enter-active, fade-leave-active {
          transition: opacity 0.5s;
        }
        .fade-enter, .fade-leave-to {
          opacity: 0;
        }
        .input-wrapper {
          padding: 10px;
          .gray-bg-input, .el-input__inner {
            /*background-color: #67C23A;*/
          }
          .btn-control {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            padding-top: 10px;
            .cancel {
              font-size: 16px;
              color: $text-normal;
              margin-right: 20px;
              cursor: pointer;
              &:hover {
                color: $text-333;
              }
            }
            .confirm {
              font-size: 16px;
            }
          }
        }
      }
    }
  }
</style>
