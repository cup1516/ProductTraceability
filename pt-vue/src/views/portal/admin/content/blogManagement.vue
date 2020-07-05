<template>
  <div>


    <el-row :span="24" class="toolbar" style="padding-bottom: 10px;padding-top: 10px;text-align: center">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="blogId" placeholder="帖子编号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="findById()">查询</el-button>
        </el-form-item>
      </el-form>
    </el-row>

    <el-dialog :visible.sync="isShow2" style="text-align: left">
      <div class="comment">
        <div class="info">
          <div class="right">
            <div class="name">{{blog1.userName}}</div>
            <div class="date">{{blog1.createTime}}</div>
          </div>
        </div>
        <div class="content">{{blog1.content}}</div>
        <div class="control">
        <span class="like" >
          <i class="el-icon-thumb"></i>
          <span class="like-num">{{blog1.likeNum > 0 ? blog1.likeNum + '人赞' : '0人赞'}}</span>
        </span>
        </div>
        <div class="reply">
          <div class="item" v-for="(reply,j) in comment" :key="j">
            <div class="reply-content">
              <span class="from-name">{{reply.userName}}:</span>
              <span class="to-name">@{{reply.parentName}}</span>
              <span>{{reply.content}}</span>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>

    <el-card style="margin: 18px 2%;width: 95%">
      <el-table
        :data="blog"
        stripe
        style="width: 100%"
        >
        <el-table-column
          prop="blogId"
          label="帖子编号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="userName"
          label="发表用户"
          fit>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="发表时间"
          sortable>
        </el-table-column>
        <el-table-column
          prop="content"
          label="发表内容"
          fit>
        </el-table-column>
        <el-table-column
          prop="likeNum"
          label="点赞数"
          fit>
        </el-table-column>

        <el-table-column
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button type="primary" size="small"  icon ='el-icon-share'@click="showBlog(scope.row)">查看</el-button>
            <el-button @click="deleteBlog(scope.row)" type="primary" icon="el-icon-delete" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
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
  import qs from 'qs'

  export default {
    methods: {

      showBlog(item){
        this.isShow2 = true
        this.blog1 = item
        this.$axios.get('/portal/Blogcomment/get/by-blog/'+item.blogId).then(resp => {
          this.comment=resp;
        })

      },

      deleteBlog(row) {
        this.$confirm('是否删除帖子?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios.delete('/portal/blog/delete/' + row.blogId).then(resp => {
              console.log(resp)
                this.$message({
                  type: 'info',
                  message: '已删除成功',
                  callback: action => {
                    window.location.reload();
                  }
                })
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },

      findById(){
        this.$axios.get('/portal/blog/get/by-BlogId/'+this.blogId).then(resp =>{
          console.log(resp)
          this.blog = resp;
          }
        )
      },
      loadBlog(){

        var _this = this
        this.$axios.get('/portal/blog/findAllDesc/0/5').then(resp => {
          console.log(resp)
          _this.blog = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
        // this.$axios.get('/blog/get/all').then(resp => {
        //   this.blog=resp.data;
        // })
      },
      page(currentPage) {
        const _this = this
        this.$axios.get('/portal/blog/findAllDesc/'+(currentPage-1)+'/5').then(resp => {
          console.log(resp)
          _this.blog = resp.content;
          _this.pageSize = resp.size;
          _this.total = resp.totalElements
        })
      },
    },
    data(){
      return {
        //pageSize为每页显示的数据条数，total为总数据
        pageSize:'2',
        total:'20',
        currentPage:'',
        filters:{
          id:''
        },
        blog: [],
        blog1:{},
        blogId:'',
        isShow2: false,
        comment:[],
      }
    },

    created() {
      this.loadBlog()
    }
  }
</script>

<style scoped lang="scss">
  @import "src/views/portal/bbs/_index.scss";


    .comment {
      text-align: left;
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
</style>

