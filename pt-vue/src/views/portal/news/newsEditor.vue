<template>
  <div>

    <el-button class="add-button" type="primary" @click="dialogFormVisible = true">添加新闻</el-button>
    <el-dialog
      title="添加/修改新闻"
      :visible.sync="dialogFormVisible"
      @close="clear"
      >
      <el-row>
        <el-input
          v-model="news.newTitle"
          placeholder="请输入新闻标题"
          style="margin: 10px 0px;font-size: 18px;"
        >
        </el-input>
      </el-row>
      <el-row style="height: calc(100vh - 140px);">
        <mavon-editor
          v-model="news.newsContentMd"
          style="height: 100%;"
          ref=md
          @save="saveArticles"
          fontSize="16px">
          <button type="button" class="op-icon el-icon-document" :title="'摘要/封面'" slot="left-toolbar-after"
                  @click="dialogVisible = true"></button>
        </mavon-editor>
        <el-dialog
          :visible.sync="dialogVisible"
          width="30%">
          <el-divider content-position="left">摘要</el-divider>
          <el-input
            type="textarea"
            v-model="news.newsAbstract"
            rows="6"
            maxlength="255"
            show-word-limit></el-input>
          <el-row style="text-align: left;margin-bottom: 10px;margin-top: 10px">
            <el-date-picker
              v-model="news.failTime"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择失效时间">
            </el-date-picker>
          </el-row>
          <el-divider content-position="left">新闻图片</el-divider>
          <div style="margin-top: 20px">
            <el-input v-model="news.profile" autocomplete="off" placeholder="图片 URL"></el-input>
            <img-upload @onUpload="uploadImg" ref="imgUpload" style="margin-top: 5px"></img-upload>
          </div>
          <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
        </el-dialog>
      </el-row>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import ImgUpload from "./imgUpload";

  export default {
    name: 'newsEditor',
    components: {ImgUpload},
    data () {
      return {
        news: {},
        dialogFormVisible: false,
        dialogVisible: false
      }
    },
    mounted () {
    },
    methods: {
      clear () {
        this.$refs.imgUpload.clear()
        this.news = {
          newTitle: '',
          newsContentMd: '',
          newsAbstract: '',
          failTime: '',
        }
      },
      saveArticles (value, render) {
        // value 是 md，render 是 html
        this.$confirm('是否保存并发布文章?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios
              .post('/portal/News/addOrUpdate',{
                id: this.news.id,
                newTitle: this.news.newTitle,
                newsContentMd: value,
                newsHtml: render,
                newsAbstract: this.news.newsAbstract,
                profile: this.news.profile,
                failTime: this.news.failTime
                }
              ).then(resp => {
              console.log(resp)
              if (resp && resp === 'success') {
                this.$message({
                  type: 'info',
                  message: '已保存成功'
                })
                this.dialogFormVisible = false
                this.$emit('onSubmit')
              }
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消发布'
          })
        })
      },
      uploadImg () {
        this.news.profile = this.$refs.imgUpload.url
      }
    }
  }
</script>
<style scoped>
  .add-button {
    margin: 18px 0 0 10px;
  }
</style>
