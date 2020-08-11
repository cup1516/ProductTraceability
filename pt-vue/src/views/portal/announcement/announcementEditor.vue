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
          style="margin: 10px 0px;font-size: 18px; width: 600px"
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
          @save="save"
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

  import { saveAnnouncement } from '../../../api/portal/announcement'

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
      save (value, render) {
        // value 是 md，render 是 html
        this.$confirm('是否保存并发布公告?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          saveAnnouncement(
            this.announcement = {
              id: this.announcement.id,
              announcementTitle: this.announcement.announcementTitle,
              announcementContentMd: value,
              announcementHtml: render,
              failTime: this.announcement.failTime,
              companyId:this.$store.getters.company_id
          })
          .then(() => {
            this.msgSuccess("新增成功");
            this.dialogFormVisible = false
            this.$emit('onSubmit')
          })
        }
        ).catch(resp=>{
          this.msgError(resp);
        });
      },
    }
  }
</script>

