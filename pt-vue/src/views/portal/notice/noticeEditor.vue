<template>
    <div>
      <el-dialog
        title="添加/修改通告"
        :visible.sync="isShow"
        @close="clear">
        <el-row>
          <el-input
            v-model="notice.name"
            style="margin: 10px 0px;font-size: 18px;"
          ></el-input>
          <el-input
            v-model="notice.title"
            placeholder="请输入通告标题"
            style="margin: 10px 0px;font-size: 18px;"
          ></el-input>
          <el-row style="text-align: left;margin-bottom: 10px">
            <el-date-picker
              v-model="notice.failTime"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择失效时间">
            </el-date-picker>
          </el-row>
        </el-row>
        <el-row style="height: calc(100vh - 140px);">
          <mavon-editor
            v-model="notice.noticeContentMd"
            style="height: 100%;"
            ref=md
            @save="saveNotice"
            fontSize="16px">
          </mavon-editor>
        </el-row>
      </el-dialog>
      <el-button @click="isShow = true" type="primary"style="left: 20px">
        新增通告
      </el-button>

    </div>
</template>

<script>
    export default {
        name: 'noticeEditor',
      data(){
          return{
            notice:{},
            isShow:false,
          }
      },
      mounted () {
        this.notice.name = this.$store.getters.name
      },
      methods:{
        clear () {
          this.notice = {
            name:this.$store.getters.name,
            title: '',
            failTime: '',
            noticeContentMd: '',
          }
        },

        saveNotice (value, render) {
          // value 是 md，render 是 html
          this.$confirm('是否保存?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              this.$axios
                .post('/portal/notice/addOrUpdate', {
                    id: this.notice.id,
                    title:this.notice.title,
                    name: this.notice.name,
                    noticeContentMd: value,
                    noticeHtml: render,
                    failTime: this.notice.failTime,
                    companyId: this.$store.getters.company_id
                  }
                ).then(resp => {
                console.log(resp)

                  this.$message({
                    type: 'info',
                    message: '已保存成功',
                  })
                  this.isShow = false;
                  this.$emit('onSubmit')
              })
            }
          ).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消发布'
            })
          })
        }
      }
    }
</script>

<style scoped>

</style>
