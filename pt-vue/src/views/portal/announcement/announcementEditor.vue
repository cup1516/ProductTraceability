<template>
  <div>
    <el-button class="add-button" type="primary" @click="dialogFormVisible = true">添加公告</el-button>
    <el-dialog
      title="添加/修改公告"
      :visible.sync="dialogFormVisible"
      @close="clear"
      >
      <el-row style="text-align: left">
        <el-input
          v-model="announcement.announcementTitle"
          style="margin: 10px 0px;font-size: 18px; width: 700px"
          placeholder="请输入公告标题">
        </el-input>
      </el-row>
      <el-row style="text-align: left;margin-bottom: 10px">
        <el-date-picker
          v-model="announcement.failTime"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="请选择失效时间">
        </el-date-picker>
      </el-row>

      <el-row style="height: calc(100vh - 140px);">
        <mavon-editor
          v-model="announcement.announcementContentMd"
          style="height: 100%;"
          ref=md
          @save="saveArticles"
          fontSize="16px">
        </mavon-editor>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>

  export default {
    name: 'announcementEditor',
    data () {
      return {
        announcement: {},
        dialogFormVisible:false
      }
    },
    mounted () {
    },
    methods: {
      clear () {
        this.announcement = {
          announcementTitle: '',
          announcementContentMd: '',
          failTime: '',
        }
      },
      saveArticles (value, render) {
        // value 是 md，render 是 html
        this.$confirm('是否保存并发布公告?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
                  this.$axios
                          .post('/portal/Announcement/addOrUpdate',{
                                    id: this.announcement.id,
                                    announcementTitle: this.announcement.announcementTitle,
                                    announcementContentMd: value,
                                    announcementHtml: render,
                                    failTime: this.announcement.failTime
                                  }
                          ).then(resp => {
                    console.log(resp)
                    if (resp && resp === 'success') {
                      this.$message({
                        type: 'info',
                        message: '已保存成功'
                      })
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
    }
  }
</script>

